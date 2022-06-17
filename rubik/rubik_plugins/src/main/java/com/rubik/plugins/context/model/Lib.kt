/**
 * Copyright (C) Baidu Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rubik.plugins.context.model

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.*
import com.rubik.plugins.basic.publish.maven.Publication
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.context.ContextPlugin
import com.rubik.plugins.context.task.provider.CompileTaskProvider
import com.rubik.plugins.context.task.provider.JarTaskProvider
import com.rubik.plugins.context.task.provider.SourceTaskProvider
import com.rubik.plugins.extension.context.ContextExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

/**
 * Model of context lib.
 *
 * @since 1.4
 */
class Lib(
    val context: ContextExtension,
    private val project: Project,
    private val libType: String,
    val variant: BaseVariant,
    private val libTmpDirRoot: File
): Publication {
    object Type {
        const val CONTEXT = "context"
        const val ORIGINAL_VALUE = "original_value"
        const val CONTEXT_SOURCES = "context_sources"
    }

    val classesDir = getLibClassesTmpDir(project, libType, variant.name, context.uri)
    val sourceDir = getGeneratedSourceDir(project, variant.name, context.uri)

    val jarToDir = getLibsTmpDir(libTmpDirRoot, context.uri, libType)
    val jarFromDir = when (libType) {
        Type.CONTEXT_SOURCES -> sourceDir
        else -> classesDir
    }

    override val publicationName = toPascal(context.publishArtifactName, libType.snakeToPascal(), ContextPlugin.CONTEXT_LIB_BUILD_TYPE_NAME)
    override val devPublicationName = toPascal(context.publishArtifactName, "dev", libType.snakeToPascal(), ContextPlugin.CONTEXT_LIB_BUILD_TYPE_NAME)
    override val artifactId = context.publishLibArtifactId(libType)
    override val groupId = context.publishGroupId
    override val version = project.propertyOr(Ext.R_PUB_VERSION) {
        context.versionToPublish.toString()
    }
    override val devVersion = project.propertyOr(Ext.R_PUB_VERSION) {
        context.versionToPublishDev.toString()
    }
    private val libName = "$artifactId.jar"
    override val publicationFile = File("${getLibsTmpDirPath(libTmpDirRoot, context.uri, libType)}${File.separator}${libName}")
    override val source: Lib?
        get() = if (project.propertyOr(Ext.RUBIK_PUBLISH_CONTEXT_LIB_SOURCE, true))
            when (libType) {
                Type.CONTEXT -> Lib(context, project, Type.CONTEXT_SOURCES, variant, libTmpDirRoot)
                else -> null
            } else null
    override val addDependencyTypes = emptyArray<String>()
    override val addDependencies: Array<Publication> = emptyArray()

    val jarTaskName = toCamel(
        "jar",
        variant.buildType.name.camelToPascal(),
        libType.snakeToCamel()
    )

    fun getCompileTask(project: Project): KotlinCompile? =
        if (libType == Type.CONTEXT && context.useResetCompiler) CompileTaskProvider(project).getByLib(this) else null

    fun getJarTask(project: Project): Task =
        JarTaskProvider(project).getByLib(this)

    fun getJarSourceTask(project: Project): Task? =
        source?.let { sourceLib -> JarTaskProvider(project).getByLib(sourceLib) }

    fun getSourceTask(project: Project): Task? =
        if (libType == Type.CONTEXT) SourceTaskProvider(project).getByLib(this) else null

    companion object {
        fun String.nameToLibArtifactId(libType: String) = toSnake(false, this, "lib", libType).replace("_", "-")
        fun String.versionToDevVersion() = "${this}-DEV"
    }
}
