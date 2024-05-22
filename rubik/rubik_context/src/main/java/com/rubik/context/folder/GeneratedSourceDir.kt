package com.rubik.context.folder

import com.ktnail.x.uriToSnake
import org.gradle.api.Project
import java.io.File

private fun generatedSourceDir(project: Project, variantName: String, uri: String) =
        "${project.buildDir}${File.separator}rubik${File.separator}${variantName}${File.separator}${uri.uriToSnake()}${File.separator}generated${File.separator}source"


fun getGeneratedSourceDir(project: Project, variantName: String, uri: String) =
        File(generatedSourceDir(project, variantName, uri)).apply {
                mkdirs()
        }