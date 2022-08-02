package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.event.EventCodeBase
import com.rubik.apt.codebase.invoker.InvokeElementCodeBase
import com.squareup.kotlinpoet.FunSpec


private fun FunSpec.Builder.addInvokeParametersCode(
    codeBase: InvokeElementCodeBase
){
    var queryCount = codeBase.assistant?.queries?.size ?: 0
    val queryIndex = { queryCount++ }

    var resultCount = 0
    val resultIndex = { resultCount++ }

    codeBase.queries.forEach { queryCodeBase ->
        if (null != queryCodeBase.resultInvoker) {
            addApiResultTransformCode(queryCodeBase, queryCodeBase.resultInvoker) { _ ->
                addStatement(Constants.Aggregate.makeSetResultsCode(queryCodeBase.resultInvoker.queriesRequestsCode, resultIndex()))
            }
        } else {
            addApiOnRouteParametersStatements(codeBase, queryCodeBase, queryIndex())
        }
    }

}

internal fun FunSpec.Builder.addEventStatements(events: Map<String, List<EventCodeBase>>) = apply {
    events.forEach { (msg, msgEvents) ->
        beginControlFlow("\"$msg\" -> ")
        msgEvents.forEach {event->
            Constants.KDoc.function(
                event.invoker.location,
                event.invoker.queriesKDoc,
                event.invoker.resultKDoc
            ).forEach { doc ->
               addComment(doc)
            }
            event.invoker.assistant?.let { invoker ->
                addInvokeParametersCode(invoker)
            }
            addInvokeParametersCode(event.invoker)
            addStatement(event.invoker.invokeCode(castAllQuery = true, processExt = false))
        }
        endControlFlow()
    }
}