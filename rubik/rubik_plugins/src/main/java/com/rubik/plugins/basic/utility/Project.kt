package com.rubik.plugins.basic.utility

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestExtension
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.SourceKind
import com.ktnail.x.Logger
import com.ktnail.x.toCamel
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.utility.PluginName.KOTLIN_KAPT
import com.rubik.plugins.basic.utility.PluginName.MAVEN_PUBLISH
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractKotlinTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.util.*

private const val ANDROID_PROJECT_EXTENSION_NAME = "android"
private const val KOTLIN_PROJECT_EXTENSION_NAME = "kotlin"

object DependencyType {
    const val API = "api"
    const val IMPLEMENTATION = "implementation"
    const val COMPILE_ONLY = "compileOnly"
    const val RUNTIME_ONLY = "runtimeOnly"
    const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
    const val ANDROID_TEST_COMPILE_ONLY = "androidTestCompileOnly"
}

object PluginName {
    const val MAVEN_PUBLISH = "maven-publish"
    const val KOTLIN_ANDROID = "kotlin-android"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val ANDROID_APPLICATION = "com.android.application"
}

val Project.androidExtension: BaseExtension?
    get() = extensions.findByName(ANDROID_PROJECT_EXTENSION_NAME).let { extension ->
        when (extension) {
            is AppExtension, is LibraryExtension, is TestExtension -> extension
            else -> null
        }
    } as? BaseExtension

val Project.kotlinExtension: KotlinAndroidProjectExtension?
    get() = extensions.findByName(KOTLIN_PROJECT_EXTENSION_NAME) as? KotlinAndroidProjectExtension

fun Project.forEachVariant(action: (BaseVariant) -> Unit) {
    extensions.getByName(ANDROID_PROJECT_EXTENSION_NAME).let { androidExtension ->
        when (androidExtension) {
            is AppExtension -> androidExtension.applicationVariants.all(action)
            is LibraryExtension -> {
                androidExtension.libraryVariants.all(action)
//                if (androidExtension is FeatureExtension) {
//                    androidExtension.featureVariants.all(action)
//                }
            }
//                is TestExtension -> androidExtension.applicationVariants.all(action)
        }
//            if (androidExtension is TestedExtension) {
//                androidExtension.testVariants.all(action)
//                androidExtension.unitTestVariants.all(action)
//            }
    }
}

fun Project.firstVariant(buildTypeName: String, action: (BaseVariant) -> Unit) {
    val buildTypeNames = mutableSetOf<String>()
    forEachVariant { variant ->
        if (!buildTypeNames.contains(variant.buildType.name)) {
            if (variant.buildType.name == buildTypeName) action(variant)
            buildTypeNames.add(variant.buildType.name)
        }
    }
}

fun Project.forEachVariantSourceFolder(action: (File) -> Unit) {
    project.forEachVariant { variant ->
        variant.getSourceFolders(SourceKind.JAVA).forEach { ft ->
            action(ft.dir)
        }
    }
}

fun Project.getDependencySet(type: String): DependencySet? {
    var result: DependencySet? = null
    configurations.all { configuration ->
        if (configuration.name == type) {
            result = configuration.dependencies
        }
    }
    return result
}

fun Project.findProjectDependencies (
    vararg types: String
): List<Pair<String, Project>> = mutableListOf<Pair<String, Project>>().apply {
    types.forEach { type ->
        getDependencySet(type)?.forEach { dependency ->
            if (dependency is DefaultProjectDependency) {
                add(type to  dependency.dependencyProject)
            }
        }
    }
}

fun Project.createDependencyType(type: String, extendsFrom: String) =
    configurations.findByName(extendsFrom).let { extends ->
        configurations.create(type).apply {
            if (null != extends) extendsFrom(extends)
        }.dependencies
    }

fun Project.addDirDependency(type: String, dir: File, nameLike: String = "*.jar") {
    Logger.p(LogTags.PLUGIN , this) { " ADD_DEPENDENCIES ($type) DIR:${dir.absolutePath} nameLike:$nameLike" }
    getDependencySet(type)?.add(dependencies.create(fileTree(mapOf( "dir" to dir,"include" to listOf(nameLike)))))
}

fun Project.addDirDependency(type: String, flavor: String, dir: File, nameLike: String = "*.jar") {
    addDirDependency(flavor.flavorDependencyType(type), dir, nameLike)
}

