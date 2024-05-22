package com.rubik.plugins.context

import com.android.build.gradle.internal.dsl.BuildType
import com.ktnail.gradle.androidExtension
import com.ktnail.gradle.applyMaven
import com.ktnail.gradle.p
import com.ktnail.gradle.propertyOr
import com.ktnail.x.Logger
import com.rubik.context.extra.Context
import com.rubik.context.Ext
import com.rubik.pick.ProjectMode
import com.rubik.pick.TmpDirMode
import com.rubik.pick.contextDependLibCompileOnlyWhat
import com.rubik.pick.contextDependLibWhat
import com.rubik.plugins.RubikPlugin
import com.rubik.plugins.basic.LogTags
import com.rubik.context.Ext.RUBIK_AUTO_FILL_FLAVOR
import com.rubik.context.extra.autoGenerateComponentId
import com.rubik.context.extra.contextsContainer
import com.rubik.context.extra.isCustomBuildType
import com.rubik.plugins.context.controller.ComponentIdGeneratingController
import com.rubik.plugins.context.controller.ContextKaptController
import com.rubik.publish.task.PublishContextTaskController
import com.rubik.picker.ContextPicker
import com.rubik.publish.task.component.VariantComponentTask
import com.rubik.publish.task.target.ContextTaskTarget
import com.rubik.plugins.basic.transform.MakeLibsTransform
import com.rubik.picker.extra.componentPickWhat
import com.rubik.plugins.basic.extra.addRubikRouterDependency
import com.rubik.publish.extra.target
import com.rubik.publish.buildTypeName
import com.rubik.publish.compilerBuildTypeName
import com.rubik.publish.useResetCompiler
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * The the plugin of rubik context project .
 * Provide the ability to publish contexts & Dependence other contexts.
 *
 * @ Since:1.3
 */
abstract class ContextPlugin : RubikPlugin() {
    private var contexts: List<Context> = emptyList()

    private val tasksController: PublishContextTaskController by lazy { PublishContextTaskController(project) }

    private val kaptController: ContextKaptController  by lazy { ContextKaptController(project) }

    private val picker: ContextPicker by lazy { ContextPicker() }

    override fun apply(project: Project) {
        super.apply(project)

//        project.fuzzyApplyRubikConfigFiles(project.projectDir)

        LogTags.logApplyContext(project)

        project.afterEvaluate { // wait for gradle task create
            LogTags.logCreateTaskFinish(project)

            dependContextLibs()
            dependPackingComponent()
            project.addRubikRouterDependency()

            LogTags.logPickFinish(project)
        }

        contexts = contextsContainer.obtainByProject(project.path).onEach { context ->
            onObtainContext(context)
        }

    }

    private fun onObtainContext(context: Context) {

        prepareContext(context)

        LogTags.logPrepareContextFinish(project, context)

        tasksController.createTasks(context, context.target) { componentTask ->
            preparePublishComponent(context, componentTask)
        }

        fillFlavor(context)
    }

    private fun prepareContext(context: Context) {
        if (context.target.publishingSth) {
            project.applyMaven()
        }

        if (context.target.publishingContextLibs) {
            preparePublishContextLibs(context)
        }

        kaptController.configKapt(context)
    }

    private fun preparePublishContextLibs(context: Context) {
        project.androidExtension?.buildTypes(Action { container ->
            container.maybeCreate(context.buildTypeName()).apply {
                isDebuggable = true
                setMatchingFallbacks("debug")
            }
            Logger.p(LogTags.PREPARE_CONTEXT, project) { " ADD BUILD_TYPES (${context.buildTypeName()})" }
        })

        if (!context.useResetCompiler) {
            project.androidExtension?.registerTransform(MakeLibsTransform(project, context))
        } else {
            project.androidExtension?.buildTypes(Action { container ->
                container.maybeCreate(context.compilerBuildTypeName()).apply {
                    isDebuggable = true
                    setMatchingFallbacks("debug")
                }
                Logger.p(LogTags.PREPARE_CONTEXT, project) { " ADD BUILD_TYPES (${context.compilerBuildTypeName()})" }
            })
        }
    }

    private fun preparePublishComponent(context: Context, task: VariantComponentTask) {
        if (project.autoGenerateComponentId) {
            ComponentIdGeneratingController(project, context).createTask(
                task.artifactName,
                task.version,
                task.variant
            )
        }
    }

    private fun dependContextLibs() {
        val dev = contexts.devWhenPublishingComponent  // force pick publishing type , when publishing

        contexts.forEach { context ->
            context.touching.forEach { dependency ->
                Logger.p(LogTags.DO_PICK, project) { "  PICK CONTEXT LIBS  <${dependency.uri}> :" }
                picker.pick(contextDependLibWhat(dependency.uri, dev), project, pickMavenResult = { result ->
                    context.id.addTouching(result.context, result.version, result.variant)
                })
            }
            if(context.enableProvideRoute){
                if (!context.target.publishingContextLibs) {
                    Logger.p(LogTags.DO_PICK, project) { "  PICK SELF CONTEXT LIBS :" }
                    picker.pick(contextDependLibWhat(context.uri, dev), project)
                } else if (context.target.publishingBoth) {
                    Logger.p(LogTags.DO_PICK, project) { "  PICK SELF CONTEXT LIBS TMP DIR :" }
                    picker.pick(contextDependLibCompileOnlyWhat(context.uri, dev), project, TmpDirMode())
                }
            }
        }
    }

    private fun dependPackingComponent() {
        contexts.forEach { context ->
            context.packing.forEach { relation ->
                Logger.p(LogTags.DO_PICK, project) { "  PICK COMPONENT <${relation.uri}> :" }
                val what = relation.componentPickWhat(project)
                val exceptBuildType: ((BuildType) -> Boolean)? =
                    if (contexts.publishingContextLibs && project.propertyOr(Ext.RUBIK_REMOVE_PROJECT_WHEN_PUBLISH_CONTEXT_LIBS, true))
                        { buildType -> buildType.isCustomBuildType }
                    else null
                if (context.target.publishingComponent ) {
                    picker.pick(what, project, ProjectMode(), exceptBuildType = exceptBuildType, pickMavenResult = { result ->
                        context.id.addPacking(result.context, result.version, result.variant)
                    })
                } else {
                    picker.pick(what, project, exceptBuildType = exceptBuildType, pickMavenResult = {result ->
                        context.id.addPacking(result.context, result.version, result.variant)
                    })
                }
            }
        }
    }

    private val List<Context>.publishingContextLibs: Boolean
        get() = any { context -> context.target.publishingContextLibs }

    private val List<Context>.devWhenPublishingComponent: Boolean?
        get() = mapNotNull { context -> (context.target as? ContextTaskTarget.PublishContextTarget)?.devWhenPublishingComponent }.firstOrNull()

    private fun fillFlavor(context: Context) {
        if (project.propertyOr(RUBIK_AUTO_FILL_FLAVOR, true))
            context.packing.forEach { relation ->
                if (!context.target.publishingComponent) {
                    picker.fillFlavor(relation.componentPickWhat(project), project)
                }
            }
    }
}
