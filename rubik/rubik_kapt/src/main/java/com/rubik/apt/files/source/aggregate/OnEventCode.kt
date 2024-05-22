package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.event.EventCodeBase
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.addWhenBlockStatements
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

fun onEventFunction(
    context: ContextCodeBase,
    nameBox: FileNameBox
) = FunSpec.builder(
    Constants.Aggregate.METHOD_ON_EVENT_NAME
).addModifiers(
    KModifier.OVERRIDE
).addParameter(
    Constants.Aggregate.EVENT_PARAMETER_MSG_NAME, String::class
).addParameter(
    Constants.Aggregate.ROUTE_PARAMETER_QUERIES_NAME,
    ClassName.bestGuess("${Constants.Aggregate.ROUTE_PACKAGE_NAME}.${Constants.Aggregate.QUERIES_CLASS_NAME}")
).addWhenBlockStatements(Constants.Aggregate.EVENT_PARAMETER_MSG_NAME, null) {
    addEventStatements(context.events, nameBox)
}.build()

private fun FunSpec.Builder.addEventStatements(
    events: Map<String, List<EventCodeBase>>,
    nameBox: FileNameBox
) = apply {
    events.forEach { (msg, msgEvents) ->
        beginControlFlow("\"$msg\" -> ".noSpaces())
        msgEvents.forEach {event->
            Constants.KDoc.function(
                event.invoker.location,
                event.invoker.queriesKDoc,
                event.invoker.resultKDoc
            ).forEach { doc ->
                addComment(doc)
            }
            addInvokeParametersCode(event.invoker, nameBox)
            addCode(invokeOriginalCode(event.invoker,nameBox, castAllQuery = true, processExt = false))
        }
        endControlFlow()
    }
}

private fun FunSpec.Builder.addInvokeParametersCode(
    invoker: InvokeOriginalCodeBase,
    nameBox: FileNameBox
){

    var queryCount = 0
    val queryIndex = { queryCount++ }

    invoker.parameters.forEach { parameter ->
        if (null != parameter.callback && parameter.callback.transformRMirror) {
            addQueryVariableCaseToContextType(invoker, parameter, queryIndex(), nameBox)
            addCallbackTransformCode(parameter, parameter.callback, nameBox)
        } else {
            addQueryVariable(invoker, parameter, queryIndex())
        }
    }

}