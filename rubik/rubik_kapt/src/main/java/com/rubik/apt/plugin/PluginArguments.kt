package com.rubik.apt.plugin

import com.ktnail.x.Logger
import com.rubik.apt.declare.LeastContext
import javax.annotation.processing.ProcessingEnvironment

data class PluginArguments(
    val defaultScheme: String?,
    val aggregateMethodSize: Int,
    val aggregateGenerated: String?,
    val routerContextEnable: Boolean,
    val ignoreValueAnnos: List<String>,
    val aggregateKDocUserAndTime: Boolean,
    val contexts: List<ContextArguments>?
) {

    constructor(processingEnv: ProcessingEnvironment) : this(
        processingEnv.arguments(Arguments.Declare.DEFAULT_SCHEME),
        processingEnv.intArguments(Arguments.Declare.AGGREGATE_METHOD_SIZE, 100),
        processingEnv.arguments(Arguments.Declare.AGGREGATE_GENERATED),
        processingEnv.booleanArguments(Arguments.Declare.CONTEXT_ROUTER_ENABLE),
        processingEnv.arguments(Arguments.Declare.CONTEXT_IGNORE_VALUE_ANNOS)?.split("|")?: emptyList(),
        processingEnv.booleanArguments(Arguments.Declare.AGGREGATE_USER_AND_TIME_ENABLE),
        processingEnv.argumentContexts()
    )

    val contextLibsEnable = contexts?.any { context -> context.contextLibsEnable } == true
    val aggregateEnable = contexts?.any { context -> context.aggregateEnable } == true

    override fun toString(): String = "defaultScheme:$defaultScheme  " +
            "aggregateMethodSize:$aggregateMethodSize " +
            "aggregateGenerated:$aggregateGenerated " +
            "routerContextEnable:$routerContextEnable " +
            "aggregateKDocUserAndTime:$aggregateKDocUserAndTime " +
            "contexts:$contexts "


    data class ContextArguments(
        val context: LeastContext,
        val contextLibsEnable: Boolean,
        val aggregateEnable: Boolean
    )
}

private fun ProcessingEnvironment.argumentContexts() :List<PluginArguments.ContextArguments>? =
    options?.filter { (key, value) ->
        Logger.e(" APT DBG options key:${key}  value:${value}")
        key.startsWith(Arguments.Declare.JSON_CONTEXT)
    }?.map { (key, json) ->
        val contextLibsEnable = booleanArguments(key.replace(Arguments.Declare.JSON_CONTEXT, Arguments.Declare.CONTEXT_LIBS_ENABLE))
        val aggregateEnable = booleanArguments(key.replace(Arguments.Declare.JSON_CONTEXT, Arguments.Declare.AGGREGATE_ENABLE))
        PluginArguments.ContextArguments(
            LeastContext.createByJson(json),
            contextLibsEnable,
            aggregateEnable
        )
    }


