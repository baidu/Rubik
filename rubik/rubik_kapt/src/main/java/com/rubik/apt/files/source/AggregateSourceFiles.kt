package com.rubik.apt.files.source

import com.rubik.apt.Constants
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.files.source.aggregate.addActivityStatements
import com.rubik.apt.files.source.aggregate.addApiStatements
import com.rubik.apt.files.source.aggregate.addEventStatements
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File

class AggregateSourceFiles(
    private val directory: File
)  {
    fun generate(
        contexts: Map<String, ContextCodeBase>
    ) {
        contexts.forEach { (uri, context) ->
            doGenerate(uri, context)
        }
    }

    private fun doGenerate(
        uri: String,
        context: ContextCodeBase
    ) {
        val className = context.getAggregateName()
        FileSpec.builder(Constants.Aggregate.Declare.makeAggregatePackageName(uri), className)
            .addImport(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME)
            .addImport(
                Constants.Aggregate.RESULT_PACKAGE_NAME,
                Constants.Aggregate.RESULT_CLASS_NAME
            )
            .addImport(
                Constants.Aggregate.LAUNCHER_PACKAGE_NAME,
                Constants.Aggregate.LAUNCHER_CLASS_NAME
            )
            .addImport(
                Constants.Aggregate.RESULT_PACKAGE_NAME,
                "set"
            )
            .addType(
                TypeSpec.classBuilder(className).addSuperinterface(
                    ClassName.bestGuess(Constants.Aggregate.Declare.INTERFACE_NAME)
                ).addType(
                    TypeSpec.companionObjectBuilder()
                        .superclass(
                            ClassName.bestGuess( Constants.Aggregate.COMPANION_SUPER_NAME)
                        ).addProperty(
                            PropertySpec.builder(
                                Constants.Aggregate.Declare.PROPERTY_URI_NAME, String::class
                            ).addModifiers(
                                KModifier.OVERRIDE
                            ).initializer(
                                "%S", uri
                            ).build()
                        ).addProperty(
                            PropertySpec.builder(
                                Constants.Aggregate.PROPERTY_DEPENDENCIES_NAME,
                                List::class.parameterizedBy(String::class)
                            ).addModifiers(
                                KModifier.OVERRIDE
                            ).initializer(
                                "listOf(${context.dependencies.joinToString(",") { "\"$it\"" }})"
                            ).build()
                        ).addProperty(
                            PropertySpec.builder(
                                Constants.Aggregate.Declare.PROPERTY_EVENT_MSGS_NAME,
                                List::class.parameterizedBy(String::class)
                            ).addModifiers(
                                KModifier.OVERRIDE
                            ).initializer(
                                "listOf(${context.events.keys.joinToString(",") { "\"$it\"" }})"
                            ).build()
                        ).addProperty(
                            PropertySpec.builder(
                                Constants.Aggregate.PROPERTY_CREATOR_NAME,
                                Function0::class.asTypeName().parameterizedBy(ClassName.bestGuess(Constants.Aggregate.Declare.INTERFACE_NAME))
                            ).addModifiers(
                                KModifier.OVERRIDE
                            ).initializer(
                                "{${className}()}"
                            ).build()
                        ).addRGeneratedAnnotation(
                            "aggregate_companion", context.version
                        ).addAnnotation(
                            AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                                .build()
                        ).build()
                ).addFunction(
                    FunSpec.builder(
                        Constants.Aggregate.METHOD_ON_EVENT_NAME
                    ).addModifiers(
                        KModifier.OVERRIDE
                    ).addParameter(
                        Constants.Aggregate.LIVE_PARAMETER_MSG_NAME, String::class
                    ).addParameter(
                        Constants.Aggregate.ROUTE_PARAMETER_QUERIES_NAME,
                        ClassName.bestGuess(Constants.Aggregate.QUERIES_FULL_CLASS_NAME)
                    ).addLiveStatements {
                        addEventStatements(context.events)
                    }.build()
                ).addFunction(
                    FunSpec.builder(
                        Constants.Aggregate.METHOD_ON_ROUTE_NAME
                    ).addModifiers(
                        KModifier.OVERRIDE
                    ).addParameter(
                        Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME, String::class
                    ).addParameter(
                        Constants.Aggregate.ROUTE_PARAMETER_QUERIES_NAME,
                        ClassName.bestGuess(Constants.Aggregate.QUERIES_FULL_CLASS_NAME)
                    ).addParameter(
                        Constants.Aggregate.ROUTE_PARAMETER_RESULTS_NAME,
                        List::class.parameterizedBy(Class.forName(Constants.Aggregate.RESULTS_FULL_CLASS_NAME).kotlin)
                    ).addRouteStatements {
                        addApiStatements(uri, context.apis)
                        addActivityStatements(context.activities)
                    }.build()
                ).addRGeneratedAnnotation(
                    "aggregate", context.version
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                        .build()
                ).addKdoc(
                    Constants.KDoc.aggregate(uri, context.version)
                ).build()
            ).build().writeTo(directory)
    }
}

fun FunSpec.Builder.addLiveStatements(block: FunSpec.Builder.() -> Unit) = apply {
    beginControlFlow("when(${Constants.Aggregate.LIVE_PARAMETER_MSG_NAME}){")
    block.invoke(this)
    addStatement("else -> {}" )
    endControlFlow()
}

fun FunSpec.Builder.addRouteStatements(block: FunSpec.Builder.() -> Unit) = apply {
    beginControlFlow("when {")
    block.invoke(this)
    addStatement("else -> { throw ${Constants.Aggregate.makeRouteExceptionCode()}}" )
    endControlFlow()
}
