package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase
import com.rubik.apt.files.source.context.action.inceface.addApiRouteActionParametersAndResult
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.inControlFlowStatementIf
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec


internal fun TypeSpec.Builder.addRouteActionsImplFunctions(
    routers: SectionCodeBase<RouteCodeBase>,
    nameBox: FileNameBox
) =
    apply {
        routers.forEachRoute { route ->
            if (route is ApiCodeBase) {
                addApiRouteActionsImplFunctions(route, nameBox)
            }
        }
    }

private fun TypeSpec.Builder.addApiRouteActionsImplFunctions(
    api: ApiCodeBase,
    nameBox: FileNameBox
) = apply {
    val functionName = api.actionFunctionName
    val contextInvoker = api.contextInvoker(nameBox.uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addModifiers(
            KModifier.OVERRIDE
        ).addApiRouteActionParametersAndResult(
            contextInvoker,
            nameBox,
            false
        ).addApiRouteActionImplBody(
            api.invoker,
            contextInvoker,
            nameBox
        ).build()
    )
}


private fun FunSpec.Builder.addApiRouteActionImplBody(
    originalInvoker: InvokeOriginalCodeBase,
    contextInvoker: InvokeContextCodeBase,
    nameBox: FileNameBox
) = apply {
    Constants.KDoc.function(
        originalInvoker.location,
        originalInvoker.queriesKDoc,
        originalInvoker.resultKDoc
    ).forEach { doc ->
        addComment(doc)
    }
    originalInvoker.queries.forEach { query ->
        query.callback?.let { callback ->
            if (callback.transformRMirror)
                addCallbackTransformCode(query, callback, nameBox)
        }
    }
    val invokeCode =
        invokeOriginalCode(originalInvoker, nameBox, castAllQuery = false, processExt = false)

    if (null != originalInvoker.result) {
        when {
            contextInvoker.hasSyncReturn -> {
                addCode("return $invokeCode".noSpaces())
            }
            contextInvoker.resultParameters.isNotEmpty() -> {
                val resultFunction = contextInvoker.resultParameters.first()
                inControlFlowStatementIf(resultFunction.nullable, resultFunction.name) {
                    addCode("${resultFunction.name}(${invokeCode})")
                }
            }
            else -> {
                addCode(invokeCode)
            }
        }
    } else {
        addCode(invokeCode)
    }
}

