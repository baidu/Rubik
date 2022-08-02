package com.rubik.plugins.basic.utility

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.ktnail.x.uri.buildUri
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.*
import com.rubik.plugins.basic.utility.Rubik.CONTEXT_PLUGIN_NAME
import com.rubik.plugins.basic.utility.Rubik.ROOT_PLUGIN_NAME
import com.rubik.plugins.basic.utility.Rubik.TEST_PLUGIN_NAME
import com.rubik.plugins.extension.RubikConfigExtension
import com.rubik.plugins.extension.RubikExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.AppliedPlugin
import java.io.File
import java.net.URI

object Rubik {
    const val MAIN_PLUGIN_NAME = "rubik"
    const val ROOT_PLUGIN_NAME = "rubik-root"
    const val CONTEXT_PLUGIN_NAME = "rubik-context"
    const val TEST_PLUGIN_NAME = "rubik-test"
    const val CONFIG_EXTENSION_NAME = "rubik-config"
    const val RUBIK_TASK_GROUP_NAME = "rubik"
    const val RUBIK_LIB_TASK_GROUP_NAME = "rubik-lib"
    const val RUBIK_COMPONENT_TASK_GROUP_NAME = "rubik-component"
    const val RUBIK_MODULE_TASK_GROUP_NAME = "rubik-module"
    const val RUBIK_LIB_DEV_TASK_GROUP_NAME = "rubik-lib-dev"
    const val RUBIK_COMPONENT_DEV_TASK_GROUP_NAME = "rubik-component-dev"
    const val RUBIK_MODULE_DEV_TASK_GROUP_NAME = "rubik-module-dev"
    const val RUBIK_LIBS_TASK_GROUP_NAME = "rubik-lib-all"
}

val Project.rubikExtensionName
    get() = project.propertyOr(Ext.RUBIK_EXTENSION_NAME) { "rubik" }

val Project.rubikExtension: RubikExtension
    get() = (extensions.findByName(rubikExtensionName) as? RubikExtension) ?: extensions.create(
        rubikExtensionName,
        RubikExtension::class.java,
        this
    )

fun Project.rubikTask(name: String, action: (Task) -> Unit): Task =
    task(name, action).apply {
        group = Rubik.RUBIK_TASK_GROUP_NAME
    }

fun <T : Task> Project.rubikTask(
    name: String,
    type: Class<T>,
    action: (T) -> Unit
): T = tasks.create(name, type, action).apply {
    group = Rubik.RUBIK_TASK_GROUP_NAME
}

fun Project.rubikLibTask(name: String): Task =
    task(name) {}.apply {
        group = Rubik.RUBIK_LIB_TASK_GROUP_NAME
    }

fun Project.rubikLibDevTask(name: String): Task =
    task(name) {}.apply {
        group = Rubik.RUBIK_LIB_DEV_TASK_GROUP_NAME
    }

fun Project.rubikComponentTask(name: String): Task =
    task(name) {}.apply {
        group = Rubik.RUBIK_COMPONENT_TASK_GROUP_NAME
    }

fun Project.rubikComponentDevTask(name: String): Task =
    task(name) {}.apply {
        group = Rubik.RUBIK_COMPONENT_DEV_TASK_GROUP_NAME
    }

fun Project.rubikModuleTask(name: String): Task =
    task(name) {}.apply {
        group = Rubik.RUBIK_MODULE_TASK_GROUP_NAME
    }

fun Project.rubikModuleDevTask(name: String, config: (Task) -> Unit = {}): Task =
    task(name, config).apply {
        group = Rubik.RUBIK_MODULE_DEV_TASK_GROUP_NAME
    }

fun Project.rubikLibsTask(name: String): Task =
    task(name) {}.apply {
        group = Rubik.RUBIK_LIBS_TASK_GROUP_NAME
    }

val Project.rubikConfig
    get() =
        when {
            pluginManager.hasPlugin(Rubik.MAIN_PLUGIN_NAME) -> this
            rootProject.pluginManager.hasPlugin(Rubik.MAIN_PLUGIN_NAME) -> rootProject
            else -> throw RubikConfigPluginNotApplyException()
        }

val Project.rubikConfigExtension
    get() =
        rubikConfig.run {
            (extensions.findByName(Rubik.CONFIG_EXTENSION_NAME) as? RubikConfigExtension)
                ?: extensions.create(
                    Rubik.CONFIG_EXTENSION_NAME,
                    RubikConfigExtension::class.java,
                    this
                )
                ?: throw RubikConfigPluginNotApplyException()
        }

