package com.rubik.apt.files.source

import com.rubik.annotations.source.RContextLib
import com.rubik.annotations.source.RGeneratedValue
import com.rubik.apt.Constants
import com.rubik.apt.codebase.AnnotationCodeBase
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.RouteCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.codebase.value.ValueCodeBase
import com.rubik.apt.files.source.context.*
import com.rubik.apt.files.source.value.addAnnotationMembers
import com.rubik.apt.files.source.value.addConstantsProperty
import com.rubik.apt.files.source.value.addConstructorParameter
import com.rubik.apt.files.source.value.addFieldsProperty
import com.rubik.apt.utility.addNextLevelSectionTypes
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.addSectionTypes
import com.rubik.apt.utility.addThisLevelSectionTypes
import com.squareup.kotlinpoet.*
import java.io.File

class ContextSourceFiles(private val dictionary: File) {
    fun generate(
        contexts: Map<String, ContextCodeBase>
    ) {
        contexts.forEach { (uri, value) ->
            generateContextFile(
                value.getContextName(),
                uri,
                value.sections,
                value.version,
                dictionary
            )
            generateValueFiles(
                uri,
                value.values,
                value.version,
                dictionary
            )
        }
    }

    private fun generateContextFile(
        className: String,
        uri: String,
        routers: SectionCodeBase,
        version: String,
        dictionary: File
    ) {
        FileSpec.builder(Constants.Contexts.makeContextPackageName(uri), className).apply {
            addImport(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.TOUCH_FUNCTION_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.RESULT_DSL_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FUNCTION_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FOR_RESULT_FUNCTION_NAME)
            addType(
                TypeSpec.classBuilder(className).addType(
                    TypeSpec.objectBuilder(
                        Constants.Contexts.OBJECT_URIS_NAME
                    ).addSectionTypes(routers) { builder, route ->
                        builder.addPathProperties(uri, route)
                    }.addRGeneratedAnnotation(
                        "context_uris", version
                    ).addAnnotation(
                        AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                    ).build()
                ).addType(
                    TypeSpec.companionObjectBuilder().addProperty(
                        PropertySpec.builder(
                            Constants.Contexts.CONSTANTS_URI_NAME, String::class
                        ).addModifiers(
                            KModifier.CONST
                        ).initializer(
                            "%S", uri
                        ).build()
                    ).addTouchFunction(
                    ).addThisLevelSectionTypes(routers) { builder, route ->
                        builder.addRouterFunctions(uri, route)
                    }.addRGeneratedAnnotation(
                        "context_companion",version
                    ).addAnnotation(
                        AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                    ).build()
                ).addNextLevelSectionTypes(routers) { builder, route ->
                    builder.addRouterFunctions(uri, route)
                }.addToucherClass(
                    version
                ).addRGeneratedAnnotation(
                    "context",version
                ).addAnnotation(
                    AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                ).addKdoc(
                    Constants.KDoc.context(uri, version)
                ).build()
            ).build().writeTo(dictionary)
        }
    }

    private fun generateValueFiles(
        uri: String,
        values: List<ValueCodeBase>,
        version: String,
        provideDirectory: File
    ) {
        values.forEach { value ->
            FileSpec.builder(Constants.Contexts.makeContextPackageName(uri), value.className).addType(
                TypeSpec.classBuilder(value.className).primaryConstructor(
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
                ).addInterfaces(value.interfaces)
                    .addRGeneratedAnnotation(
                    "value",version
                ).addAnnotation(
                    AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(RGeneratedValue::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                ).addKdoc(
                    Constants.KDoc.context(uri, version)
                ).build()
            ).build().writeTo(provideDirectory)
        }
    }

    private fun TypeSpec.Builder.addRouterFunctions(
        uri: String,
        route: RouteCodeBase
    ) = apply {
        when(route){
            is ApiCodeBase-> addApiRouterFunctions(uri, route)
            is ActivityCodeBase ->addActivityRouterFunctions(uri, route)
        }
    }

    private fun TypeSpec.Builder.addAnnotations(annotations: List<AnnotationCodeBase>) = apply {
        addAnnotations(annotations.map { codebase ->
            AnnotationSpec.builder(
                Class.forName(
                    codebase.className
                ).asClassName()
            ).addAnnotationMembers(codebase.members).build()
        })
    }

    private fun TypeSpec.Builder.addInterfaces(interfaces: List<ClassName>) = apply {
        interfaces.forEach { name ->
            addSuperinterface(name)
        }
    }
}


