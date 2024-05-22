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
package com.rubik.apt.codebase

import com.ktnail.x.pathToCamel
import com.ktnail.x.pathToSnake
import com.ktnail.x.uri.buildVersionPath

abstract class RouteCodeBase(
    private val originalPath: String,
    val version: String,
    val navigationOnly: Boolean,
    val pathSectionOptimize: Boolean
) {
    var resetPath: String? = null

    val path
        get() = resetPath ?: originalPath

    private val pathSection
        get() = if (pathSectionOptimize && path.contains("/")) path.split("/") else listOf()

    val sections
        get() = if (pathSectionOptimize && pathSection.isNotEmpty()) pathSection.dropLast(1) else listOf()

    val contextFunctionName
        get() = (if (pathSectionOptimize && pathSection.isNotEmpty()) pathSection.last() else path).pathToCamel()

    val contextPropertyName
        get() = (if (pathSectionOptimize && pathSection.isNotEmpty()) pathSection.last() else path).pathToSnake(true)

    val actionFunctionName
        get() = path.pathToCamel()

    val versionPath
        get() = buildVersionPath(path, version)

    fun isSamePath(other: RouteCodeBase) = path == other.path

    abstract fun location(): String

    open fun resetCrashPath(crash: RouteCodeBase) {
        throw RuntimeException(" Duplicate Rubik Route Path：[$versionPath] ! Between [${location()}] and [${crash.location()}] !")
    }
}
