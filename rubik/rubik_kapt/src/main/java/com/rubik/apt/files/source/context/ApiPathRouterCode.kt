package com.rubik.apt.files.source.context

import com.ktnail.x.Logger
import com.ktnail.x.uri.buildVersionUri
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.utility.inControlFlowStatementIf
import com.squareup.kotlinpoet.*

fun TypeSpec.Builder.addApiRouterFunctions(
    uri: String,
    api: ApiCodeBase
) = apply {
    val functionName = api.functionName
    val apiUri = buildVersionUri(uri, api.path, api.version)
    addFunction(
        FunSpec.builder(
            functionName
        ).addApiRouterStatement(
            uri,
            apiUri,
            api,
            functionName
        ).addAnnotation(
            JvmStatic::class.java
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

fun FunSpec.Builder.addApiRouterParameter(contextUri: String, query: QueryCodeBase) {
    if (!query.isExtendThis)
        addParameter(
            ParameterSpec.builder(
                query.legalName, query.toTypeName(contextUri)
            ).apply {
                if (query.isVarargs) { addModifiers(KModifier.VARARG) }
            }.build()
        )
    else
        receiver(query.toTypeName(contextUri))

}

fun FunSpec.Builder.addApiRouterAddToQueryCode(query: QueryCodeBase, functionName: String) {
    if (!query.isExtendThis) {
        addStatement(Constants.Apis.makeAddToQueryCode(query.originalName, query.legalName))
    } else {
        addStatement(Constants.Apis.makeAddToQueryCode(query.originalName, "this@$functionName"))
    }
}


fun FunSpec.Builder.addApiRouterStatement(
    contextUri: String,
    apiUri: String,
    api: ApiCodeBase,
    functionName: String
) = apply {
    val queries = api.invoker.queries.filter { query -> null == query.resultInvoker }
    api.invoker.assistant?.queries?.forEach { query ->
        addApiRouterParameter(contextUri, query)
    }
    queries.forEach { query ->
        addApiRouterParameter(contextUri, query)
    }

    if (api.forResult) {
        addApiRouterReturnType(api.getForResult(contextUri))
        beginControlFlow("return ${Constants.Apis.NAVIGATE_FOR_RESULT_FUNCTION_NAME}") // navigateForResult
        addApiRouterDSLQueriesStatement(apiUri, api, functionName, queries)
        endControlFlow() // navigate or navigateForResult end
    } else {
        val results = api.getResultGroupsLambdaOrDefine(contextUri)
        addApiRouterResultParameters(results)
        beginControlFlow(Constants.Apis.NAVIGATE_FUNCTION_NAME) // navigate
        addApiRouterDSLQueriesStatement(apiUri, api, functionName, queries)
        results.forEachIndexed { index, resultLambdaType ->
            val name = Constants.Apis.makeFunctionResultName(index, resultLambdaType.first)
            inControlFlowStatementIf(resultLambdaType.second.isNullable,"if(null!=$name)"){
                addStatement("${Constants.Apis.RESULT_DSL_NAME}($name)") // result()
            }
        }
        endControlFlow() // navigate or navigateForResult end
    }
}

fun FunSpec.Builder.addApiRouterDSLQueriesStatement(
    apiUri: String,
    api: ApiCodeBase,
    functionName: String,
    queries: List<QueryCodeBase>
) = apply {
    addStatement("${Constants.Apis.URI_DSL_NAME}=\"$apiUri\"") // uri
    addStatement("${Constants.Apis.CHECK_ROUTER_VERSION_DSL_NAME}(${Constants.Router.CHECK_ROUTER_VERSION})") // checkRouterVersion
    if (api.invoker.assistant?.queries?.isNotEmpty() == true || queries.isNotEmpty()){
        beginControlFlow(Constants.Apis.QUERY_DSL_NAME) // query
        api.invoker.assistant?.queries?.forEach {
            addApiRouterAddToQueryCode(it, functionName)
        }
        queries.forEach {
            addApiRouterAddToQueryCode(it, functionName)
        }
        endControlFlow() // query end
    }
}

fun FunSpec.Builder.addApiRouterReturnType(
    resultType: TypeName
) = apply {
    returns(resultType.copy(nullable = true))
}

fun FunSpec.Builder.addApiRouterResultParameters(
    results: List<Pair<String?, TypeName>>
) = apply {
    results.forEachIndexed { index, resultLambdaType ->
        addParameter(
            Constants.Apis.makeFunctionResultName(index, resultLambdaType.first),
            resultLambdaType.second
        )
    }
}

