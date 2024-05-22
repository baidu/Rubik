package com.rubik.publish.publication

import com.rubik.context.folder.getLibsTmpDirPath
import com.rubik.publish.task.provider.JarTaskProvider
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

interface Jar {
    val artifactId :String
    val jarToDir: File
    val jarFromDir: File
    val jarTaskName :String
}

fun Jar.getJarTask(project: Project): Task =
    JarTaskProvider(project).getByLib(this)

fun Jar.jarToFile(libTmpDirRoot: File, uri: String, typeName: String) = File(
    "${
        getLibsTmpDirPath(
            libTmpDirRoot,
            uri,
            typeName
        )
    }${File.separator}${artifactId}.jar"
)
