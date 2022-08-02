package com.rubik.apt.files.source.context

import com.ktnail.x.uri.buildVersionUri
import com.rubik.apt.Constants
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.utility.addRGeneratedRouterAnnotation
import com.squareup.kotlinpoet.*

fun TypeSpec.Builder.addActivityRouterFunctions(
    uri: String,
    activity: ActivityCodeBase
) = apply {
    buildVersionUri(uri, activity.path, activity.version).let { apiUri ->
        addFunction(createActivityRouterFunctionBuilder(uri, apiUri, activity, Constants.Activities.CONTEXT_CLASS_NAME).build())
        addFunction(createActivityRouterFunctionBuilder(uri, apiUri, activity, Constants.Activities.ACTIVITY_CLASS_NAME).build())
        addFunction(createActivityRouterFunctionBuilder(uri, apiUri, activity, Constants.Activities.FRAGMENT_CLASS_NAME).build())
    }
}

fun createActivityRouterFunctionBuilder(
    uri: String,
    apiUri: String,
    activity: ActivityCodeBase,
    receiverName: String
) = FunSpec.builder(
    activity.contextFunctionName
).addParameter(
    Constants.Activities.PROPERTY_LAUNCHER, ClassName.bestGuess(receiverName)
).addActivityRouterStatement(
    uri,
    apiUri,
    activity
).addAnnotation(
    JvmStatic::class.java
).addRGeneratedRouterAnnotation(
    apiUri, "ACTIVITY", activity.className, ""
).addKdoc(
    Constants.KDoc.functionRouter(apiUri, activity.className, activity.propertiesKDoc)
)

fun FunSpec.Builder.addActivityRouterStatement(
    contextUri: String,
    apiUri: String,
    activity: ActivityCodeBase
) = apply {
    beginControlFlow("${Constants.Activities.PROPERTY_LAUNCHER}.${Constants.Apis.NAVIGATE_FUNCTION_NAME}") // navigate
    addStatement("${Constants.Apis.URI_DSL_NAME}=\"$apiUri\"") // uri
    addStatement("${Constants.Apis.CHECK_ROUTER_VERSION_DSL_NAME}(${Constants.Router.CHECK_ROUTER_VERSION})") // checkRouterVersion

    beginControlFlow(Constants.Apis.QUERY_DSL_NAME) // query
    addStatement("${Constants.Activities.FLAGS_DSL_NAME}=${Constants.Activities.PROPERTY_INTENT_FLAGS}") // flags
    addStatement("${Constants.Activities.REQUEST_CODE_DSL_NAME}=${Constants.Activities.PROPERTY_REQUEST_CODE}") // requestCode
    activity.sortedProperties.forEach { property ->
        addParameter(
            ParameterSpec.builder(
                property.legalName, property.toContextTypeName(contextUri)
            ).build()
        )
        addStatement(property.makeAddToQueryCode())
    }
    addParameter(
        ParameterSpec.builder(
            Constants.Activities.PROPERTY_INTENT_FLAGS,
            Int::class.asTypeName().copy(nullable = true)
        ).defaultValue("null").build()
    )
    addParameter(
        ParameterSpec.builder(
            Constants.Activities.PROPERTY_REQUEST_CODE,
            Int::class.asTypeName().copy(nullable = true)
        ).defaultValue("null").build()
    )
    endControlFlow()

    endControlFlow() // navigate end
}