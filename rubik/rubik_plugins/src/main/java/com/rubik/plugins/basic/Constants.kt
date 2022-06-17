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
package com.rubik.plugins.basic

import com.ktnail.x.toPascal
import com.ktnail.x.uriToSnake
import org.gradle.api.Project
import java.io.File

object Constants {
    object Router {
        const val PACKAGE_NAME = "com.rubik"
        const val RUBIK_CLASS_NAME = "$PACKAGE_NAME.Rubik"
        const val INIT_FUNCTION_NAME = "init"
        const val GENERATE_PACKAGE_NAME = "rubik.generate.root"
        const val GENERATE_FILE_NAME = "RubikExt"
        const val KEEP_ANNOTATION_CLASS_NAME = "androidx.annotation.Keep"
        const val METHOD_REGISTER_NAME = "registerAggregatable"

    }

    object Aggregate {
        object Declare{
            private const val GENERATE_PACKAGE_NAME = "rubik.generate"
            fun makeAggregatePackageName(uri: String): String = "$GENERATE_PACKAGE_NAME.aggregate.${uri.uriToSnake()}"
            fun makeAggregateClassName(name: String) = toPascal(name, "Aggregate")
            const val REGISTER_FUNCTION_NAME = "register"
        }
    }

    object Lib {
        fun classesTmpDirPath(project: Project, libType: String,  variantName: String, uri: String) =
            "${project.buildDir}${File.separator}rubik${File.separator}${variantName}${File.separator}${uri.uriToSnake()}${File.separator}${libType}_classes"

        fun generatedSourceDir(project: Project, variantName: String, uri: String) =
            "${project.buildDir}${File.separator}rubik${File.separator}${variantName}${File.separator}${uri.uriToSnake()}${File.separator}generated${File.separator}source"

    }

    object KDoc {
        fun init() =
            """
            |init Rubik Router.
            """.trimMargin()
    }
}
