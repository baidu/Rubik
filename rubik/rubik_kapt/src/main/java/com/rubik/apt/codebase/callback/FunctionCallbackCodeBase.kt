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
package com.rubik.apt.codebase.callback

import com.blueprint.kotlin.lang.element.KbpHighOrderFunctionElement
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.invoker.Callbackable
import com.rubik.apt.codebase.invoker.InvokeFunctionCodeBase
import com.rubik.apt.codebase.invoker.InvokeFunctionCodeBase.Companion.toInvokeFunctionCodeBase
import com.rubik.apt.utility.isResultCallback

class FunctionCallbackCodeBase(
    val function: InvokeFunctionCodeBase,
    override val isResult: Boolean
) : RToken, Callbackable {
    companion object {
        operator fun invoke(
            element: KbpHighOrderFunctionElement
        ): FunctionCallbackCodeBase? = element.toInvokeFunctionCodeBase()?.let {
            FunctionCallbackCodeBase(
                it,
                element.isResultCallback()
            )
        }
    }

    override val transformRMirror: Boolean
        get() = function.containRMirror

    override val transformRMirrorResult: Boolean
        get() = if (!isResult) function.result?.isRMirror == true else false

    override val functions: List<InvokeFunctionCodeBase>
        get() = listOf(function)

    override val tokenList
        get() = TokenList(
            function,
            isResult,
            key = "CBF",
            warp = false
        )
}