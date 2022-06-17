/**
 * Copyright (C) Baidu Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rubik.plugins.context.task.provider

import com.ktnail.x.emptyRecursively
import com.ktnail.x.toCamel
import com.rubik.plugins.basic.exception.RubikNoKotlinCompileFoundException
import com.rubik.plugins.basic.exception.RubikNoSourceToCompileException
import com.rubik.plugins.context.model.Lib
import com.rubik.plugins.basic.utility.getKotlinCompileTaskByVariant
import com.rubik.plugins.basic.utility.rubikTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CompileTaskProvider(val project: Project) {
    fun getByLib(lib: Lib): KotlinCompile {
        return (project.getKotlinCompileTaskByVariant(lib.variant.name) ?: throw RubikNoKotlinCompileFoundException(
            lib.variant.name
        )).apply {
            doFirst{
                source = project.fileTree(lib.sourceDir)
                if (lib.sourceDir.emptyRecursively()) {
                    throw RubikNoSourceToCompileException(lib.sourceDir.absolutePath)
                }
            }
        }
    }
}

fun KotlinCompile.getRedirectDestinationTask(lib: Lib): Task {
    return project.rubikTask(toCamel("redirect", "destination", "dir", "rubik", name)) {
        it.doFirst {
            lib.classesDir.deleteRecursively()
            lib.classesDir.mkdirs()
            destinationDir = lib.classesDir
        }
    }
}