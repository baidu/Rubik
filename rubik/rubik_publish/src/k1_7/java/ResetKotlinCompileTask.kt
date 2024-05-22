package com.rubik.publish.task.provider

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

fun KotlinCompile.resetDestinationDir(dir: File) {
    destinationDirectory.set(dir)
}

fun KotlinCompile.resetSource(sourceDir: File) {
    setSource(project.fileTree(sourceDir))
}