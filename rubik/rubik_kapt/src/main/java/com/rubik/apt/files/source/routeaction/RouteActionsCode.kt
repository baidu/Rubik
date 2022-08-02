package com.rubik.apt.files.source.routeaction

import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.files.source.context.addApiRouterParametersAndResult
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.squareup.kotlinpoet.*
import java.io.File

internal fun generateRouteActionsFile(
    className: String,
    uri: String,
    routers: SectionCodeBase,
    version: String,
    dictionary: File
) {
    FileSpec.builder(Constants.Contexts.makeContextPackageName(uri), className).apply {
        addType(
            TypeSpec.interfaceBuilder(className).addSuperinterface(
                ClassName.bestGuess(Constants.RouteActions.INTERFACE_NAME)
            ).addRouteActionsFunction(
                uri, routers
            ).addRGeneratedAnnotation(
                "route_actions", version
            ).addAnnotation(
                AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
            ).addKdoc(
                Constants.KDoc.routeActions(uri, version)
            ).build()
        ).build().writeTo(dictionary)
    }
}

private fun TypeSpec.Builder.addApiRouteActionsFunctions(
    uri: String,
    api: ApiCodeBase
) = apply {
    val functionName = api.actionFunctionName
    val contextInvoker = api.contextInvoker(uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addModifiers(
            KModifier.ABSTRACT
        ).addApiRouterParametersAndResult(
            uri,
            contextInvoker,
            false
        ).build()
    )
}

internal fun TypeSpec.Builder.addRouteActionsFunction(uri: String, routers: SectionCodeBase) =
    apply {
        routers.forEachRoute { route ->
            if (route is ApiCodeBase) {
                addApiRouteActionsFunctions(uri, route)
            }
        }
    }