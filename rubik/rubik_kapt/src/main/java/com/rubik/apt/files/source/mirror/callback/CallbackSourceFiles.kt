package com.rubik.apt.files.source.mirror.callback

import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.callback.ObjectCallbackCodeBase
import com.rubik.apt.codebase.invoker.InvokeFunctionCodeBase
import com.rubik.apt.codebase.packageName
import com.rubik.apt.namebox.closeInNameBox
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.*
import java.io.File

internal fun generateCallbackFiles(
    uri: String,
    callbacks: List<ObjectCallbackCodeBase>,
    version: String,
    provideDirectory: File
) {
    callbacks.forEach { callbackObject ->
        FileSpec.builder(
            callbackObject.packageName(uri),
            callbackObject.simpleName
        ).closeInNameBox(uri) { nameBox ->
            nameBox.import(Constants.Router.PACKAGE_NAME, Constants.ContextRouters.RUBIK_CLASS_NAME)
            addType(
                TypeSpec.interfaceBuilder(
                    callbackObject.simpleName
                ).addCallbackFunction(
                    uri, callbackObject.functions
                ).addRGeneratedAnnotation(
                    "callback",version
                ).addAnnotation(
                    AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                ).addKdoc(
                    Constants.KDoc.callback(uri, version)
                ).build()
            ).process().build().writeTo(provideDirectory)
        }
    }
}

private fun TypeSpec.Builder.addCallbackFunction(
    uri: String,
    functions: List<InvokeFunctionCodeBase>
) = apply {
    functions.forEach { function ->
        addFunction(
            FunSpec.builder(
                function.name
            ).addModifiers(KModifier.ABSTRACT).apply {
                function.contextParameters(uri).forEach { (name, type) ->
                    addParameter(name, type)
                }
            }.clearBody().build()
        )
    }
}