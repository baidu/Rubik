package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.invoker.TypeCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.addWhenBlockStatements
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

internal fun TypeSpec.Builder.addOnRouteFunction(
    context: ContextCodeBase,
    nameBox: FileNameBox,
    maxPageSize: Int
) = apply {
    val routes :List<RouteCodeBase> = context.apis.map { (_, value) -> value } + context.activities.map { (_, value) -> value }
    var page = 0
    var start = 0
    do {
        val end = (start + maxPageSize).let { e -> if (e > routes.size) routes.size else e }
        addFunction(
            onRouteFunction(routes.subList(start, end), page, end >= routes.size, nameBox)
        )
        page++
        start = end
    } while (start < routes.size)
}

private fun onRouteFunction(
    routes: List<RouteCodeBase>,
    index: Int,
    end: Boolean,
    nameBox: FileNameBox
) = FunSpec.builder(
   index.indexToFunctionName()
).apply {
    if (index == 0) addModifiers(KModifier.OVERRIDE)
    else addModifiers(KModifier.PRIVATE)
}.addParameter(
    Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME, String::class
).addParameter(
    Constants.Aggregate.ROUTE_PARAMETER_QUERIES_NAME,
    ClassName.bestGuess("${Constants.Aggregate.ROUTE_PACKAGE_NAME}.${Constants.Aggregate.QUERIES_CLASS_NAME}")
).addParameter(
    Constants.Aggregate.ROUTE_PARAMETER_RESULTS_NAME,
    ClassName.bestGuess("${Constants.Aggregate.ROUTE_PACKAGE_NAME}.${Constants.Aggregate.RESULTS_CLASS_NAME}")
).addWhenBlockStatements(
    null,
    if (end) "throw ${Constants.Aggregate.makeRouteExceptionCode()}"
    else "${(index + 1).indexToFunctionName()}(${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME}, ${Constants.Aggregate.ROUTE_PARAMETER_QUERIES_NAME}, ${Constants.Aggregate.ROUTE_PARAMETER_RESULTS_NAME})"
) {
    routes.forEach { route ->
        if (route is ApiCodeBase)
            addApiOnRouteStatements(route, nameBox)
        else if (route is ActivityCodeBase)
            addActivityOnRouteStatements(route, nameBox)
    }
}.build()

private fun Int.indexToFunctionName() =
    if (this == 0) Constants.Aggregate.METHOD_ON_ROUTE_NAME
    else "${Constants.Aggregate.METHOD_ON_ROUTE_NAME}Ext${this}"

private fun FunSpec.Builder.addApiOnRouteStatements(
    api: ApiCodeBase,
    nameBox: FileNameBox
) = apply {
    val contextInvoker = api.contextInvoker(nameBox.uri)
    val versionPath = api.versionPath
    if (Constants.Aggregate.isParameterPath(versionPath)) {
        beginControlFlow("${Constants.Aggregate.PATH_CLASS_NAME_AS}(\"$versionPath\").${Constants.Aggregate.METHOD_MATCHING_NAME}(${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME}) -> ".noSpaces())
        addStatement(Constants.Aggregate.makeAddPathQueriesCode(versionPath))
    } else
        beginControlFlow("\"$versionPath\" == ${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME} -> ".noSpaces())

    val parameterNames = mutableListOf<String>()

    var queryCount = 0
    val queryIndex = { queryCount++ }

    contextInvoker.receiver?.let { query ->
        addQueryVariable(api.invoker, query, queryIndex())
        parameterNames.add(TypeCodeBase.castToTypeCode(query.callName))
    }

    contextInvoker.parameters.forEach { query ->
        addQueryVariable(api.invoker, query, queryIndex())
        parameterNames.add(TypeCodeBase.castToTypeCode(query.callName))
    }

    var resultCount = 0
    val resultIndex = { resultCount++ }

    contextInvoker.resultParameters.forEach { result ->
        addStatement("val ${result.name}: ${result.toTypeCode(nameBox)} = { ${result.queriesCode} ->".noSpaces())
        addStatement(
            "  ${
                Constants.Aggregate.makeSetResultsCode(
                    result.queriesRequestsCode,
                    resultIndex()
                )
            }".noSpaces()
        )
        addStatement("}".noSpaces())
        parameterNames.add(result.name)
    }

    val stringParameterCode = parameterNames.joinToString(", ")

    val invokeCode = "${api.actionFunctionName}(${stringParameterCode})"

    if (contextInvoker.hasSyncReturn) {
        beginControlFlow("${invokeCode}.apply".noSpaces())
        addStatement(
            Constants.Aggregate.makeSetResultsCode(
                Constants.Aggregate.makeResultsCode("this"),
                resultIndex()
            )
        )
        endControlFlow()
    } else {
        addStatement(invokeCode.noSpaces())
    }
    endControlFlow()
}

private fun FunSpec.Builder.addActivityOnRouteStatements(
    activity: ActivityCodeBase,
    nameBox: FileNameBox
) = apply {
    val versionPath = activity.versionPath
    if (Constants.Aggregate.isParameterPath(versionPath))
        beginControlFlow("${Constants.Aggregate.PATH_CLASS_NAME_AS}(\"$versionPath\").${Constants.Aggregate.METHOD_MATCHING_NAME}(${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME}) -> ")
    else
        beginControlFlow("\"$versionPath\" == ${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME} -> ")
    Constants.KDoc.function(
        activity.className,
        activity.propertiesKDoc
    ).forEach { doc ->
        addComment(doc)
    }
    addStatement(activity.startCode(versionPath, nameBox))
    endControlFlow()
}