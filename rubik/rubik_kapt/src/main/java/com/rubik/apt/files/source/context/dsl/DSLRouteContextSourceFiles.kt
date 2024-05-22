package com.rubik.apt.files.source.context.dsl

import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.namebox.closeInNameBox
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.addSectionTypes
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.*
import java.io.File


internal fun generateContextRouteFile(
    className: String,
    uri: String,
    routers: SectionCodeBase<RouteCodeBase>,
    version: String,
    dictionary: File
) {
    FileSpec.builder(Constants.Contexts.Declare.makeContextPackageName(uri), className).closeInNameBox(uri) { nameBox ->
        addAliasedImport(ClassName(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME), Constants.Aggregate.PATH_CLASS_NAME_AS)
        addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.RESULT_DSL_NAME)
        addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FUNCTION_NAME)
        addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FOR_RESULT_FUNCTION_NAME)
        addType(
            TypeSpec.classBuilder(className).also { rootType ->
                rootType.addType(
                    TypeSpec.companionObjectBuilder().addProperty(
                        PropertySpec.builder(
                            Constants.Contexts.CONSTANTS_URI_NAME, String::class
                        ).addModifiers(
                            KModifier.CONST
                        ).initializer(
                            "%S", uri
                        ).build()
                    ).addSectionTypes(routers, true, rootType) { builder, route ->
                        builder.addRouterFunctions(uri, route, nameBox)
                    }.addRGeneratedAnnotation(
                        "context_companion", version
                    ).addAnnotation(
                        AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                            .build()
                    ).build()
                )
            }.addRGeneratedAnnotation(
                "context_route", version
            ).addAnnotation(
                AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri)
                    .build()
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                    .build()
            ).addKdoc(
                Constants.KDoc.routerContext(uri, version)
            ).build()
        ).process().build().writeTo(dictionary)
    }
}

internal fun TypeSpec.Builder.addRouterFunctions(
    uri: String,
    route: RouteCodeBase,
    nameBox: FileNameBox
) = apply {
    when (route) {
        is ApiCodeBase -> addApiRouterDSLFunctions(uri, route, nameBox)
        is ActivityCodeBase -> addActivityRouterFunctions(uri, route, nameBox)
    }
}

