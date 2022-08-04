package com.rubik.apt.plugin

import com.ktnail.x.Logger
import com.rubik.apt.declare.ContextDeclare
import javax.annotation.processing.ProcessingEnvironment

data class PluginArguments(
    val defaultScheme: String?,
    val aggregateEnable: Boolean,
    val aggregateGenerated: String?,
    val routerContextEnable: Boolean
) {
    private val contexts = mutableListOf<Pair<ContextDeclare, Boolean>>()

    fun argumentContexts(action: (String, String, List<String>?, String, Boolean) -> Unit) {
        contexts.forEach { context ->
            action(
                context.first.uri,
                context.first.name,
                context.first.dependencies?.map { dependency -> dependency.uri },
                context.first.version,
                context.second
            )
        }
    }

    override fun toString(): String {
        return "defaultScheme:$defaultScheme aggregateEnable:$aggregateEnable aggregateGenerated:$aggregateGenerated routerContextEnable:$routerContextEnable contexts:$contexts "
    }

    companion object {
        operator fun invoke(processingEnv: ProcessingEnvironment) = PluginArguments(
            processingEnv.arguments(Arguments.Declare.DEFAULT_SCHEME),
            processingEnv.booleanArguments(Arguments.Declare.AGGREGATE_ENABLE),
            processingEnv.arguments(Arguments.Declare.AGGREGATE_GENERATED),
            processingEnv.booleanArguments(Arguments.Declare.CONTEXT_ROUTER_ENABLE)
        ).apply {
            processingEnv.argumentContexts { context, enable ->
                contexts.add(Pair(context, enable))
            }
        }
    }
}

private fun ProcessingEnvironment.argumentContexts(action: (ContextDeclare, Boolean) -> Unit) {
    options?.filter { (key, _) ->
        key.startsWith(Arguments.Declare.JSON_CONTEXT)
    }?.forEach { (key, json) ->
        val generated = booleanArguments(key.replace(Arguments.Declare.JSON_CONTEXT, Arguments.Declare.CONTEXT_ENABLE))
        Logger.e(" APT DBG jsonContext :${json}  generated :${generated}")
        action(ContextDeclare.createByJson(json), generated)
    }
}

