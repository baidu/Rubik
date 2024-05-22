package com.rubik.apt.files.source.identity

import com.ktnail.x.md5
import com.rubik.annotations.source.RAggregate
import com.rubik.annotations.source.RContext
import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

internal fun generateContextIdentityFile(
    className: String,
    uri: String,
    version: String,
    token: String,
    dictionary: File
) {
    FileSpec.builder(Constants.Contexts.Declare.makeContextPackageName(uri), className).apply {
        val md5Token = token.md5()
        addType(
            TypeSpec.classBuilder(className).superclass(
                ClassName.bestGuess(Constants.Identity.CONTEXT_ID_SUPER_CLASS_NAME)
            ).addSuperclassConstructorParameter(
                "uri = %S, version = %S, token = %S".noSpaces(), uri, version, md5Token
            ).addRGeneratedAnnotation(
                "context_id", version
            ).addAnnotation(
                AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
            ).addAnnotation(
                AnnotationSpec.builder(RContext::class.java)
                    .addMember("uri = %S", uri)
                    .addMember("version = %S", version)
                    .addMember("token = %S", md5Token).build()
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                    .build()
            ).addKdoc(
                Constants.KDoc.contextId(uri, version, token)
            ).build()
        ).process().build().writeTo(dictionary)
    }
}

internal fun FileSpec.Builder.addAggregateIdentity(
    className: String,
    uri: String,
    version: String,
    token: String,
    kDocUserAndTime: Boolean
) = apply {
    val md5Token = token.md5()
    addType(
        TypeSpec.classBuilder(className).superclass(
            ClassName.bestGuess(Constants.Identity.AGGREGATE_ID_SUPER_CLASS_NAME)
        ).addSuperclassConstructorParameter(
            "uri = %S, version = %S, token = %S".noSpaces(), uri, version, md5Token
        ).addRGeneratedAnnotation(
            "aggregate_Id", version
        ).addAnnotation(
            AnnotationSpec.builder(RAggregate::class.java)
                .addMember("uri = %S", uri)
                .addMember("version = %S", version)
                .addMember("token = %S", md5Token).build()
        ).addAnnotation(
            AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                .build()
        ).addKdoc(
            Constants.KDoc.aggregateId(uri, version,token, kDocUserAndTime)
        ).build()
    )
}