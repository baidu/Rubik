package com.rubik.plugins.basic.extra

import com.ktnail.gradle.*
import com.ktnail.x.Logger
import com.rubik.context.Ext
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikKaptDependencyNotSetException
import com.rubik.context.exception.RubikPluginNotApplyException
import com.rubik.context.extra.rubikExtensionName
import com.rubik.context.extra.rubikMainProject
import com.rubik.plugins.basic.exception.RubikRouterDependencyNotSetException
import com.rubik.plugins.basic.extra.Rubik.CONTEXT_PLUGIN_NAME
import com.rubik.plugins.basic.extra.Rubik.SHELL_PLUGIN_NAME
import com.rubik.context.utility.Kapt
import com.rubik.plugins.extension.RubikExtension
import com.rubik.plugins.extension.RubikSingletonExtension
import com.rubik.publish.extra.publicationRecordDirRoot
import com.rubik.publish.extra.rubikMavenLocalRepository
import com.rubik.publish.extra.rubikMavenRepository
import org.gradle.api.Project
import java.io.File

object Rubik {
    const val SHELL_PLUGIN_NAME = "rubik-shell"
    const val CONTEXT_PLUGIN_NAME = "rubik-context"
}

val Project.rubik: RubikExtension
    get() = (extensions.findByName(rubikExtensionName) as? RubikExtension) ?: extensions.create(
        rubikExtensionName,
        RubikExtension::class.java,
        this
    )

val Project.rubikSingleton
    get() =
        rubikMainProject.run {
            (extensions.findByName("rubik-singleton") as? RubikSingletonExtension)
                ?: extensions.create(
                    "rubik-singleton",
                    RubikSingletonExtension::class.java,
                    this
                ) ?: throw RubikPluginNotApplyException()
        }

val Project.isRubikShell
    get() = pluginManager.hasPlugin(SHELL_PLUGIN_NAME)

fun Project.addRubikRepository() {
    repositories.maven { maven ->
        maven.url = rubikMavenRepository
        maven.isAllowInsecureProtocol = true
    }
    repositories.maven { maven ->
        maven.url = rubikMavenLocalRepository
        maven.isAllowInsecureProtocol = true
    }
}

fun Project.applyContext() {
    if (!pluginManager.hasPlugin(CONTEXT_PLUGIN_NAME)) pluginManager.apply(CONTEXT_PLUGIN_NAME)
}

fun Project.applyShell() {
    if (!pluginManager.hasPlugin(SHELL_PLUGIN_NAME)) pluginManager.apply(SHELL_PLUGIN_NAME)
}

fun Project.addRubikKaptDependency() {
    addDependency(Kapt.CONFIGURATION_NAME, propertyOr(Ext.RUBIK_KAPT_VERSION) { throw RubikKaptDependencyNotSetException() })
}

fun Project.addRubikRouterDependency() {
    addDependency(DependencyType.IMPLEMENTATION, propertyOr(Ext.RUBIK_ROUTER_VERSION) { throw RubikRouterDependencyNotSetException() })
}

private fun Project.fuzzyApplyRubikConfigFiles(dir: File, vararg startsWith: String) {
    val configs = mutableListOf<String>()
    startsWith.forEach { start ->
        dir.listFiles()?.filter { file ->
            file.name.startsWith(start) && file.name.endsWith(".gradle")
        }?.forEach { file ->
            if (!configs.contains(file.absolutePath)) configs.add(file.absolutePath)
        }
    }
    Logger.pIf(configs.isNotEmpty(), LogTags.PLUGIN, this) { " CONFIG FILES :\n  ${configs.joinToString("\n  ")}" }
    configs.forEach { path ->
        val file = File(path)
        Logger.p(LogTags.PLUGIN, this) { " APPLIED CONFIG FILE path:${file.absolutePath}" }
        apply(mapOf("from" to file))
    }
}

fun Project.fuzzyApplyRubikConfigFiles(dir: File) {
    fuzzyApplyRubikConfigFiles(
        dir,
        "rubik-global",
        "rubik-component",
        "rubik-context",
        "rubik-packing",
        "rubik"
    )
}

fun Project.fuzzyApplyGivenRubikConfigFileDirs() {
    arrayProperties(Ext.RUBIK_CONFIG_FILE_DIRS).forEach { path ->
        fuzzyApplyRubikConfigFiles(File(path))
    }
}

fun Project.fuzzyApplyRubikPublicationRecordFiles() {
    fuzzyApplyRubikConfigFiles(publicationRecordDirRoot)
}


val Project.logLevel: Int
    get() {
        val level = propertyOfT<Int>(Ext.RUBIK_LOG_LEVEL)
        return when {
            level == null -> Logger.Level.ALL
            level > 3 -> Logger.Level.CLOSE
            level == 3 -> Logger.Level.HIGH
            level == 2 -> Logger.Level.DEFAULT
            level == 1 -> Logger.Level.LOW
            level < 1 -> Logger.Level.ALL
            else -> Logger.Level.ALL
        }
    }

val Project.logWriteToFile: Boolean
    get() = propertyOr(Ext.RUBIK_WRITE_LOG_TO_FILE, false)