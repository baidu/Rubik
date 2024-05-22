package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun TypeSpec.Builder.addAggregateFactory(uri: String, context: ContextCodeBase, className: String) = apply {
    addType(
        TypeSpec.companionObjectBuilder()
            .superclass(
                ClassName.bestGuess(Constants.Aggregate.COMPANION_SUPER_NAME)  //  AggregateFactory
            ).addProperty( // URI
                PropertySpec.builder(
                    Constants.Aggregate.PROPERTY_URI_NAME, String::class
                ).addModifiers(
                    KModifier.OVERRIDE
                ).initializer(
                    "%S", uri
                ).build()
            ).addProperty( // EVENT_MSGS
                PropertySpec.builder(
                    Constants.Aggregate.PROPERTY_EVENT_MSGS_NAME,
                    List::class.parameterizedBy(String::class)
                ).addModifiers(
                    KModifier.OVERRIDE
                ).initializer(
                    "listOf(${context.eventsArray})"
                ).build()
            ).addProperty( // CREATOR
                PropertySpec.builder(
                    Constants.Aggregate.PROPERTY_CREATOR_NAME,
                    Function0::class.asTypeName()
                        .parameterizedBy(ClassName.bestGuess(Constants.Aggregate.INTERFACE_NAME))
                ).addModifiers(
                    KModifier.OVERRIDE
                ).initializer(
                    "{${className}()}"
                ).build()
            ).addRGeneratedAnnotation(
                "aggregate_companion", context.version
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
            ).build()
    )
}

