package com.rubik.apt.files.source

import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.RouteCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.files.source.context.*
import com.rubik.apt.files.source.context.route.generateContextRouteFile
import com.rubik.apt.files.source.routeaction.generateRouteActionsFile
import com.rubik.apt.files.source.value.generateValueFiles
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
                dictionary,
                value.getRouteActionsName()
            )
            generateValueFiles(
                uri,
                value.values,
                value.version,
                dictionary
            )
            generateRouteActionsFile(
                value.getRouteActionsName(),
                uri,
                value.sections,
                value.version,
                dictionary
            )
            generateContextRouteFile(
                value.getRouteContextName(),
                uri,
                value.sections,
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
        dictionary: File,
        routeActionsName: String
    ) {
        FileSpec.builder(Constants.Contexts.makeContextPackageName(uri), className).apply {
            addImport(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.TOUCH_FUNCTION_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FUNCTION_NAME)
            addImport(Constants.Router.PACKAGE_NAME, Constants.ContextRouters.RUBIK_CLASS_NAME)
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
                        builder.addRouteActionFunctions(uri, route)
                    }.addRouteActionProperty(
                        uri, routeActionsName
                    ).addRGeneratedAnnotation(
                        "context_companion",version
                    ).addAnnotation(
                        AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                    ).build()
                ).addNextLevelSectionTypes(routers) { builder, route ->
                    builder.addRouteActionFunctions(uri, route)
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
}

internal fun TypeSpec.Builder.addRouteActionFunctions(
    uri: String,
    route: RouteCodeBase
) = apply {
    when(route){
        is ApiCodeBase -> addApiRouteActionFunctions(uri, route)
        is ActivityCodeBase ->addActivityRouterFunctions(uri, route)
    }
}

internal fun TypeSpec.Builder.addRouteActionProperty(
    uri: String,
    routeActionsName: String
) = apply {
    addProperty(
        PropertySpec.builder(
            Constants.RouteActions.PROPERTY_ROUTE_ACTION,
            ClassName(Constants.Contexts.makeContextPackageName(uri), routeActionsName)
        ).addModifiers(
            KModifier.PRIVATE
        ).getter(
            FunSpec.getterBuilder().addStatement(
                "return ${Constants.ContextRouters.RUBIK_CLASS_NAME}.${Constants.ContextRouters.FIND_ACTIONS_FUNCTION_NAME}<${routeActionsName}>(${Constants.Contexts.CONSTANTS_URI_NAME})"
            ).build()
        ).build()
    )
}
