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

import com.blueprint.kotlin.lang.type.toKotlinTypeName
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName

class InvokeContextCodeBase(
    val receiver: QueryCodeBase?,
    private val objectInstance: QueryCodeBase?,
    val parameters: List<QueryCodeBase>,
    val hasSyncReturn: Boolean,
    val resultParameters: List<LambdaTypeCodeBase>,
    val returnType: TypeName
) {
    companion object {
        operator fun invoke(
            api: ApiCodeBase,
            contextUri: String,
            inObject: Boolean = false
        ): InvokeContextCodeBase {
            val receiver = api.invoker.parameters.find { query -> query.isExtendThis }

            var objectInstance: QueryCodeBase? = null

            val parameter = api.invoker.parameters.filter { query ->
                null == query.resultCallbacks && !query.isExtendThis
            }.let {
                if (inObject) it.filter { query ->
                    (query.originalName != Constants.Object.OBJECT_INSTANCE_PARAMETER_NAME).also { check ->
                        if (!check) objectInstance = query
                    }
                } else it
            }

            val hasSyncReturn = api.syncReturn && null != api.invoker.result

            val resultParameters = api.invoker.getResultGroupsLambdaOrDefine(
                api.defineResultType,
                contextUri,
                hasSyncReturn
            )

            val returnType = api.invoker.getForResultOrDefine(api.defineResultType, contextUri)

            return InvokeContextCodeBase(
                receiver,
                objectInstance,
                parameter,
                hasSyncReturn,
                resultParameters,
                returnType
            )
        }

        private fun InvokeOriginalCodeBase.getForResultOrDefine(
            defineResultType: String?,
            uri: String
        ): TypeName {
            return if (defineResultType?.isNotBlank() == true)
                Class.forName(defineResultType).asTypeName().toKotlinTypeName()
            else
                result?.toContextTypeName(uri) ?: Unit::class.asClassName()
        }

        private fun InvokeOriginalCodeBase.getResultGroupsLambdaOrDefine(
            defineResultType: String?,
            uri: String,
            hasSyncReturn: Boolean
        ): List<LambdaTypeCodeBase> =
            mutableListOf<LambdaTypeCodeBase>().apply {
                val resultCallbackFunctions = queries.flatMap { query ->
                    query.resultCallbacks?.functions?.map { function->
                        query to function
                    } ?: listOf()
                }
                resultCallbackFunctions.forEach { (query, function) ->  // have @RResult callback
                    if (size == 0 && defineResultType?.isNotBlank() == true) {
                        // if have defineResultType and have @RResult, merge there in first callback
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.toResultTransformerName(size, function.legalName),
                                listOf(Class.forName(defineResultType).asTypeName().toKotlinTypeName()),
                                Unit::class.asClassName(),
                                query.nullable
                            )
                        )
                    } else {
                        val parameters = function.queries.map { codeBase -> codeBase.toContextTypeName(uri) }
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.toResultTransformerName(size, function.legalName),
                                parameters,
                                Unit::class.asClassName(),
                                query.nullable
                            )
                        )
                    }
                }
                if (!hasSyncReturn) {
                    if (resultCallbackFunctions.isEmpty() && defineResultType?.isNotBlank() == true) {
                        // if have defineResultType but no @RResult, give a defineResultType callback
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.toResultTransformerName(size, result?.name),
                                listOf(Class.forName(defineResultType).asTypeName().toKotlinTypeName()),
                                Unit::class.asClassName(),
                                result?.originalType?.nullable ?: false
                            )
                        )
                    }
                    if (isEmpty() && null != result) {
                        // still no callback but is asnyc return, give callback of function return type
                        add(
                            LambdaTypeCodeBase(
                                Constants.Apis.toResultTransformerName(size, result.name),
                                listOf(result.toContextTypeName(uri)),
                                Unit::class.asClassName(),
                                result.originalType.nullable
                            )
                        )
                    }
                }
            }
    }

    fun invokeParameterNames(): List<String> =
        listOfNotNull(objectInstance?.let { query->"this@${(query.originalType.toTypeName() as? ClassName)?.simpleName}" }) +
                listOfNotNull(receiver?.let { "this" }) +
                parameters.map { query -> query.callName } +
                resultParameters.map { lambda -> lambda.name }
}
