package com.rubik.apt.files.source.context

import com.rubik.apt.Constants
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.squareup.kotlinpoet.*

fun TypeSpec.Builder.addToucherClass(version: String): TypeSpec.Builder = apply {
    addType(
        TypeSpec.classBuilder(
            Constants.Contexts.CLASS_TOUCHER
        ).primaryConstructor(
            FunSpec.constructorBuilder().addParameter(
                "action", LambdaTypeName.get(returnType = Unit::class.asClassName())
            ).build()
        ).addProperty(
            PropertySpec.builder(
                "holder",
                ClassName.bestGuess(Constants.Contexts.CLASS_TOUCH_HOLDER)
            ).addModifiers(
                KModifier.PRIVATE
            ).initializer(
                "${Constants.Apis.TOUCH_FUNCTION_NAME}(${Constants.Contexts.CONSTANTS_URI_NAME}, action)"
            ).build()
        ).addFunction(
            FunSpec.builder(
                Constants.Apis.MISS_FUNCTION_NAME
            ).addParameter(
                "action", LambdaTypeName.get(returnType = Unit::class.asClassName())
            ).addStatement(
                "holder.${Constants.Apis.MISS_FUNCTION_NAME}(action)"
            ).build()
        ).addRGeneratedAnnotation(
            "context_toucher",version
        ).addAnnotation(
            AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
        ).build()
    )
}

fun TypeSpec.Builder.addTouchFunction(): TypeSpec.Builder = apply {
    addFunction(
        FunSpec.builder(
            Constants.Apis.TOUCH_FUNCTION_NAME
        ).addParameter(
            ParameterSpec.builder(
                "action", LambdaTypeName.get(returnType = Unit::class.asClassName())
            ).build()
        ).addStatement(
            "return ${Constants.Contexts.CLASS_TOUCHER}(action)"
        ).addAnnotation(
            JvmStatic::class.java
        ).build()
    )
}