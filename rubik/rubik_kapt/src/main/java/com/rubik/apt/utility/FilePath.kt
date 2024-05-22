package com.rubik.apt.utility

import com.rubik.apt.plugin.Arguments
import com.rubik.apt.plugin.arguments
import java.io.File
import javax.annotation.processing.ProcessingEnvironment

fun makeDefaultGeneratedDir(processingEnv: ProcessingEnvironment) =
    processingEnv.arguments(Arguments.KAPT_GENERATED)?.let {
        File(it).apply { mkdirs() }
    }

fun makeAggregateGeneratedDir(aggregateGenerated: String?) =
    aggregateGenerated?.let {
        File(it).apply { mkdirs() }
    }
