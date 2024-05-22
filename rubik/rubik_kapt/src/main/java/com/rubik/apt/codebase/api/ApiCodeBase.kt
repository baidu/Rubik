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

import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.TokenName
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase
import com.rubik.apt.codebase.invoker.OriginalInvokable

/**
 * The code structure of Router Api.
 *
 * @since 1.1
 */
open class ApiCodeBase(
    override val invoker: InvokeOriginalCodeBase,
    path: String,
    version: String,
    val defineResultType: String? = null,
    navigationOnly: Boolean,
    val syncReturn: Boolean = false,
    pathSectionOptimize: Boolean
) : RouteCodeBase(path, version, navigationOnly, pathSectionOptimize), OriginalInvokable, RToken {

    fun contextInvoker(uri: String) = InvokeContextCodeBase(this, uri)

    fun objectContextInvoker(uri: String) = InvokeContextCodeBase(this, uri, inObject = true)

    override fun resetCrashPath(crash: RouteCodeBase) {
        resetPath = resetPath ?: (path + invoker.parameterPathSuffix)

        if (crash.versionPath == versionPath)
            super.resetCrashPath(crash)
    }

    override fun location() = invoker.location

    override val tokenList
        get() = TokenList(
            TokenName(versionPath),
            defineResultType,
            navigationOnly,
            syncReturn,
            pathSectionOptimize,
            invoker,
            key = "API",
            warp = false
        )

}



