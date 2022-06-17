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

import com.blueprint.kotlin.lang.element.KbpVariableElement
import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.pool.ElementPool
import com.ktnail.x.camelToPascal
import com.rubik.annotations.route.RExtend
import com.rubik.apt.Constants
import com.rubik.apt.utility.isResultInvoker
import com.rubik.apt.utility.isVarargs

/**
 * The code structure of Router Query.
 *
 * @since 1.1
 */
class QueryCodeBase(
    private var name: String,
    type: KbpType,
    var isExtendThis: Boolean,
    var isVarargs: Boolean,
    val resultInvoker: ResultInvokerCodeBase?
) : TypeCodeBase(type) {
    companion object {
        operator fun invoke(
            parameters: List<KbpType>, prefix: String
        ): List<QueryCodeBase> =
            parameters.mapIndexed { index, type ->
                QueryCodeBase(
                    "$prefix$index",
                    type,
                    isExtendThis = false,
                    isVarargs = false,
                    resultInvoker = null
                )
            }

        operator fun invoke(
            parameters: List<KbpVariableElement>,
            elementPool: ElementPool
        ): List<QueryCodeBase> =
            parameters.map { variableElement ->
                QueryCodeBase(
                    variableElement.name,
                    variableElement.type,
                    isExtendThis = null != variableElement.jmElement?.getAnnotation(
                        RExtend::class.java),
                    isVarargs = variableElement.type.isVarargs(),
                    resultInvoker = if (variableElement.isResultInvoker()) ResultInvokerCodeBase(
                        variableElement,
                        elementPool
                    ) else null
                )
            }
    }

    fun addNameAssistPrefix() {
        if (!name.startsWith(Constants.Apis.PARAMETER_NAME_ASSIST_PREFIX)) {
            name = "${Constants.Apis.PARAMETER_NAME_ASSIST_PREFIX}${name.camelToPascal()}"
        }
    }

    val legalName
        get() = Constants.Apis.toLegalParameterName(name)

    val originalName
        get() = name

    fun makeGetQueryCode(name: String, type: KbpType, index: Int) =
        if (isRValue)
            Constants.Aggregate.makeGetValueQueryCode(name, type.toTypeName().toString(), index)
        else
            Constants.Aggregate.makeGetQueryCode(name, type.toTypeName().toString(), index)
}