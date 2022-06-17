package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.event.EventCodeBase
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.addEventStatements(events: Map<String, List<EventCodeBase>>) = apply {
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
            addInvokeCode(event.invoker)
        }
        endControlFlow()
    }
}