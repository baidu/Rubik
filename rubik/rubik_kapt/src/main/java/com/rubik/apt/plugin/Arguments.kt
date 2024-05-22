package com.rubik.apt.plugin

import javax.annotation.processing.ProcessingEnvironment

object Arguments {
    const val KAPT_GENERATED = "kapt.kotlin.generated"

    object Declare {
        const val JSON_CONTEXT = "rubik.context.json"
        const val CONTEXT_LIBS_ENABLE = "rubik.context.libs.enable"
        const val CONTEXT_ROUTER_ENABLE = "rubik.context.router.enable"
        const val CONTEXT_IGNORE_VALUE_ANNOS = "rubik.context.value.ignoreannos"
        const val AGGREGATE_USER_AND_TIME_ENABLE = "rubik.aggregate.userandtime.enable"

        const val AGGREGATE_ENABLE = "rubik.aggregate.enable"
        const val AGGREGATE_GENERATED = "rubik.aggregate.generated"
        const val AGGREGATE_METHOD_SIZE = "rubik.aggregate.method.size"

        const val DEFAULT_SCHEME = "rubik.default.scheme"
    }
}

fun ProcessingEnvironment.arguments(key: String): String? {
    return options?.get(key)
}

fun ProcessingEnvironment.booleanArguments(key: String): Boolean {
    return options?.get(key) == "true"
}

fun ProcessingEnvironment.intArguments(key: String, default: Int = 0): Int {
    return options?.get(key)?.toIntOrNull() ?: default
}