val Project.startTaskFromThisProject
    get() = projectDir.absolutePath == gradle.startParameter.projectDir?.absolutePath

val Project.startTaskFromRootProject
    get() = rootProject.startTaskFromThisProject

val Project.startTaskFromTerminal
    get() = null == gradle.startParameter.projectDir?.absolutePath

val Project.isTaskTarget
    get() = startTaskFromThisProject || startTaskFromRootProject || startTaskFromTerminal

fun Project.isTaskTarget(vararg taskNames: String) =
    isTaskTarget && gradle.startParameter.taskNames.any { name -> taskNames.contains(name) }

val Project.isRubikRoot
    get() = pluginManager.hasPlugin(ROOT_PLUGIN_NAME)

val Project.autoGenerateAggregate
    get() = propertyOr(Ext.RUBIK_AUTO_GENERATE_AGGREGATE, false)

val Project.rubikMavenRepository: URI
    get() = uri(propertyOr(Ext.RUBIK_MAVEN_REPOSITORY) { throw RubikMavenRepositoryNotSetException() })

val Project.rubikMavenLocalRepository: URI
    get() = uri(propertyOr(Ext.RUBIK_MAVEN_LOCAL_REPOSITORY) { "./rubik_maven_local" })

fun Project.addRubikRepository() {
    repositories.maven { maven ->
        maven.url = rubikMavenRepository
    }
    repositories.maven { maven ->
        maven.url = rubikMavenLocalRepository
    }
}

fun String.toUriIfAuthority(project: Project) = if (contains("://")) {
    this
} else {
    buildUri(
        project.rubikConfigExtension.globalConfig.scheme
            ?: throw RubikDefaultSchemeNotSetException(), this
    )
}

fun Project.isAndroidApplication() = pluginManager.hasPlugin(PluginName.ANDROID_APPLICATION)

fun Project.ifAndroidApplication(action: (AppliedPlugin) -> Unit) = pluginManager.withPlugin(PluginName.ANDROID_APPLICATION, action)

fun Project.applyContext() {
    if (!pluginManager.hasPlugin(CONTEXT_PLUGIN_NAME)) pluginManager.apply(CONTEXT_PLUGIN_NAME)
}

fun Project.applyRoot() {
    if (!pluginManager.hasPlugin(ROOT_PLUGIN_NAME)) pluginManager.apply(ROOT_PLUGIN_NAME)
}

fun Project.applyTest() {
    if (!pluginManager.hasPlugin(TEST_PLUGIN_NAME)) pluginManager.apply(TEST_PLUGIN_NAME)
}

fun Project.addRubikKaptDependency() {
    addDependency(Kapt.CONFIGURATION_NAME, propertyOr(Ext.RUBIK_KAPT_VERSION) { throw RubikKaptDependencyNotSetException() })
}

fun Project.addRubikRouterDependency() {
    addDependency(DependencyType.IMPLEMENTATION, propertyOr(Ext.RUBIK_ROUTER_VERSION) { throw RubikRouterDependencyNotSetException() })
}

fun Project.fuzzyApplyRubikConfigFiles(dir: File) {
    dir.listFiles()?.filter { file ->
        file.name.startsWith("rubik") && file.name.endsWith(".gradle")
    }?.forEach { file ->
        Logger.p(LogTags.PLUGIN, this) { " APPLIED FILE file:${file.absolutePath}" }
        apply(mapOf("from" to file))
    }
}

fun Project.fuzzyApplyGivenRubikConfigFileDirs() {
    (properties[Ext.RUBIK_CONFIG_FILE_DIRS] as? List<*>)?.forEach { path ->
        fuzzyApplyRubikConfigFiles(File(path.toString()))
    }
}

fun Logger.p(tag: String, project: Project?, action: () -> String) {
    d(tag + project.let { _ -> if (null != project) " [${project.path}] " else "" } + action())
}

fun BaseVariant.match(other: BaseVariant) = name == other.name || buildType.name == other.name

val Project.libTmpDirRoot: File
    get() = File(project.propertyOr(Ext.RUBIK_TMP_LIB_DIR) {
        project.rootProject.rootDir.absolutePath + File.separator + "rubik_libs"
    })
