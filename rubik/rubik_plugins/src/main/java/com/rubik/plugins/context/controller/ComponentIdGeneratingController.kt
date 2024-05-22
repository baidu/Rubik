package com.rubik.plugins.context.controller

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.toCamel
import com.rubik.context.extra.Context
import com.rubik.context.extra.makeRubikGeneratingDir
import com.rubik.context.extra.rubikTask
import com.rubik.plugins.context.generate.file.IdentitySourceFile
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

class ComponentIdGeneratingController(
    private val project: Project,
    private val context: Context
) {
    fun createTask(
        artifactName: String,
        version: String,
        variant: BaseVariant
    ) {
        val generateDir = project.makeRubikGeneratingDir(variant)
        variant.registerJavaGeneratingTask(
            makeGenerateFileTask(
                artifactName,
                version,
                variant,
                generateDir
            ), generateDir
        )
    }

    private fun makeGenerateFileTask(
        artifactName: String,
        version: String,
        pubVariant: BaseVariant,
        generateDir: File
    ): Task {
        return project.rubikTask(
            toCamel(
                "generate",
                pubVariant.name,
                artifactName,
                "RubikComponentId"
            )
        ) { task ->
            task.doLast {
                doGenerate(version, pubVariant, generateDir)
            }
        }
    }

    private fun doGenerate(
        pubVersion: String,
        pubVariant: BaseVariant,
        generateDir: File
    ) {
        IdentitySourceFile(
            context,
            pubVersion,
            pubVariant.name,
            generateDir
        ).generate()
    }

}