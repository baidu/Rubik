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

import com.ktnail.gradle.propertyOr
import com.ktnail.x.camelToPascal
import com.ktnail.x.emptyRecursively
import com.ktnail.x.toCamel
import com.rubik.context.Ext
import com.rubik.context.extra.rubikTask
import com.rubik.publish.exception.RubikNoSourceToCompileException
import com.rubik.publish.publication.Lib
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

class CopySourceTaskProvider(val project: Project) {
    fun getByLib(lib: Lib): Task =
        project.rubikTask(
            toCamel(
                "copy",
                "source",
                "rubik",
                lib.variant.name.camelToPascal(),
                "context",
                "sources"
            )
        ) {
            it.doFirst {
                val kaptSourceRootDir = project.propertyOr(Ext.RUBIK_KAPT_SOURCE_FOLDER) { "generated/source/kaptKotlin" }
                val kaptSourceDir = File(project.buildDir, kaptSourceRootDir + File.separator + lib.variant.name + File.separator + "rubik")
                if (kaptSourceDir.emptyRecursively())
                    throw RubikNoSourceToCompileException(kaptSourceDir.absolutePath)
                else
                    lib.sourceDir.let { sourceDir ->
                        sourceDir.deleteRecursively()
                        kaptSourceDir.copyRecursively(File(sourceDir, "rubik").apply { mkdirs() })
                    }
            }
        }

}