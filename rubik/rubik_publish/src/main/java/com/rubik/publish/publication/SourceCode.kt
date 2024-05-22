package com.rubik.publish.publication

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.propertyOr
import com.ktnail.x.camelToPascal
import com.ktnail.x.snakeToCamel
import com.ktnail.x.toCamel
import com.rubik.context.extra.Context
import com.rubik.context.Ext
import com.rubik.context.folder.getLibsTmpDir
import com.rubik.publish.publishLibArtifactId
import com.rubik.publish.task.provider.JarTaskProvider
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

class SourceCode(
    val context: Context,
    val project: Project,
    typeName: String,
    val variant: BaseVariant,
    override val jarFromDir: File,
    libTmpDirRoot: File
) : Jar {
    override val artifactId = context.publishLibArtifactId(typeName)

    override val jarToDir =  getLibsTmpDir(libTmpDirRoot, context.uri, typeName)

    override val jarTaskName: String =
        toCamel("jar", "rubik", variant.name.camelToPascal(), typeName.snakeToCamel())

    fun getJarSourceTask(project: Project): Task? =
        if (project.propertyOr(Ext.RUBIK_PUBLISH_CONTEXT_LIB_SOURCE, true))
            JarTaskProvider(project).getByLib(this)
        else null
}
