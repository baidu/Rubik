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
package com.rubik.apt.codebase.api

import com.ktnail.x.pathToCamel
import com.ktnail.x.pathToSnake

open class RouteCodeBase(
    val path: String,
    val version: String,
    val navigationOnly: Boolean = false,
    pathSectionOptimize: Boolean = true
) {
    private val pathSection = if (pathSectionOptimize && path.contains("/")) path.split("/") else listOf()
    val sections = if (pathSectionOptimize && pathSection.isNotEmpty()) pathSection.dropLast(1) else listOf()

    val contextFunctionName = (if (pathSectionOptimize && pathSection.isNotEmpty()) pathSection.last() else path).pathToCamel()
    val contextPropertyName = (if (pathSectionOptimize && pathSection.isNotEmpty()) pathSection.last() else path).pathToSnake(true)

    val actionFunctionName = path.pathToCamel()
}


