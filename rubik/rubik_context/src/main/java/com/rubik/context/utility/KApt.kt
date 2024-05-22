package com.rubik.context.utility

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.uriToSnake
import com.rubik.context.extra.Context
import com.rubik.context.utility.Arguments.Declare.JSON_CONTEXT
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

object Kapt {
    const val EXTENSION_NAME = "kapt"
    const val CONFIGURATION_NAME = "kapt"
}

object Arguments {
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

val Project.kaptExtension: KaptExtension?
    get() = extensions.findByName(Kapt.EXTENSION_NAME) as? KaptExtension

fun Project.putKaptArgument(key: String, value: Any) {
    kaptExtension?.arguments {
        arg(key, value)
    }
}

fun Project.putKaptBooleanArgument(key: String, value: Boolean) {
    putKaptArgument(key, value.toString())
}


fun BaseVariant.putKaptArgument(key: String, value: Any) {
    javaCompileOptions.annotationProcessorOptions.arguments[key] = value.toString()
}

fun BaseVariant.putKaptBooleanArgument(key: String, value: Boolean) {
    putKaptArgument(key, value.toString())
}

fun BaseVariant.putKaptContext(context: Context, version: String) {
    context.id.addVersion(version)
    putKaptArgument("$JSON_CONTEXT.${context.uri.uriToSnake()}", context.id.toJson())
}

fun BaseVariant.putKaptContextLibsEnable(context: Context) {
    putKaptBooleanArgument("${Arguments.Declare.CONTEXT_LIBS_ENABLE}.${context.uri.uriToSnake()}", true)
}

fun BaseVariant.putKaptAggregateEnable(context: Context) {
    putKaptBooleanArgument("${Arguments.Declare.AGGREGATE_ENABLE}.${context.uri.uriToSnake()}", true)
}

