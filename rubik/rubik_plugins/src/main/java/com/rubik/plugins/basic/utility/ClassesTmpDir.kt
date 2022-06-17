package com.rubik.plugins.basic.utility

import com.ktnail.x.Logger
import com.rubik.plugins.basic.Constants
import org.gradle.api.Project
import java.io.File


fun getLibClassesTmpDir(project: Project, libType: String, variantName: String, uri: String) = File(
        Constants.Lib.classesTmpDirPath(project, libType, variantName, uri)).apply {
                mkdirs()
        }

fun cleanLibClassesTmpDir(project: Project, libType: String, variantName: String, uri: String) =
        File(Constants.Lib.classesTmpDirPath(project, libType, variantName, uri)).apply {
                deleteRecursively()
                mkdirs()
        }

