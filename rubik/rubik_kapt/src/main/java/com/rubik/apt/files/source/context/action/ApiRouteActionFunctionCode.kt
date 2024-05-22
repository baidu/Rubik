package com.rubik.apt.files.source.context.action

import com.ktnail.x.uri.buildVersionUri
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.invoker.InvokeContextCodeBase
import com.rubik.apt.files.source.context.action.inceface.addApiRouteActionParametersAndResult
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.addRGeneratedRouterAnnotation
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName


/**
 *  add call router RouteAction function
 */
fun TypeSpec.Builder.addApiRouteActionFunctions(
    uri: String,
    api: ApiCodeBase,
    nameBox: FileNameBox
) = apply {
    val functionName = api.contextFunctionName
    val apiUri = buildVersionUri(uri, api.path, api.version)
    val contextInvoker = api.contextInvoker(uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addApiRouteActionParametersAndResult(
            contextInvoker,
            nameBox,
            true
        ).addApiRouteActionCallRouterBody(
            contextInvoker,
            api.actionFunctionName,
            nameBox
        ).addAnnotation(
            JvmStatic::class.java
        ).addRGeneratedRouterAnnotation(
            apiUri,
            api.invoker.type.toString(),
            api.invoker.clazz.name,
            api.invoker.name
        ).addKdoc(
            Constants.KDoc.functionRouter(
                apiUri,
                api.invoker.location,
                api.invoker.queriesKDoc,
                api.invoker.resultKDoc
            )
        ).build()
    )
}

fun FunSpec.Builder.addApiRouteActionCallRouterBody(
    contextInvoker: InvokeContextCodeBase,
    functionName: String,
    nameBox: FileNameBox
) = apply {
    val safeRouteCode = "${Constants.ContextRouters.ROUTE_FUNCTION_NAME}${
        if (contextInvoker.hasSyncReturn) ""
        else "<${nameBox.closeSimpleName(Unit::class.asTypeName())}>"
    }"
    val invokeCode = "${Constants.RouteActions.PROPERTY_ROUTE_ACTION}?.$functionName(${contextInvoker.invokeParameterNames().joinToString(", ")})"
    beginControlFlow(if (contextInvoker.hasSyncReturn) "return $safeRouteCode" else safeRouteCode)
    addStatement(invokeCode.noSpaces())
    endControlFlow()
}
