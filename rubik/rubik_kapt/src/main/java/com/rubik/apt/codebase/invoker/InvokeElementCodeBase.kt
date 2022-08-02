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
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.api.ResultCodeBase
import com.rubik.apt.utility.invokeElementCode
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

class InvokeElementCodeBase(
    val type: InvokeElementType,
    val className: String,
    private val staticClass: Boolean,
    val name: String,
    private val static: Boolean,
    val queries: List<QueryCodeBase>,
    val result: ResultCodeBase?,
    var assistant: InvokeElementCodeBase? = null
) {
    companion object {
        operator fun invoke(
            elementPool: ElementPool,
            element: KbpElement
        ): InvokeElementCodeBase? {
            return when (element) {
                is KbpConstructorElement -> (element.jmElement?.enclosingElement as? TypeElement)?.let { classElement ->
                    InvokeElementCodeBase(
                        InvokeElementType.CONSTRUCTOR,
                        classElement.qualifiedName.toString(),
                        false,
                        classElement.qualifiedName.toString(),
                        false,
                        QueryCodeBase(element.parameters, elementPool),
                        ResultCodeBase(element.returnType)
                    )
                }
                is KbpFunctionElement -> element.jmElement?.let { functionElement ->
                    (functionElement.enclosingElement as? TypeElement)?.let { classElement ->
                        InvokeElementCodeBase(
                            InvokeElementType.METHOD,
                            classElement.qualifiedName.toString(),
                            classElement.modifiers.contains(Modifier.STATIC),
                            element.name,
                            functionElement.modifiers.contains(Modifier.STATIC),
                            QueryCodeBase(element.parameters, elementPool),
                            ResultCodeBase(element.returnType)
                        )
                    }
                }
                is KbpHighOrderFunctionElement -> element.jmElement?.let { functionElement ->
                    (functionElement.enclosingElement as? TypeElement)?.let { classElement ->
                         InvokeElementCodeBase(
                             InvokeElementType.HIGHER_ORDER_FUNC,
                             classElement.qualifiedName.toString(),
                             classElement.modifiers.contains(Modifier.STATIC),
                             element.name,
                             functionElement.modifiers.contains(Modifier.STATIC),
                             QueryCodeBase(element.parameters, Constants.Apis.FUNCTION_ARGUMENTS),
                             ResultCodeBase(element.returnType)
                        )
                    }
                }
                is KbpVariableElement -> element.jmElement?.let { propertyElement ->
                    (propertyElement.enclosingElement as? TypeElement)?.let { classElement ->
                        InvokeElementCodeBase(
                            InvokeElementType.PROPERTY,
                            classElement.qualifiedName.toString(),
                            classElement.modifiers.contains(Modifier.STATIC),
                            element.name,
                            propertyElement.modifiers.contains(Modifier.STATIC),
                            listOf(),
                            ResultCodeBase(element.type)
                        )
                    }
                }
                else -> null
            }
        }
    }

    val location
        get() = "$className.$name"

    val queriesKDoc
        get() =  queries.map { queryCodeBase -> "${queryCodeBase.originalName} : ${queryCodeBase.originalType}" }

    val resultKDoc
        get() =  result?.originalType.toString()

    fun invokeCode(castAllQuery: Boolean, processExt: Boolean): String = invokeElementCode(
        type,
        staticClass,
        className,
        static,
        name,
        provideInstanceCode(castAllQuery, processExt),
        queryNamesCode(castAllQuery, processExt)
    ).let { code ->
        if (null != result && result.isRValue) {
            Constants.Aggregate.makeToTypeCode(code)
        } else {
            code
        }
    }

    private fun queryNamesCode(castAllQuery: Boolean, processExt: Boolean) = queries.joinToString(",\n") { query ->
        val needTransform = null != query.resultInvoker && query.resultInvoker.needTransform
        var code = when {
            query.isExtendThis && processExt -> "this"
            needTransform -> Constants.Apis.toResultTransformName(query.legalName)
            else -> query.callName
        }
        if (query.isRValue && !needTransform) {
            code = Constants.Aggregate.makeToTypeCode(code)
        } else {
            if (castAllQuery) code = Constants.Aggregate.makeCaseToTypeCode(code)
        }
        "  $code"
    }.let { code ->
        if (code.isNotBlank()) {
            "\n$code\n"
        } else {
            ""
        }
    }

    private fun provideInstanceCode(castAllQuery: Boolean, processExt: Boolean): String? =
        if (type == InvokeElementType.CONSTRUCTOR) "" else assistant?.invokeCode(castAllQuery, processExt)


}

