package com.rubik.apt.files.source.context.action.inceface

import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.codebase.invoker.LambdaTypeCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

/**
 * add RouteAction parameters and result for function
 */
fun FunSpec.Builder.addApiRouteActionParametersAndResult(
    contextInvoker: InvokeContextCodeBase,
    nameBox: FileNameBox,
    processExt: Boolean = false
) = apply {
    contextInvoker.receiver?.let { query ->
        if (processExt) {
            receiver(query.toContextTypeName(nameBox.uri))
        } else {
            addApiRouterParameter(nameBox.uri, query)
        }
    }
    contextInvoker.parameters.forEach { query ->
        addApiRouterParameter(nameBox.uri, query)
    }

    if (contextInvoker.hasSyncReturn) {
        addApiRouterReturnType(contextInvoker.returnType)
    }
    val results = contextInvoker.resultParameters
    addApiRouterResultParameters(results)
}


fun FunSpec.Builder.addApiRouterParameter(
    contextUri: String,
    query: QueryCodeBase
) {
    addParameter(
        ParameterSpec.builder(
            query.legalName, query.toContextTypeName(contextUri)
        ).apply {
            if (query.isVarargs) { addModifiers(KModifier.VARARG) }
        }.build()
    )
}


fun FunSpec.Builder.addApiRouterReturnType(
    resultType: TypeName
) = apply {
    returns(resultType.copy(nullable = true))
}

fun FunSpec.Builder.addApiRouterResultParameters(
    results: List<LambdaTypeCodeBase>
) = apply {
    results.forEach { resultLambdaType ->
        addParameter(
            resultLambdaType.name,
            resultLambdaType.toTypeName()
        )
    }
}

