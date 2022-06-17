package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.ktnail.x.uri.buildVersionPath
import com.squareup.kotlinpoet.FunSpec


fun FunSpec.Builder.addActivityStatements(activities: Map<String, ActivityCodeBase>) = apply {
    activities.forEach { (_, activity) ->
        val path = buildVersionPath(activity.path, activity.version)
        if(Constants.Aggregate.isParameterPath(path))
            beginControlFlow("${Constants.Aggregate.PATH_CLASS_NAME}(\"$path\").${Constants.Aggregate.METHOD_MATCHING_NAME}(${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME}) -> ")
        else
            beginControlFlow("\"$path\" == ${Constants.Aggregate.ROUTE_PARAMETER_PATH_NAME} -> ")
        Constants.KDoc.function(
            activity.className,
            activity.propertiesKDoc
        ).forEach {doc->
            addComment(doc)
        }
        addStatement(activity.startCode(path))
        endControlFlow()
    }
}