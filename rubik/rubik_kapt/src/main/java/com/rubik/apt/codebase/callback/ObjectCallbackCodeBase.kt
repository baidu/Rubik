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

import com.blueprint.kotlin.lang.element.KbpRooterElement
import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.lang.utility.toType
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.apt.codebase.ClassMirrorable
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.invoker.Callbackable
import com.rubik.apt.codebase.invoker.InvokeFunctionCodeBase
import com.rubik.apt.codebase.invoker.InvokeFunctionCodeBase.Companion.toInvokeFunctionCodeBase
import com.rubik.apt.utility.isResultCallback
import javax.lang.model.element.ElementKind

/**
 * The code structure of Router Result Callback.
 *
 * @since 1.10
 */
class ObjectCallbackCodeBase(
    override val qualifiedName: String,
    override val simpleName: String,
    override val originalType: KbpType,
    val isInterface: Boolean,
    override val functions: List<InvokeFunctionCodeBase>
) : ClassMirrorable, Callbackable, RToken {
    companion object {
        operator fun invoke(
            elementPool: ElementPool,
            classElement: KbpRooterElement
        ): ObjectCallbackCodeBase? = classElement.toType()?.let { type ->
            val isInterface = classElement.jmElement?.kind == ElementKind.INTERFACE
            classElement.functions.let { functions ->
                val resultFunctions = functions.filter { (_, function) -> function.isResultCallback() }.values.toMutableList()
                if (resultFunctions.isEmpty() && isInterface) {
                    resultFunctions.addAll(functions.values)
                }
                ObjectCallbackCodeBase(
                    classElement.qualifiedName,
                    classElement.simpleNames,
                    type,
                    isInterface,
                    resultFunctions.mapNotNull { function ->
                        function.toInvokeFunctionCodeBase(elementPool)
                    }
                )
            }
        }
    }

    override val isResult: Boolean = true

    override val transformRMirror: Boolean
        get() = isResult

    override val transformRMirrorResult: Boolean
        get() = false


    override val tokenList
        get() = TokenList(
            originalType,
            functions,
            key = "CBO",
            warp = false
        )

}
