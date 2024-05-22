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
package com.rubik.apt.codebase.invoker

import com.blueprint.kotlin.lang.element.*
import com.blueprint.kotlin.pool.ElementPool
import com.ktnail.x.NameBox
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.ClassMirrorable
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.TokenName
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.api.ResultCodeBase
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

open class InvokeOriginalCodeBase(
    type: InvokeElementType,
    name: String,
    static: Boolean,
    queries: List<QueryCodeBase>,
    result: ResultCodeBase?,
    val clazz: InvokeHandle
) : InvokeFunctionCodeBase(type, name, static, queries, result) {
    companion object {
        fun KbpRooterElement.toInvokeOriginalCodeBases(elementPool: ElementPool): List<InvokeOriginalCodeBase> =
            mutableListOf<InvokeOriginalCodeBase>().also { result ->
                jmElement?.let { classElement ->
                    constructors.forEach { (_, constructor) ->
                        result.add(
                            InvokeOriginalCodeBase(
                                type = InvokeElementType.CONSTRUCTOR,
                                name = classElement.qualifiedName.toString(),
                                static = false,
                                queries = QueryCodeBase(constructor.parameters, elementPool),
                                result = ResultCodeBase(constructor.returnType),
                                clazz = InvokeHandle(classElement.qualifiedName.toString(), false)
                            )
                        )
                    }
                }
            }

        fun KbpConstructorElement.toInvokeOriginalCodeBase(elementPool: ElementPool) =
            (jmElement?.enclosingElement as? TypeElement)?.let { classElement ->
                InvokeOriginalCodeBase(
                    type = InvokeElementType.CONSTRUCTOR,
                    name = classElement.qualifiedName.toString(),
                    static = false,
                    queries = QueryCodeBase(parameters, elementPool),
                    result = ResultCodeBase(returnType),
                    clazz = InvokeHandle(classElement.qualifiedName.toString(), false)
                )
            }

        fun KbpFunctionElement.toInvokeOriginalCodeBase(elementPool: ElementPool) =
            jmElement?.let { functionElement ->
                (functionElement.enclosingElement as? TypeElement)?.let { classElement ->
                    InvokeOriginalCodeBase(
                        type = InvokeElementType.METHOD,
                        name = name,
                        static = functionElement.modifiers.contains(Modifier.STATIC),
                        queries = QueryCodeBase(parameters, elementPool),
                        result = ResultCodeBase(returnType),
                        clazz = InvokeHandle(classElement.qualifiedName.toString(), classElement.modifiers.contains(Modifier.STATIC))
                    )
                }
            }

        fun KbpHighOrderFunctionElement.toInvokeOriginalCodeBase() =
            jmElement?.let { functionElement ->
                (functionElement.enclosingElement as? TypeElement)?.let { classElement ->
                    InvokeOriginalCodeBase(
                        type = InvokeElementType.HIGHER_ORDER_FUNC,
                        name = name,
                        static = functionElement.modifiers.contains(Modifier.STATIC),
                        queries = QueryCodeBase(parameters, Constants.Apis.FUNCTION_ARGUMENTS),
                        result = ResultCodeBase(returnType),
                        clazz = InvokeHandle(classElement.qualifiedName.toString(), classElement.modifiers.contains(Modifier.STATIC))
                    )
                }
            }

        fun KbpVariableElement.toInvokeOriginalCodeBase() = jmElement?.let { propertyElement ->
            (propertyElement.enclosingElement as? TypeElement)?.let { classElement ->
                InvokeOriginalCodeBase(
                    type = InvokeElementType.PROPERTY,
                    name = name,
                    static = propertyElement.modifiers.contains(Modifier.STATIC),
                    queries = listOf(),
                    result = ResultCodeBase(type),
                    clazz = InvokeHandle(classElement.qualifiedName.toString(), classElement.modifiers.contains(Modifier.STATIC))
                )
            }
        }

        fun KbpElement.toInvokeOriginalCodeBases(elementPool: ElementPool): List<InvokeOriginalCodeBase> =
            when (this) {
                is KbpRooterElement -> toInvokeOriginalCodeBases(elementPool)
                is KbpConstructorElement -> listOfNotNull(toInvokeOriginalCodeBase(elementPool))
                is KbpFunctionElement -> listOfNotNull(toInvokeOriginalCodeBase(elementPool))
                is KbpHighOrderFunctionElement -> listOfNotNull(toInvokeOriginalCodeBase())
                is KbpVariableElement -> listOfNotNull(toInvokeOriginalCodeBase())
                else -> listOf()
            }

    }

    init {
        NameBox().let { box ->
            queries.forEach { query ->
                query.callback?.functions?.forEach { function ->
                    function.useName = Constants.Apis.toCallbackName(box.useName(function.name))
                }
            }
        }
    }

    var instance: Instanceable? = null

    val parameters
        get() = listOfNotNull(objectParameter) + (instanceInvoker?.queries ?: listOf()) + queries

    val instanceInvoker: InvokeOriginalCodeBase?
        get() = (instance as? OriginalInvokable)?.invoker

    val objectParameter: QueryCodeBase?
        get() = (instance as? ClassMirrorable)?.let { type ->
            QueryCodeBase(
                Constants.Object.OBJECT_INSTANCE_PARAMETER_NAME, type.originalType,
                isExtendThis = false,
                isVarargs = false,
                callback = null
            )
        }

    val location
        get() = if (type == InvokeElementType.CONSTRUCTOR) clazz.name else "${clazz.name}.$name"

    override val tokenList
        get() = TokenList(
            TokenName(name),
            static,
            instance,
            parameters,
            result,
            TokenName(clazz.name),
            clazz.static,
            key =  "IVO",
            warp = false
        )
}

data class InvokeHandle(
    val name: String,
    val static: Boolean
)