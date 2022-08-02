package com.rubik.apt.files.source.context

import com.ktnail.x.uri.buildVersionUri
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.codebase.invoker.LambdaTypeCodeBase
import com.rubik.apt.utility.addRGeneratedRouterAnnotation
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.*

fun TypeSpec.Builder.addApiRouteActionFunctions(
    uri: String,
    api: ApiCodeBase
) = apply {
    val functionName = api.contextFunctionName
    val apiUri = buildVersionUri(uri, api.path, api.version)
    val contextInvoker = api.contextInvoker(uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addApiRouterParametersAndResult(
            uri,
            contextInvoker,
            true
        ).addApiRouteActionBody(
            contextInvoker,
            api.actionFunctionName
        ).addAnnotation(
            JvmStatic::class.java
        ).addRGeneratedRouterAnnotation(
            apiUri,
            api.originalInvoker.type.toString(),
            api.originalInvoker.className,
            api.originalInvoker.name
        ).addKdoc(
            Constants.KDoc.functionRouter(
                apiUri,
                api.originalInvoker.location,
                api.originalInvoker.queriesKDoc,
                api.originalInvoker.resultKDoc
            )
        ).build()
    )
}

fun FunSpec.Builder.addApiRouterParameter(contextUri: String, query: QueryCodeBase) {
    addParameter(
        ParameterSpec.builder(
            query.legalName, query.toContextTypeName(contextUri)
        ).apply {
            if (query.isVarargs) { addModifiers(KModifier.VARARG) }
        }.build()
    )
}


fun FunSpec.Builder.addApiRouterParametersAndResult(
    contextUri: String,
    contextInvoker: InvokeContextCodeBase,
    processExt: Boolean = false
) = apply {
    contextInvoker.receiver?.let { query ->
        if (processExt) {
            receiver(query.toContextTypeName(contextUri))
        } else {
            addApiRouterParameter(contextUri, query)
        }
    }
    contextInvoker.parameters.forEach { query ->
        addApiRouterParameter(contextUri, query)
    }

    if (contextInvoker.hasSyncReturn) {
        addApiRouterReturnType(contextInvoker.returnType)
    }
    val results = contextInvoker.resultParameters
    addApiRouterResultParameters(results)
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


fun FunSpec.Builder.addApiRouteActionBody(
    contextInvoker: InvokeContextCodeBase,
    functionName: String
) = apply {
    var code = if (contextInvoker.hasSyncReturn) "return " else ""
    code += "${Constants.RouteActions.PROPERTY_ROUTE_ACTION}.$functionName"
    val parameters
    = mutableListOf<String>().apply { contextInvoker.receiver?.let { add("this") } } +
            contextInvoker.parameters.map { query -> query.callName } +
            contextInvoker.resultParameters.map { lambda -> lambda.name }
    code += "(${parameters.joinToString(", ")})"
    addStatement(code.noSpaces())
}

