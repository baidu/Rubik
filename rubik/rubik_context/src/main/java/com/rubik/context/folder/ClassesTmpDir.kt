package com.rubik.context.folder

import com.ktnail.x.uriToSnake
import org.gradle.api.Project
import java.io.File

private fun classesTmpDirPath(project: Project, libType: String, variantName: String, uri: String) =
        "${project.buildDir}${File.separator}rubik${File.separator}${variantName}${File.separator}${uri.uriToSnake()}${File.separator}${libType}_classes"

fun getLibClassesTmpDir(project: Project, libType: String, variantName: String, uri: String) = File(
        classesTmpDirPath(project, libType, variantName, uri)
).apply {
        mkdirs()
}

fun cleanLibClassesTmpDir(project: Project, libType: String, variantName: String, uri: String) =
        File(classesTmpDirPath(project, libType, variantName, uri)).apply {
                deleteRecursively()
                mkdirs()
        }

