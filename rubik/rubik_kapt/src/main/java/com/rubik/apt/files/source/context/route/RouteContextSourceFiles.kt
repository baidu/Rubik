package com.rubik.apt.files.source.context.route

import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.RouteCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.files.source.context.addActivityRouterFunctions
import com.rubik.apt.utility.addNextLevelSectionTypes
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.addThisLevelSectionTypes
import com.squareup.kotlinpoet.*
import java.io.File


internal fun generateContextRouteFile(
    className: String,
    uri: String,
    routers: SectionCodeBase,
    version: String,
    dictionary: File
) {
    FileSpec.builder(Constants.Contexts.makeContextPackageName(uri), className).apply {
        addImport(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME)
        addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.RESULT_DSL_NAME)
        addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FUNCTION_NAME)
        addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FOR_RESULT_FUNCTION_NAME)
        addType(
            TypeSpec.classBuilder(className).addType(
                TypeSpec.companionObjectBuilder().addProperty(
                    PropertySpec.builder(
                        Constants.Contexts.CONSTANTS_URI_NAME, String::class
                    ).addModifiers(
                        KModifier.CONST
                    ).initializer(
                        "%S", uri
                    ).build()
                ).addThisLevelSectionTypes(routers) { builder, route ->
                    builder.addRouterFunctions(uri, route)
                }.addRGeneratedAnnotation(
                    "context_companion", version
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                        .build()
                ).build()
            ).addNextLevelSectionTypes(routers) { builder, route ->
                builder.addRouterFunctions(uri, route)
            }.addRGeneratedAnnotation(
                "context_route", version
            ).addAnnotation(
                AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                    .build()
            ).addKdoc(
                Constants.KDoc.routerContext(uri, version)
            ).build()
        ).build().writeTo(dictionary)
    }
}

internal fun TypeSpec.Builder.addRouterFunctions(
    uri: String,
    route: RouteCodeBase
) = apply {
    when(route){
        is ApiCodeBase-> addApiRouterDSLFunctions(uri, route)
        is ActivityCodeBase ->addActivityRouterFunctions(uri, route)
    }
}

