package com.rubik.apt.files.source.mirror.objekt

import com.ktnail.x.uri.buildVersionUri
import com.rubik.annotations.source.RContextLib
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.objekt.ObjectCodeBase
import com.rubik.apt.codebase.packageName
import com.rubik.apt.files.source.addRouteActionProperty
import com.rubik.apt.files.source.context.action.addApiRouteActionCallRouterBody
import com.rubik.apt.files.source.context.action.inceface.addApiRouteActionParametersAndResult
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.namebox.closeInNameBox
import com.rubik.apt.utility.*
import com.squareup.kotlinpoet.*
import java.io.File

internal fun generateObjectFiles(
    uri: String,
    objects: MutableMap<String, ObjectCodeBase>,
    version: String,
    routeActionsName: String,
    provideDirectory: File
) {
    objects.forEach { (_, objekt) ->
        val routers = objekt.sections
        FileSpec.builder(
            objekt.packageName(uri),
            objekt.simpleName
        ).closeInNameBox(uri) { nameBox ->
            nameBox.import(Constants.Router.PACKAGE_NAME, Constants.ContextRouters.RUBIK_CLASS_NAME)
            nameBox.import(Constants.Apis.NAVIGATE_FUNCTION_PACKAGE_NAME, Constants.ContextRouters.ROUTE_FUNCTION_NAME)
            addType(
                TypeSpec.classBuilder(objekt.simpleName).superclass(
                    ClassName.bestGuess(Constants.Object.SUPER_NAME)
                ).primaryConstructor(
                    FunSpec.constructorBuilder().addParameter(
                        Constants.Object.SUB_OBJECT_ORIGINAL_FILED_NAME,
                        Any::class.asTypeName().copy(nullable = true)
                    ).addModifiers(KModifier.PRIVATE).build()
                ).addSuperclassConstructorParameter(
                    Constants.Object.SUB_OBJECT_ORIGINAL_FILED_NAME
                ).addSectionTypes(routers, false) { builder, route ->
                    builder.addApiRouteActionFunctions(uri, route, nameBox)
                }.addType(
                    TypeSpec.companionObjectBuilder().addProperty(
                        PropertySpec.builder(
                            Constants.Contexts.CONSTANTS_URI_NAME, String::class
                        ).addModifiers(
                            KModifier.CONST
                        ).initializer(
                            "%S", uri
                        ).build()
                    ).addRouteActionProperty(
                        uri, routeActionsName
                    ).addCreator(
                        objekt.simpleName
                    ).addFakeConstructors(
                        objekt.constructors, nameBox
                    ).addRGeneratedAnnotation(
                        "object_companion",version
                    ).addAnnotation(
                        AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                    ).build()
                ).addRGeneratedAnnotation(
                    "object",version
                ).addAnnotation(
                    AnnotationSpec.builder(RContextLib::class.java).addMember("uri = %S", uri).build()
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
                ).addKdoc(
                    Constants.KDoc.objekt(uri, version)
                ).build()
            ).process().build().writeTo(provideDirectory)
        }
    }
}

private fun TypeSpec.Builder.addFakeConstructors(
    constructors: MutableList<ApiCodeBase>,
    nameBox: FileNameBox
) = apply {
    constructors.forEach { constructor ->
        val contextInvoker = constructor.contextInvoker(nameBox.uri)
        val apiUri = buildVersionUri(nameBox.uri, constructor.path, constructor.version)
        addFunction(
            FunSpec.builder(
                "invoke"
            ).addModifiers(
                KModifier.OPERATOR
            ).addApiRouteActionParametersAndResult(
                contextInvoker,
                nameBox,
                true
            ).addApiRouteActionCallRouterBody(
                contextInvoker,
                constructor.actionFunctionName,
                nameBox
            ).addRGeneratedRouterAnnotation(
                apiUri,
                constructor.invoker.type.toString(),
                constructor.invoker.clazz.name,
                constructor.invoker.name
            ).addKdoc(
                Constants.KDoc.functionRouter(
                    apiUri,
                    constructor.invoker.location,
                    constructor.invoker.queriesKDoc,
                    constructor.invoker.resultKDoc
                )
            ).build())
    }
}

private fun TypeSpec.Builder.addCreator(
    className :String
) = apply {
    addFunction(
        FunSpec.builder(
            "create"
        ).addParameter(
            Constants.Object.SUB_OBJECT_ORIGINAL_FILED_NAME,
            Any::class.asTypeName().copy(nullable = true)
        ).addStatement(
            "return " + "${className}(${Constants.Object.SUB_OBJECT_ORIGINAL_FILED_NAME})".noSpaces()
        ).build()
    )
}

fun TypeSpec.Builder.addApiRouteActionFunctions(
    uri: String,
    api: ApiCodeBase,
    nameBox: FileNameBox
) = apply {
    val functionName = api.contextFunctionName
    val apiUri = buildVersionUri(uri, api.path, api.version)
    val contextInvoker = api.objectContextInvoker(uri)
    addFunction(
        FunSpec.builder(
            functionName
        ).addApiRouteActionParametersAndResult(
            contextInvoker,
            nameBox,
            true
        ).addApiRouteActionCallRouterBody(
            contextInvoker,
            api.actionFunctionName,
            nameBox
        ).addRGeneratedRouterAnnotation(
            apiUri,
            api.invoker.type.toString(),
            api.invoker.clazz.name,
            api.invoker.name
        ).addKdoc(
            Constants.KDoc.functionRouter(
                apiUri,
                api.invoker.location,
                api.invoker.queriesKDoc,
                api.invoker.resultKDoc
            )
        ).build()
    )
}