fun Project.addDependency(type: String, groupId: String, artifactId: String, version: String) {
    addDependency(type, "$groupId:$artifactId:$version")
}

fun Project.addDependency(type: String, flavor:String, groupId: String, artifactId: String, version: String) {
    addDependency(flavor.flavorDependencyType(type), groupId, artifactId, version)
}

fun Project.addDependency(type: String, depPath: String) {
    Logger.p(LogTags.PLUGIN, this) { " ADD_DEPENDENCIES ($type) PATH:${depPath}" }
    getDependencySet(type)?.add(dependencies.create(depPath))
}

fun Project.addProjectDependency(type: String, projectDependency: Project) {
    Logger.p(LogTags.PLUGIN, this) { " ADD_DEPENDENCIES ($type) PROJECT:${projectDependency.path}" }
    getDependencySet(type)?.add(dependencies.create(projectDependency))
}

fun Project.removeProjectDependency(type: String, projectDependency: Project) {
    Logger.p(LogTags.PLUGIN, this) { " REMOVE_DEPENDENCIES ($type) PROJECT:${projectDependency.path}" }
    getDependencySet(type)?.remove(dependencies.create(projectDependency))
}

fun Project.addProjectDependency(type: String, flavor:String, projectDependency: Project) {
    addProjectDependency(flavor.flavorDependencyType(type), projectDependency)
}

fun Project.addToJavaSourceSet(toName: String, absolutePath: String) {
    androidExtension?.sourceSets?.let { sourceSets ->
        sourceSets.forEach { sourceSet ->
            if (sourceSet.name == toName) {
                Logger.p(LogTags.PLUGIN, this) { " ADD JAVA SOURCE_SET FILE  (${sourceSet.name}) file:$absolutePath" }
                sourceSet.java.srcDirs(project.file(absolutePath))
            }
        }
    }
}

private val Project.kotlinProjectExtension: KotlinAndroidProjectExtension?
    get() = extensions.findByName(KOTLIN_PROJECT_EXTENSION_NAME) as? KotlinAndroidProjectExtension


private val Project.kotlinTarget: AbstractKotlinTarget?
    get() = kotlinProjectExtension?.target

fun Project.forEachVariantKotlinCompilation(action: (BaseVariant, KotlinCompilation<KotlinCommonOptions>?) -> Unit) {
    kotlinTarget?.let { target ->
        forEachVariant { variant ->
            action(variant, target.compilations.getByName(variant.name))
        }
    }
}

fun Project.forEachVariantKotlinTask(action: (BaseVariant, KotlinCompile? /*kotlin*/, TaskProvider<JavaCompile> /*java*/) -> Unit) {
    forEachVariantKotlinCompilation { variant, compilation ->
        (compilation?.compileKotlinTask as? KotlinCompile)?.let { compile ->
            action(variant, compile, variant.javaCompileProvider)
        }
    }
}

fun Project.getKotlinCompileTaskByVariant(variantName: String): KotlinCompile? {
    var result: KotlinCompile? = null
    kotlinTarget?.let { target ->
        val compilation: KotlinCompilation<KotlinCommonOptions>? = target.compilations.getByName(variantName)
        result = compilation?.compileKotlinTask as? KotlinCompile
    }
    return result
}

fun Project.whenExecuted(action: Project.() -> Unit) {
    if (state.executed) {
        action(this)
    } else {
        afterEvaluate {
            action(this)
        }
    }
}

fun Project.applyMaven() {
    if (!pluginManager.hasPlugin(MAVEN_PUBLISH)) pluginManager.apply(MAVEN_PUBLISH)
}

fun Project.applyKapt() {
    if (!pluginManager.hasPlugin(KOTLIN_KAPT)) pluginManager.apply(KOTLIN_KAPT)
}

val Project.snackPath
    get() = path.removePrefix(":").replace(":", "_").toLowerCase(Locale.ROOT)

fun Project.propertyOr(name: String, default: () -> String) =
    properties[name].let { value ->
        if (!value?.toString().isNullOrBlank()) value.toString() else default()
    }

fun Project.propertyOr(name: String, default: Boolean) = properties[name] as? Boolean ?: default

val BaseVariant.outputAar
    get() = outputs.find { output -> output.outputFile.path.endsWith(".aar") }?.outputFile

fun String.flavorDependencyType(type: String) = toCamel(this, type)