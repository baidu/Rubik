package com.rubik.context.extra

import com.android.build.gradle.api.BaseVariant
import com.android.builder.model.BuildType
import com.ktnail.gradle.forEachVariant
import com.ktnail.gradle.propertyOr
import com.ktnail.x.Logger
import com.rubik.context.Ext
import com.rubik.context.exception.RubikPluginNotApplyException
import com.rubik.context.log.LogTags
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

object Rubik {
    const val MAIN_PLUGIN_NAME = "rubik"
    const val RUBIK_TASK_GROUP_NAME = "rubik"
}

fun Project.rubikTask(name: String, action: (Task) -> Unit): Task =
    task(name, action).apply {
        group = Rubik.RUBIK_TASK_GROUP_NAME
        Logger.dta(LogTags.CREATE_TASK) { " CREATE $group TASK ($name) for [${this@rubikTask.path}]" }
    }

fun <T : Task> Project.rubikTask(
    name: String,
    type: Class<T>,
    action: (T) -> Unit
): T = tasks.create(name, type, action).apply {
    group = Rubik.RUBIK_TASK_GROUP_NAME
    Logger.dta(LogTags.CREATE_TASK) { " CREATE $group TASK ($name) for [${this@rubikTask.path}]" }
}

val Project.rubikExtensionName
    get() = project.propertyOr(Ext.RUBIK_EXTENSION_NAME) { "rubik" }


val Project.rubikMainProject: Project
    get() =
        when {
            pluginManager.hasPlugin(Rubik.MAIN_PLUGIN_NAME) -> this
            rootProject.pluginManager.hasPlugin(Rubik.MAIN_PLUGIN_NAME) -> rootProject
            else -> throw RubikPluginNotApplyException()
        }

val Project.libTmpDirRoot: File
    get() = File(project.propertyOr(Ext.RUBIK_TMP_LIB_DIR) {
        project.rootProject.rootDir.absolutePath + File.separator + "rubik_libs"
    })

fun BuildType.isContextLibBuildType() = name.endsWith(com.rubik.context.publication.BuildType.CONTEXT_LIB_BUILD_TYPE_NAME)

fun BuildType.isContextLibCompilerBuildType() = name.endsWith(com.rubik.context.publication.BuildType.CONTEXT_LIB_COMPILER_BUILD_TYPE_NAME)

val BuildType.isCustomBuildType: Boolean
    get() = isContextLibBuildType() || isContextLibCompilerBuildType()

val BaseVariant.isCustomVariant: Boolean
    get() = buildType.isCustomBuildType

val Project.autoGenerateAggregate
    get() = propertyOr(Ext.RUBIK_AUTO_GENERATE_AGGREGATE, false)

val Project.autoGenerateComponentId
    get() = propertyOr(Ext.RUBIK_AUTO_GENERATE_COMPONENT_ID, false)

val Project.generateAggregateInBuildDir
    get() = propertyOr(Ext.RUBIK_GENERATE_AGGREGATE_IN_BUILD_DIR, true)

fun Project.makeRubikGeneratingDirs(onMakeDir: (File, BaseVariant) -> Unit) {
    project.forEachVariant { variant ->
        onMakeDir(makeRubikGeneratingDir(variant), variant)
    }
}

fun Project.makeRubikGeneratingDir(variant: BaseVariant): File =
    File(project.file(File(project.buildDir, "generated/source/rubikKotlin")), variant.name).apply {
        mkdirs()
    }