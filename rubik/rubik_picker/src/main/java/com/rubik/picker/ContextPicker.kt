package com.rubik.picker

import com.android.build.gradle.internal.dsl.BuildType
import com.ktnail.gradle.androidExtension
import com.ktnail.gradle.flavorNamesInDimensions
import com.ktnail.gradle.p
import com.ktnail.gradle.propertyOr
import com.ktnail.x.Logger
import com.ktnail.x.firstOrNull
import com.rubik.context.extra.Context
import com.rubik.pick.*
import com.rubik.picker.exception.RubikProjectNoFlavorFoundException
import com.rubik.context.Ext
import com.rubik.picker.container.ContextPickCase
import com.rubik.context.extra.globalConfig
import com.rubik.picker.extra.getPickCase
import com.rubik.picker.extra.obtainContext
import com.rubik.picker.log.LogTags
import org.gradle.api.Project

class ContextPicker {
    companion object {
        private const val FLAVOR_DIMENSION = "RUBIK_AUTO_FILL_FLAVOR_DIMENSION"
    }

    fun pick(
        cases: List<ContextPickCase>,
        forProject: Project
    ) {
        cases.forEach { case ->
            pickCase(case.pickCase, forProject,  case.context, null, null)
        }
    }

    fun pick(
        what: PickWhat,
        forProject: Project,
        forceMode: PickHow? = null,
        exceptBuildType: ((BuildType) -> Boolean)? = null,
        pickMavenResult: ((PickResult) -> Unit)? = null
    ) {
        what.where.obtainContext(
            strict = forProject.propertyOr(Ext.RUBIK_STRICT_TOUCHING_AND_PACKING, true)
        ).forEach { context ->
            pickCase(
                context.getPickCase(what),
                forProject,
                context,
                forceMode,
                exceptBuildType,
                pickMavenResult
            )
        }
    }

    private fun pickCase(
        case: PickCase,
        forProject: Project,
        context: Context,
        forceMode: PickHow? = null,
        exceptBuildType: ((BuildType) -> Boolean)? = null,
        pickMavenResult: ((PickResult) -> Unit)? = null
    ) {

        case.processForceMode(forceMode, context)
            .processOnlyLib(forProject)
            .processFalsePick(context, pickMavenResult)
            .processMergeFlavor()
            .processFlavorCheck(context, forProject, false)
            .let { processedCase ->
                processedCase.flavorHows.forEach { (flavor, how) ->
                    pickCase(
                        processedCase.what,
                        how,
                        forProject,
                        flavor,
                        context,
                        exceptBuildType,
                        pickMavenResult
                    )
                }
            }

    }

    private fun pickCase(
        what: PickWhat,
        how: PickHow,
        forProject: Project,
        forFlavor: String?,
        context: Context,
        exceptBuildType: ((BuildType) -> Boolean)? = null,
        pickMavenResult: ((PickResult) -> Unit)? = null
    ) {
        Logger.p(LogTags.PICK_CASE, forProject) {  "\n    > WHAT:${what}\n    > HOW:${how} " }

        when (how) {
            is MavenMode -> {
                MavenPicker(what, how, context, pickMavenResult).pick(forProject, forFlavor)
            }
            is ProjectMode -> {
                ProjectPicker(what, how, context, exceptBuildType).pick(forProject, forFlavor)
            }
            is TmpDirMode -> {
                TmpDirLibPicker(what, how, context).pick(forProject, forFlavor)
            }
            else -> { /* nothing */
            }
        }
    }

    private fun PickCase.processForceMode(
        forceMode: PickHow?,
        context: Context
    ): PickCase {
        return if (
            null != forceMode ||
            globalConfig.forceMavenModeEnable ||
            globalConfig.forceProjectModeEnable
        )
            PickCase(what).also { newCase ->
                newCase.addPickHows(flavorHows.mapValues { (_, baseHow) ->
                    val forceHow = when {
                        null != forceMode -> forceMode
                        baseHow is NoSourceMode -> baseHow
                        globalConfig.forceMavenModeEnable -> MavenMode()
                        globalConfig.forceProjectModeEnable && null != baseHow.projectPath -> ProjectMode()
                        else -> baseHow
                    }

                    if (forceHow != baseHow) forceHow.mergeLowerPriority(baseHow)
                    else baseHow

                })
            } else this
    }

    private fun PickCase.processOnlyLib(project: Project): PickCase {
        return if (pickLibOnly) {
            PickCase(what).also { newCase ->
                flavorHows.firstOrNull()?.let { how->
                    if (project.propertyOr(Ext.RUBIK_TMP_LIB_DIR_MODE, false)) {
                        newCase.addDefaultHow(TmpDirMode().mergeLowerPriority(how))
                    } else if (how is TmpDirMode || how is MavenMode) {
                        newCase.addDefaultHow(how)
                    } else {
                        newCase.addDefaultHow(MavenMode().mergeLowerPriority(how))
                    }
                }
            }
        } else this
    }

    private fun PickCase.processFalsePick(
        context: Context,
        pickMavenResult: ((PickResult) -> Unit)?
    ): PickCase {
        return this.apply {
            if (null != pickMavenResult && !mavenMode) {
                flavorHows.forEach { (flavor, how) ->
                    MavenPicker(what, how, context, pickMavenResult).falsePick(flavor)
                }
            }
        }
    }

    private fun PickCase.processMergeFlavor(): PickCase {
        return if (!defaultPickOnly && noFlavorNeedMode) {
            PickCase(what).also { newCase ->
                firstHow?.let { how ->
                    newCase.addDefaultHow(how)
                }
            }
        } else this
    }

    private fun PickCase.processFlavorCheck(
        context: Context,
        forProject: Project,
        autoFill:Boolean
    ): PickCase {
        return this.apply {
            if (forProject.propertyOr(Ext.RUBIK_CHECK_FLAVOR_WHEN_PICK, true) || autoFill) {
                flavorHows.forEach { (forFlavor, _) ->
                    if (null != forFlavor) {
                        if (!forProject.flavorNamesInDimensions().contains(forFlavor)) {
                            if (!autoFill)
                                throw RubikProjectNoFlavorFoundException(forProject.path, context.uri, forFlavor)
                            else {
                                forProject.androidExtension?.flavorDimensions(FLAVOR_DIMENSION)
                                forProject.androidExtension?.productFlavors?.create(forFlavor) {
                                    it.dimension = FLAVOR_DIMENSION
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun fillFlavor(what: PickWhat, forProject: Project) {
        what.where.obtainContext().forEach { context ->
            context.getPickCase(what).processFlavorCheck(context, forProject, true)
        }
    }

    data class PickResult(
        val context: Context,
        val version: String,
        val variant: String?
    )
}
