package com.rubik.apt.files.source

import com.ktnail.x.uri.buildVersionUri
import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.files.source.context.action.addApiRouteActionFunctions
import com.rubik.apt.files.source.context.action.inceface.generateRouteActionsFile
import com.rubik.apt.files.source.context.addTouchFunction
import com.rubik.apt.files.source.context.addToucherClass
import com.rubik.apt.files.source.context.dsl.addActivityRouterFunctions
import com.rubik.apt.files.source.context.dsl.generateContextRouteFile
import com.rubik.apt.files.source.identity.generateContextIdentityFile
import com.rubik.apt.files.source.mirror.callback.generateCallbackFiles
import com.rubik.apt.files.source.mirror.objekt.generateObjectFiles
import com.rubik.apt.files.source.mirror.value.generateValueFiles
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.namebox.closeInNameBox
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.addSectionTypes
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.*
import java.io.File

class ContextSourceFiles(private val dictionary: File) {
    fun generate(
        uri: String,
        context: ContextCodeBase,
        routerContextEnable: Boolean
    ) {
        generateContextFile(
            context.contextName,
            uri,
            context.sections,
            context.version,
            dictionary,
            context.routeActionsName
        )
        generateValueFiles(
            uri,
            context.values,
            context.version,
            dictionary
        )
        generateRouteActionsFile(
            context.routeActionsName,
            uri,
            context.sections,
            context.version,
            dictionary
        )
        generateContextIdentityFile(
            context.contextIdName,
            uri,
            context.version,
            context.token,
            dictionary
        )
        if (routerContextEnable) {
            generateContextRouteFile(
                context.routeContextName,
                uri,
                context.sections,
                context.version,
                dictionary
            )
        }
        generateObjectFiles(
            uri,
            context.objects,
            context.version,
            context.routeActionsName,
            dictionary
        )
        generateCallbackFiles(
            uri,
            context.callbacks,
            context.version,
            dictionary
        )
    }

    private fun generateContextFile(
        className: String,
        uri: String,
        routers: SectionCodeBase<RouteCodeBase>,
        version: String,
        dictionary: File,
        routeActionsName: String
    ) {
        FileSpec.builder(Constants.Contexts.Declare.makeContextPackageName(uri), className).closeInNameBox(uri) { nameBox ->
            addAliasedImport(ClassName(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME), Constants.Aggregate.PATH_CLASS_NAME_AS)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.TOUCH_FUNCTION_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.Apis.NAVIGATE_FUNCTION_NAME)
            addImport(Constants.Router.PACKAGE_NAME, Constants.ContextRouters.RUBIK_CLASS_NAME)
            addImport(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.ContextRouters.ROUTE_FUNCTION_NAME)
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
                ).also { rootType ->
                    rootType.addType(
                        TypeSpec.companionObjectBuilder().addProperty(
                            PropertySpec.builder(
                                Constants.Contexts.CONSTANTS_URI_NAME, String::class
                            ).addModifiers(
                                KModifier.CONST
                            ).initializer(
                                "%S", uri
                            ).build()
                        ).addTouchFunction(
                        ).addSectionTypes(routers, true, rootType) { builder, route ->
                            builder.addRouteActionFunctions(uri, route, nameBox)
                        }.addRouteActionProperty(
                            uri, routeActionsName
                        ).addRGeneratedAnnotation(
                            "context_companion", version
                        ).addAnnotation(
                            AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                        ).build()
                    )
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
            ).process().build().writeTo(dictionary)
        }
    }
}

internal fun TypeSpec.Builder.addRouteActionFunctions(
    uri: String,
    route: RouteCodeBase,
    nameBox: FileNameBox
) = apply {
    when(route){
        is ApiCodeBase -> addApiRouteActionFunctions(uri, route, nameBox)
        is ActivityCodeBase -> addActivityRouterFunctions(uri, route, nameBox)
    }
}

fun TypeSpec.Builder.addPathProperties(
    uri: String,
    route: RouteCodeBase
) = apply {
    val name = route.contextPropertyName
    val versionUri = buildVersionUri(uri, route.path, route.version)
    if (Constants.Aggregate.isParameterPath(route.path)) {
        addFunction(
            FunSpec.builder(
                name
            ).addParameter(
                Constants.Aggregate.PARAMETER_VALUES_NAME, String::class, KModifier.VARARG
            ).returns(
                String::class
            ).addStatement(
                "return ${Constants.Aggregate.PATH_CLASS_NAME_AS}(\"$versionUri\").${Constants.Aggregate.METHOD_SET_PARAMETERS_NAME}(${Constants.Aggregate.PARAMETER_VALUES_NAME})"
            ).build()
        )
    } else {
        addProperty(
            PropertySpec.builder(
                name, String::class
            ).addModifiers(
                KModifier.CONST
            ).initializer(
                "%S", versionUri
            ).build()
        )
    }
}

internal fun TypeSpec.Builder.addRouteActionProperty(
    uri: String,
    routeActionsName: String
) = apply {
    addProperty(
        PropertySpec.builder(
            Constants.RouteActions.PROPERTY_ROUTE_ACTION,
            ClassName(Constants.Contexts.Declare.makeContextPackageName(uri), routeActionsName).copy(nullable = true)
        ).addModifiers(
            KModifier.PRIVATE
        ).getter(
            FunSpec.getterBuilder().addStatement(
                "return " + "${Constants.ContextRouters.RUBIK_CLASS_NAME}.${Constants.ContextRouters.FIND_ACTIONS_FUNCTION_NAME}<${routeActionsName}>(${Constants.Contexts.CONSTANTS_URI_NAME})".noSpaces()
            ).build()
        ).build()
    )
}
