package com.rubik.apt.files.source.aggregate

import com.ktnail.x.uri.buildVersionPath
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.invoker.InvokeElementCodeBase
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.FunSpec

internal fun FunSpec.Builder.addApiOnRouteParametersStatements(
    originalInvoker: InvokeElementCodeBase,
    query: QueryCodeBase,
    index: Int
) = apply {
    addStatement(
        "val ${query.legalName} = ${
            query.makeGetQueryContextTypeCode(
                if (originalInvoker.type == InvokeElementType.HIGHER_ORDER_FUNC) "\"${query.originalName}\"" else "null",
                index
            )
        }".noSpaces()
    )
}

internal fun FunSpec.Builder.addApiOnRouteStatements(
    uri: String,
    apis: Map<String, ApiCodeBase>
) = apply {
    apis.forEach { (_, api) ->
        val contextInvoker = api.contextInvoker(uri)
        val path = buildVersionPath(api.path, api.version)
        if (Constants.Aggregate.isParameterPath(path)) {
            beginControlFlow("${Constants.Aggregate.PATH_CLASS_NAME}(\"$path\").${Constants.Aggregate.METHOD_MATCHING_NAME}(${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME}) -> ")
            addStatement(Constants.Aggregate.makeAddPathQueriesCode(path))
        } else
            beginControlFlow("\"$path\" == ${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME} -> ".noSpaces())

        val parameterNames = mutableListOf<String>()

        var queryCount = 0
        val queryIndex = { queryCount++ }

        contextInvoker.receiver?.let { query ->
            addApiOnRouteParametersStatements(api.originalInvoker, query, queryIndex())
            parameterNames.add(Constants.Aggregate.makeCaseToTypeCode(query.callName))
        }

        contextInvoker.parameters.forEach { query ->
            addApiOnRouteParametersStatements(api.originalInvoker, query, queryIndex())
            parameterNames.add(Constants.Aggregate.makeCaseToTypeCode(query.callName))
        }

        var resultCount = 0
        val resultIndex = { resultCount++ }

        contextInvoker.resultParameters.forEach { result ->
            addStatement("val ${result.name} : ${result.toTypeName()} = { ${result.queriesCode} ->".noSpaces())
            addStatement("  ${Constants.Aggregate.makeSetResultsCode(result.queriesRequestsCode, resultIndex())}".noSpaces())
            addStatement("}".noSpaces())
            parameterNames.add(result.name)
        }

        val stringParameterCode = parameterNames.joinToString(",\n") { parameterName ->
            "  $parameterName".noSpaces()
        }.let { code ->
            if (code.isNotBlank()) {
                "\n${code}\n"
            } else ""
        }

        val invokeCode =  "${api.actionFunctionName}(${stringParameterCode})"

        if (contextInvoker.hasSyncReturn) {
            beginControlFlow("${invokeCode}.apply".noSpaces())
            addStatement(Constants.Aggregate.makeSetResultsCode(Constants.Aggregate.makeResultsCode("this"), resultIndex()))
            endControlFlow()
        }else{
            addStatement(invokeCode.noSpaces())
        }

        endControlFlow()
    }
}
