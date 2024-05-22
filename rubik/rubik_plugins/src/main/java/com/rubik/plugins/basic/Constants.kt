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

import BY_VERSION
import com.ktnail.x.*
import org.gradle.api.Project
import java.io.File

object Constants {
    object Router {
        const val PACKAGE_NAME = "com.rubik"
        const val RUBIK_CLASS_NAME = "$PACKAGE_NAME.Rubik"
        const val INIT_FUNCTION_NAME = "init"
        const val GENERATE_BASIC_PACKAGE_NAME = "rubik.generate"
        const val GENERATE_PACKAGE_NAME = "$GENERATE_BASIC_PACKAGE_NAME.shell"
        const val GENERATE_RUBIK_INITIALIZER_FILE_NAME = "RubikInitializer"
        const val KEEP_ANNOTATION_CLASS_NAME = "androidx.annotation.Keep"
        const val METHOD_REGISTER_NAME = "registerAggregatable"

        const val GENERATE_RUBIK_ID_CHECKER_FILE_NAME = "RubikIdChecker"
        const val ID_CHECKER_FUNCTION_NAME = "idChecker"

    }

    object Contexts {
        object Declare {
            fun makeContextPackageName(uri: String): String = "${Router.GENERATE_BASIC_PACKAGE_NAME}.context.${uri.uriToSnake()}"
        }
    }

    object Aggregate {
        object Declare{
            fun makeAggregatePackageName(uri: String): String = "${Router.GENERATE_BASIC_PACKAGE_NAME}.aggregate.${uri.uriToSnake()}"
            fun makeAggregateClassName(name: String) = toPascal(name, "Aggregate")
        }
        const val REGISTER_FUNCTION_NAME = "register"
    }

    object Identity {
        fun makeComponentPackageName(uri: String): String = "${Router.GENERATE_BASIC_PACKAGE_NAME}.component.${uri.uriToSnake()}"
        const val COMPONENT_ID_BASE_CLASS_NAME = "ComponentId"

        object Declare {
            const val CONTEXT_ID_BASE_CLASS_NAME = "ContextId"
            const val AGGREGATE_ID_BASE_CLASS_NAME = "AggregateId"
        }

        fun contextIdName(name:String): String = toPascal(name, COMPONENT_ID_BASE_CLASS_NAME)
        const val COMPONENT_ID_SUPER_PACKAGE_NAME = "${Router.PACKAGE_NAME}.identity"
        const val CONTEXT_ID_SUPER_SIMPLE_CLASS_NAME = "RContextId"
        const val COMPONENT_ID_SUPER_SIMPLE_CLASS_NAME = "RComponentId"
        const val COMPONENT_ID_SUPER_CLASS_NAME = "${this.COMPONENT_ID_SUPER_PACKAGE_NAME}.${this.COMPONENT_ID_SUPER_SIMPLE_CLASS_NAME}"
    }

    object IdChecker {
        const val ID_CHECKER_PACKAGE_NAME = "com.rubik.identity"
        const val ID_CHECKER_SIMPLE_CLASS_NAME = "IdentityChecker"
        fun makeAggregateIdClassName(uri: String, name: String) =
            "\"${Aggregate.Declare.makeAggregatePackageName(uri)}.${ toPascal(name, Identity.Declare.AGGREGATE_ID_BASE_CLASS_NAME)}\""
        fun makeComponentIdClassName(uri: String, name: String) =
            "\"${Identity.makeComponentPackageName(uri)}.${ toPascal(name, Identity.COMPONENT_ID_BASE_CLASS_NAME)}\""
        fun makeContextIdClassName(uri: String, name: String) =
            "\"${Contexts.Declare.makeContextPackageName(uri)}.${ toPascal(name, Identity.Declare.CONTEXT_ID_BASE_CLASS_NAME)}\""

        const val ID_CHECKER_FUNCTION_NAME_ADD_COMPONENT_ID = "addComponentId"
        const val ID_CHECKER_FUNCTION_NAME_ADD_CONTEXT_ID = "addContextId"

    }

    object KDoc {
        fun init() =
            updateFileKDoc("init Rubik Router.", null)

        fun componentId(uri: String, version: String) =
            updateFileKDoc("generated Rubik componentId.", version, "uri" to "[$uri]")

    }
}
