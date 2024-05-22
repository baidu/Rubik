package com.rubik.apt.files.source.context.action.inceface

import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.namebox.closeInNameBox
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.*
import java.io.File

internal fun generateRouteActionsFile(
    className: String,
    uri: String,
    routers: SectionCodeBase<RouteCodeBase>,
    version: String,
    dictionary: File
) {
    FileSpec.builder(Constants.Contexts.Declare.makeContextPackageName(uri), className).closeInNameBox(uri) { nameBox ->
        addType(
            TypeSpec.interfaceBuilder(className).addSuperinterface(
                ClassName.bestGuess(Constants.RouteActions.INTERFACE_NAME)
            ).addRouteActionsFunction(
                uri, routers, nameBox
            ).addRGeneratedAnnotation(
                "route_actions", version
            ).addAnnotation(
                AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
            ).addKdoc(
                Constants.KDoc.routeActions(uri, version)
            ).build()
        ).process().build().writeTo(dictionary)
    }
}

private fun TypeSpec.Builder.addRouteActionsFunction(
    uri: String,
    routers: SectionCodeBase<RouteCodeBase>,
    nameBox: FileNameBox
) = apply {
    routers.forEachRoute { route ->
        if (route is ApiCodeBase) {
            addApiRouteActionsFunctions(uri, route, nameBox)
        }
    }
}

/**
 *  add abstract RouteAction function
 */
private fun TypeSpec.Builder.addApiRouteActionsFunctions(
    uri: String,
    api: ApiCodeBase,
    nameBox: FileNameBox
) = apply {
    val functionName = api.actionFunctionName
    val contextInvoker = api.contextInvoker(uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addModifiers(
            KModifier.ABSTRACT
        ).addApiRouteActionParametersAndResult(
            contextInvoker,
            nameBox,
            false
        ).build()
    )
}
