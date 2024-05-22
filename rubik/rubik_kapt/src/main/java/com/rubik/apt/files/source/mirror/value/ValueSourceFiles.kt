package com.rubik.apt.files.source.mirror.value

import com.rubik.annotations.source.RContextLib
import com.rubik.annotations.source.RGeneratedValue
import com.rubik.apt.Constants
import com.rubik.apt.codebase.packageName
import com.rubik.apt.codebase.value.ValueCodeBase
import com.rubik.apt.utility.addAnnotations
import com.rubik.apt.utility.addInterfaces
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.*
import java.io.File

internal fun generateValueFiles(
    uri: String,
    values: List<ValueCodeBase>,
    version: String,
    provideDirectory: File
) {
    values.forEach { value ->
        with(FileSpec) {
            builder(
                value.packageName(uri),
                value.simpleName
            ).addType(
                TypeSpec.classBuilder(value.simpleName).primaryConstructor(
                    FunSpec.constructorBuilder().addConstructorParameter(
                        value.fields,
                        uri
                    ).build()
                ).addAnnotations(
                    value.annotations
                ).addFieldsProperty(
                    value.fields,
                    uri
                ).addConstantsProperty(
                    value.fields,
                    uri
                ).addInterfaces(
                    value.interfaces
                ).addRGeneratedAnnotation(
                    "value", version
                ).addAnnotation(
                    AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(RGeneratedValue::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                ).addKdoc(
                    Constants.KDoc.value(uri, version)
                ).build()
            ).process().build().writeTo(provideDirectory)
        }
    }
}
