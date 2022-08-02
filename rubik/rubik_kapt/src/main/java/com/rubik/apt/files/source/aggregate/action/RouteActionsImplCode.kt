package com.rubik.apt.files.source.aggregate

import com.rubik.apt.CallResultType
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.api.ResultInvokerCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.codebase.invoker.InvokeElementCodeBase
import com.rubik.apt.files.source.context.addApiRouterParametersAndResult
import com.rubik.apt.utility.inControlFlowStatementIf
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

internal fun FunSpec.Builder.addApiResultTransformCode(
    query: QueryCodeBase,
    resultInvoker: ResultInvokerCodeBase,
    action: FunSpec.Builder.(CallResultType) -> Unit
) {
    if (resultInvoker.needTransform) {
        if (resultInvoker.callResultType == CallResultType.HIGHER_ORDER_FUNC) {
            beginControlFlow(("val ${Constants.Apis.toResultTransformName(query.legalName)} : (${resultInvoker.queriesHighLevelTypeCode}) -> Unit = { ${resultInvoker.queriesCode} -> ").noSpaces())
            action(resultInvoker.callResultType)
            endControlFlow()
        } else {
            beginControlFlow(
                "val ${Constants.Apis.toResultTransformName(query.legalName)} = object : ${resultInvoker.callClassCode}".noSpaces()
            )
            beginControlFlow("override fun ${resultInvoker.name}(${resultInvoker.getQueriesTypeAndNameCode()})".noSpaces())
            action(resultInvoker.callResultType)
            endControlFlow()
            endControlFlow()
        }
    }
}

private fun FunSpec.Builder.addApiRouteActionImplBody(
    originalInvoker :InvokeElementCodeBase,
    contextInvoker :InvokeContextCodeBase
) = apply {
    Constants.KDoc.function(
        originalInvoker.location,
        originalInvoker.queriesKDoc,
        originalInvoker.resultKDoc
    ).forEach {doc->
        addComment(doc)
    }
    originalInvoker.queries.forEach { query ->
        query.resultInvoker?.let { resultInvoker ->
            addApiResultTransformCode(query, resultInvoker) { callResultType ->
                inControlFlowStatementIf(query.nullabe, query.legalName) {
                    addStatement(
                        "${query.legalName}(${
                            resultInvoker.queryNamesCode(callResultType == CallResultType.HIGHER_ORDER_FUNC)
                        })".noSpaces()
                    )
                }
            }
        }
    }
    val invokeCode = originalInvoker.invokeCode(castAllQuery = false, processExt = false)

    if (null != originalInvoker.result) {
        when {
            contextInvoker.hasSyncReturn -> {
                addStatement("return $invokeCode".noSpaces())
            }
            contextInvoker.resultParameters.isNotEmpty() -> {
                val resultFunction =  contextInvoker.resultParameters.first()
                inControlFlowStatementIf(resultFunction.nullable, resultFunction.name) {
                    addStatement(
                        "${resultFunction.name}(${
                            if (invokeCode.isNotBlank()) {
                                "\n  $invokeCode\n"
                            } else {
                                ""
                            }
                        })".noSpaces())
                }
            }
            else -> {
                addStatement(invokeCode)
            }
        }
    }else{
        addStatement(invokeCode)
    }
}

private fun TypeSpec.Builder.addApiRouteActionsImplFunctions(
    uri: String,
    api: ApiCodeBase
) = apply {
    val functionName = api.actionFunctionName
    val contextInvoker = api.contextInvoker(uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addModifiers(
            KModifier.OVERRIDE
        ).addApiRouterParametersAndResult(
            uri,
            contextInvoker,
            false
        ).addApiRouteActionImplBody(
            api.originalInvoker,
            contextInvoker
        ).build()
    )
}

internal fun TypeSpec.Builder.addRouteActionsImplFunctions(uri: String, routers: SectionCodeBase) =
    apply {
        routers.forEachRoute { route ->
            if (route is ApiCodeBase) {
                addApiRouteActionsImplFunctions(uri, route)
            }
        }
    }
