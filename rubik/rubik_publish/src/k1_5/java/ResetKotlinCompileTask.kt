package com.rubik.publish.task.provider

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

fun KotlinCompile.resetDestinationDir(dir: File) {
    this.destinationDir = dir
}

fun KotlinCompile.resetSource(sourceDir: File) {
    source = project.fileTree(sourceDir)
}