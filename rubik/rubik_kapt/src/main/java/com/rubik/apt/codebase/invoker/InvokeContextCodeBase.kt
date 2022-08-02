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

import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName

class InvokeContextCodeBase(
    val receiver: QueryCodeBase?,
    val parameters: List<QueryCodeBase>,
    val hasSyncReturn: Boolean,
    val resultParameters: List<LambdaTypeCodeBase>,
    val returnType: TypeName
) {
    companion object {
        operator fun invoke(
            api: ApiCodeBase,
            contextUri: String
        ): InvokeContextCodeBase {
            val receiver = api.originalInvoker.queries.find { query -> query.isExtendThis }
            val parameters = (api.originalInvoker.assistant?.queries ?: listOf()) + api.originalInvoker.queries.filter { query ->
                null == query.resultInvoker&&!query.isExtendThis
            }
            val hasSyncReturn = api.forResult && null != api.originalInvoker.result
            val resultParameters = api.originalInvoker.getResultGroupsLambdaOrDefine(
                if (hasSyncReturn) api.defineResultType else null,
                contextUri,
                hasSyncReturn
            )
            val returnType = api.originalInvoker.getForResultOrDefine(api.defineResultType, contextUri)
            return InvokeContextCodeBase(
                receiver,
                parameters,
                hasSyncReturn,
                resultParameters,
                returnType
            )
        }

        private  fun InvokeElementCodeBase.getForResultOrDefine(defineResultType: String?, uri: String): TypeName {
            return if (defineResultType?.isNotBlank() == true)
                Class.forName(defineResultType).asTypeName()
            else
                result?.toContextTypeName(uri) ?: Unit::class.asClassName()
        }

        private fun InvokeElementCodeBase.getResultGroupsLambdaOrDefine(
            defineResultType: String?,
            uri: String,
            hasSyncReturn: Boolean
        ): List<LambdaTypeCodeBase> =
            mutableListOf<LambdaTypeCodeBase>().apply {
                val resultInvokers = queries.filter { query -> null != query.resultInvoker }
                resultInvokers.forEachIndexed { index, query ->
                    if (index == 0 && defineResultType?.isNotBlank() == true) {
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.makeFunctionResultName(size, query.legalName),
                                listOf(Class.forName(defineResultType).asTypeName()),
                                Unit::class.asClassName(),
                                query.resultInvoker?.nullable ?: false
                            )
                        )
                    } else{
                        val parameters = query.resultInvoker?.parameters?.map { codeBase -> codeBase.toContextTypeName(uri) } ?: listOf()
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.makeFunctionResultName(size, query.legalName),
                                parameters,
                                Unit::class.asClassName(),
                                query.resultInvoker?.nullable ?: false
                            )
                        )
                    }
                }
                if (!hasSyncReturn) {
                    if (defineResultType?.isNotBlank() == true && resultInvokers.isEmpty()) {
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.makeFunctionResultName(size, result?.name),
                                listOf(Class.forName(defineResultType).asTypeName()),
                                Unit::class.asClassName(),
                                result?.originalType?.nullable ?: false
                            )
                        )
                    }
                    if (isEmpty() && null != result) {
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.makeFunctionResultName(size, result.name),
                                listOf(result.toContextTypeName(uri)),
                                Unit::class.asClassName(),
                                result.originalType.nullable
                            )
                        )
                    }
                }
            }
    }
}
