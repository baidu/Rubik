package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.utility.noSpaces
import com.ktnail.x.uri.buildVersionPath
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.addApiStatements(uri: String, apis: Map<String, ApiCodeBase>) = apply {
    apis.forEach { (_, api) ->
        val path = buildVersionPath(api.path, api.version)
        if (Constants.Aggregate.isParameterPath(path)) {
            beginControlFlow("${Constants.Aggregate.PATH_CLASS_NAME}(\"$path\").${Constants.Aggregate.METHOD_MATCHING_NAME}(${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME}) -> ")
            addStatement(Constants.Aggregate.makeAddPathQueriesCode(path))
        } else
            beginControlFlow("\"$path\" == ${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME} -> ".noSpaces())
        Constants.KDoc.function(
            api.invoker.location,
            api.invoker.queriesKDoc,
            api.invoker.resultKDoc
        ).forEach {doc->
            addComment(doc)
        }
        api.invoker.assistant?.let { invoker ->
            addInvokeParametersCode(invoker)
        }
        addInvokeParametersCode(api.invoker)
        addInvokeWithResultCode(api.invoker)
        endControlFlow()
    }
}