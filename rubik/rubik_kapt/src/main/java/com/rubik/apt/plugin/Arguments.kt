package com.rubik.apt.plugin

import javax.annotation.processing.ProcessingEnvironment

object Arguments {
    const val KAPT_GENERATED = "kapt.kotlin.generated"

    object Declare {
        const val JSON_CONTEXT = "rubik.json.context"
        const val AGGREGATE_ENABLE = "rubik.aggregate.enable"
        const val AGGREGATE_GENERATED = "rubik.aggregate.generated"
        const val CONTEXT_ENABLE = "rubik.context.enable"
        const val DEFAULT_SCHEME = "rubik.default.scheme"
        const val CONTEXT_ROUTER_ENABLE = "rubik.context.router.enable"
    }
}

fun ProcessingEnvironment.arguments(key: String): String? {
    return options?.get(key)
}

fun ProcessingEnvironment.booleanArguments(key: String): Boolean {
    return options?.get(key) == "true"
}
