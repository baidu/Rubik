package com.rubik.plugins.shell.controller

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.packageNameToFilePath
import com.ktnail.x.toCamel
import com.rubik.context.extra.allContexts
import com.rubik.context.extra.makeRubikGeneratingDirs
import com.rubik.plugins.basic.Constants
import com.rubik.plugins.shell.generate.files.RubikIdCheckerSourceFile
import com.rubik.plugins.shell.generate.files.RubikInitializerSourceFile
import com.rubik.context.extra.rubikTask
import com.rubik.picker.extra.pickedContextsContainer
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

class ShellSourceGeneratingController(
    private val project: Project
) {
    fun createTasks() {
        project.makeRubikGeneratingDirs { generateDir, variant ->
            variant.registerJavaGeneratingTask(
                makeGenerateFileTask(
                    variant,
                    generateDir
                ), generateDir
            )
        }
    }

    private fun makeGenerateFileTask(variant: BaseVariant, generateDir: File): Task {
        return project.rubikTask(toCamel("generate", variant.name, "RubikShellSources")) { task ->
            task.doLast {
                doGenerate(generateDir, variant)
            }
        }
    }

    private fun doGenerate(generateDir: File, variant: BaseVariant) {
        cleanSourceFiles(generateDir)
        RubikInitializerSourceFile(
            pickedContextsContainer.pickCases(variant),
            generateDir
        ).generate()
        RubikIdCheckerSourceFile(
            pickedContextsContainer.pickCases(variant),
            allContexts,
            generateDir
        ).generate()
    }

    private fun cleanSourceFiles(generateDir: File) {
        File("${generateDir.absolutePath}${File.separator}${Constants.Router.GENERATE_PACKAGE_NAME.packageNameToFilePath()}").deleteRecursively()
    }
}