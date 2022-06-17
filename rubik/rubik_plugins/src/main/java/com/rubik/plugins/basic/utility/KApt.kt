package com.rubik.plugins.basic.utility

import com.ktnail.x.camelToPascal
import com.ktnail.x.toCamel
import com.ktnail.x.uriToSnake
import com.rubik.plugins.extension.context.ContextExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

object Kapt {
    const val EXTENSION_NAME = "kapt"
    const val CONFIGURATION_NAME = "kapt"
}

object Arguments {
    object Declare {
        const val JSON_CONTEXT = "rubik.json.context"
        const val AGGREGATE_ENABLE = "rubik.aggregate.enable"
        const val AGGREGATE_GENERATED = "rubik.aggregate.generated"
        const val CONTEXT_ENABLE = "rubik.context.enable"
        const val DEFAULT_SCHEME = "rubik.default.scheme"
    }
}

val Project.kaptExtension: KaptExtension?
    get() = extensions.findByName(Kapt.EXTENSION_NAME) as? KaptExtension

fun Project.getKaptArgument(key: String): String? =
    kaptExtension?.getAdditionalArguments(this, null, null)?.get(key)

fun Project.putKaptArgument(key: String, value: String) {
    kaptExtension?.arguments {
        arg(key, value)
    }
}

fun Project.putKaptBooleanArgument(key: String, value: Boolean) {
    putKaptArgument(key, value.toString())
}

fun Project.putKaptContext(context: ContextExtension) {
    context.toPublishContextDeclare().let { declare->
        kaptExtension?.arguments {
            arg("${Arguments.Declare.JSON_CONTEXT}.${context.uri.uriToSnake()}", declare.toJson())
        }
    }
}

fun Project.putKaptContextEnable(context: ContextExtension) {
    kaptExtension?.arguments {
        arg("${Arguments.Declare.CONTEXT_ENABLE}.${context.uri.uriToSnake()}", true)
    }
}

fun Project.getKaptTask(variantName: String) =
    tasks.findByName(toCamel("kapt", variantName.camelToPascal(), "kotlin"))
