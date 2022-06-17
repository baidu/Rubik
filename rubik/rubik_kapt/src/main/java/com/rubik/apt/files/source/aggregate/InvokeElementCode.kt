package com.rubik.apt.files.source.aggregate

import com.rubik.apt.CallResultType
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.InvokeElementCodeBase
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.addInvokeParametersCode(codeBase: InvokeElementCodeBase) {
    var resultCount = 0
    val resultIndex = { resultCount++ }
    var queryCount = codeBase.assistant?.queries?.size ?: 0
    val queryIndex = { queryCount++ }
    codeBase.queries.forEach { queryCodeBase ->
        if (null != queryCodeBase.resultInvoker) {
            addMakeApiAsyncResultCode(resultIndex(), queryCodeBase)
        } else {
            addStatement(
                "val ${queryCodeBase.legalName} = ${
                    queryCodeBase.makeGetQueryCode(
                        if(codeBase.type == InvokeElementType.HIGHER_ORDER_FUNC) "\"${queryCodeBase.originalName}\"" else "null",
                        queryCodeBase.originalType,
                        queryIndex()
                    )
                }".noSpaces()
            )
        }
    }
}

private fun FunSpec.Builder.addMakeApiAsyncResultCode(resultIndex: Int, queryCodeBase: QueryCodeBase) {
    queryCodeBase.resultInvoker?.let { resultInvokerCodeBase ->
        if (resultInvokerCodeBase.callResultType == CallResultType.HIGHER_ORDER_FUNC) {
            beginControlFlow("val ${queryCodeBase.legalName} : (${resultInvokerCodeBase.queriesHighLevelTypeCode}) -> Unit = { ${resultInvokerCodeBase.queriesCode} -> ".noSpaces())
            addStatement(Constants.Aggregate.makeSetResultsCode(resultInvokerCodeBase.queriesRequestsCode, resultIndex))
            endControlFlow()
        } else {
            beginControlFlow("val ${queryCodeBase.legalName} = object : ${resultInvokerCodeBase.callClassCode}".noSpaces())
            beginControlFlow("override fun ${resultInvokerCodeBase.name}(${resultInvokerCodeBase.getQueriesTypeAndNameCode()})".noSpaces())
            addStatement(Constants.Aggregate.makeSetResultsCode(resultInvokerCodeBase.queriesRequestsCode, resultIndex))
            endControlFlow()
            endControlFlow()
        }
    }
}

fun FunSpec.Builder.addInvokeWithResultCode(codeBase: InvokeElementCodeBase) {
    if (codeBase.hasResultInvoker) {
        addStatement(codeBase.invokeCode)
    } else {
        beginControlFlow("${codeBase.invokeCode}.apply".noSpaces())
        addStatement(Constants.Aggregate.makeSetResultsCode(Constants.Aggregate.makeResultsCode("this", codeBase.result?.isRValue ?: false),0))
        endControlFlow()
    }
}

fun FunSpec.Builder.addInvokeCode(codeBase: InvokeElementCodeBase) {
    addStatement(codeBase.invokeCode)
}
