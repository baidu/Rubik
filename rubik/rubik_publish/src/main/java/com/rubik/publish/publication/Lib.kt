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
package com.rubik.publish.publication

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.maven.MavenDependency
import com.ktnail.gradle.maven.Publication
import com.ktnail.x.*
import com.rubik.context.extra.Context
import com.rubik.context.publication.BuildType
import com.rubik.context.publication.LibType
import com.rubik.context.folder.getGeneratedSourceDir
import com.rubik.context.folder.getLibClassesTmpDir
import com.rubik.context.folder.getLibsTmpDir
import com.rubik.publish.publishGroupId
import com.rubik.publish.publishLibArtifactId
import com.rubik.publish.task.provider.CopySourceTaskProvider
import com.rubik.publish.task.provider.KotlinCompileTaskProvider
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
    val context: Context,
    private val project: Project,
    private val libType: String,
    baseName: String,
    val variant: BaseVariant,
    override val versionCreator: VersionCreator,
    private val libTmpDirRoot: File
) : Publication(), Jar {
    override val publicationName = toPascal(baseName, libType.snakeToPascal(), BuildType.CONTEXT_LIB_BUILD_TYPE_NAME)

    override val artifactId = context.publishLibArtifactId(libType)

    override val groupId = context.publishGroupId

    override val publicationFile = jarToFile(libTmpDirRoot, context.uri, libType)

    fun getKotlinCompileTask(project: Project, compilerVariant: BaseVariant): Task =
        KotlinCompileTaskProvider(project).getByLib(this, compilerVariant.name)

    // source
    val sourceDir = getGeneratedSourceDir(project, variant.name, context.uri)

    fun getCopySourceTask(project: Project): Task =
        source.let { CopySourceTaskProvider(project).getByLib(this) }

    val source: SourceCode?
        get() = when (libType) {
            LibType.CONTEXT -> SourceCode(
                context,
                project,
                LibType.CONTEXT_SOURCES,
                variant,
                sourceDir,
                libTmpDirRoot
            )
            else -> null
        }

    override val sourceFile: File?
        get() = source?.jarToFile(libTmpDirRoot, context.uri, LibType.CONTEXT_SOURCES)

    // pom
    override val addDependencyTypes = emptyList<String>()

    override val exceptDependencies: List<MavenDependency>? = null

    override val extraDependencies: List<MavenDependency>? = null

    // jar
    override val jarToDir = getLibsTmpDir(libTmpDirRoot, context.uri, libType)

    override val jarFromDir = getLibClassesTmpDir(project, libType, variant.name, context.uri)

    override val jarTaskName = toCamel("jar", "rubik", variant.name.camelToPascal(), libType.snakeToCamel())

    companion object {
        fun String.nameToLibArtifactId(libType: String) = toSnake(false, "-" ,this, "lib", libType).replace("_", "-")
        fun String.versionToDevVersion() = "${this}-DEV"
        fun String.versionToCodeVersion() = "${this}-CODE"
    }
}
