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

import com.blueprint.kotlin.lang.element.KbpElement
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.codebase.invoker.InvokeElementCodeBase
import com.squareup.kotlinpoet.TypeName

/**
 * The code structure of Router Api.
 *
 * @since 1.1
 */
class ApiCodeBase(
    path: String,
    version: String,
    navigationOnly: Boolean,
    pathSectionOptimize: Boolean,
    val originalInvoker: InvokeElementCodeBase,
    val defineResultType: String?,
    val forResult: Boolean = false
) : RouteCodeBase(path, version, navigationOnly, pathSectionOptimize) {
    companion object {
        operator fun invoke(
            elementPool: ElementPool,
            element: KbpElement,
            path: String,
            version: String,
            defineResultType: String? = null,
            navigationOnly: Boolean,
            forResult: Boolean,
            pathSectionOptimize: Boolean
        ): ApiCodeBase? {
            return InvokeElementCodeBase(elementPool, element)?.let { invoker ->
                ApiCodeBase(
                    path,
                    version,
                    navigationOnly,
                    pathSectionOptimize,
                    invoker,
                    defineResultType,
                    forResult
                )
            }
        }
    }

    fun contextInvoker(uri: String) = InvokeContextCodeBase(this, uri)
}



