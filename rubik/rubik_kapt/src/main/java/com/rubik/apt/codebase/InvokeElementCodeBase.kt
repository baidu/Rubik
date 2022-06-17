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

import com.blueprint.kotlin.lang.element.*
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.api.ResultCodeBase
import com.rubik.apt.utility.invokeElementCode
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

class InvokeElementCodeBase(
    val type: InvokeElementType,
    private val className: String,
    private val staticClass: Boolean,
    private val name: String,
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

    val invokeCode: String
        get() = invokeElementCode(
            type,
            staticClass,
            className,
            static,
            name,
            providerCode,
            queryNamesCode
        )

    val hasResultInvoker = null != queries.find { codeBase -> null != codeBase.resultInvoker }

    private val queryNamesCode
        get() = queries.joinToString(",") { codeBase ->
            if (codeBase.isVarargs)
                "*${codeBase.legalName}"
            else
                codeBase.legalName
        }

    private val providerCode: String?
        get() = if (type == InvokeElementType.CONSTRUCTOR) "" else assistant?.invokeCode

    fun getForResultOrDefine(defineResultType: String?, uri: String): TypeName {
        return if (defineResultType?.isNotBlank() == true)
            Class.forName(defineResultType).asTypeName()
        else
            result?.toTypeName(uri)?:Unit::class.asClassName()
    }

    fun getResultGroupsLambdaOrDefine(
        defineResultType: String?,
        uri: String
    ): List<Pair<String?, TypeName>> =
        mutableListOf<Pair<String?, TypeName>>().apply {
             if (defineResultType?.isNotBlank() == true) {
                 add(
                     result?.name to LambdaTypeName.get(
                         parameters = *arrayOf(Class.forName(defineResultType).asTypeName()),
                         returnType = Unit::class.asClassName()
                     ).copy(nullable = result?.type?.nullable ?: false)
                 )
            } else {
                queries.filter { query -> null != query.resultInvoker }.forEach { query ->
                    add(
                        query.legalName to LambdaTypeName.get(
                            parameters = *(query.resultInvoker?.parameters?.map { codeBase -> codeBase.toTypeName(uri) } ?: listOf()).toTypedArray(),
                            returnType = Unit::class.asClassName()
                        ).copy(nullable = query.resultInvoker?.nullable?:false)
                    )
                }
                if (isEmpty() && null != result) {
                    add(
                        result.name to LambdaTypeName.get(
                            parameters = *arrayOf(result.toTypeName(uri)),
                            returnType = Unit::class.asClassName()
                        ).copy(nullable = result.type.nullable)
                    )
                }
            }
        }
}

