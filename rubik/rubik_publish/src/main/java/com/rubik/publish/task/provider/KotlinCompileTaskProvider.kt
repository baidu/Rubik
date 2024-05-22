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
package com.rubik.publish.task.provider

import com.ktnail.x.emptyRecursively
import com.ktnail.x.toCamel
import com.ktnail.gradle.*
import com.ktnail.gradle.task.linkDependsOn
import com.rubik.context.extra.rubikTask
import com.rubik.publish.exception.RubikNoKotlinCompileFoundException
import com.rubik.publish.exception.RubikNoSourceToCompileException
import com.rubik.publish.publication.Lib
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinCompileTaskProvider(val project: Project) {
    fun getByLib(lib: Lib, compilerVariantName: String): Task {
        return (project.getKotlinCompileTaskByVariant(compilerVariantName)
            ?: throw RubikNoKotlinCompileFoundException(
                compilerVariantName
            )).apply {
            linkDependsOn(getRedirectDestinationTask(lib))
            doFirst {
                resetSource(lib.sourceDir)
                if (lib.sourceDir.emptyRecursively()) {
                    throw RubikNoSourceToCompileException(lib.sourceDir.absolutePath)
                }
            }
        }
    }

    private fun KotlinCompile.getRedirectDestinationTask(lib: Lib): Task {
        return project.rubikTask(toCamel("redirect", "destination", "dir", "rubik", name)) {
            it.doFirst {
                lib.jarFromDir.deleteRecursively()
                lib.jarFromDir.mkdirs()
                resetDestinationDir(lib.jarFromDir)
            }
        }
    }

}