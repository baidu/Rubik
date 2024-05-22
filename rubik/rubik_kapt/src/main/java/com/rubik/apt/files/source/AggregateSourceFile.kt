package com.rubik.apt.files.source

import com.rubik.apt.Constants
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.files.source.aggregate.*
import com.rubik.apt.files.source.identity.addAggregateIdentity
import com.rubik.apt.namebox.closeInNameBox
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.process
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class AggregateSourceFile(
    private val directory: File,
    private val methodSize: Int,
    private val kDocUserAndTime: Boolean
) {
    fun generate(
        uri: String,
        context: ContextCodeBase
    ) {
        val className = context.aggregateName
        FileSpec.builder(Constants.Aggregate.Declare.makeAggregatePackageName(uri), className).closeInNameBox(uri) { nameBox ->
            nameBox.close(ClassName(Constants.Aggregate.PATH_PACKAGE_NAME, Constants.Aggregate.PATH_CLASS_NAME), asName = Constants.Aggregate.PATH_CLASS_NAME_AS)
            nameBox.close(ClassName(Constants.Aggregate.ROUTE_PACKAGE_NAME, Constants.Aggregate.RESULT_CLASS_NAME), asName = Constants.Aggregate.RESULT_CLASS_NAME_AS)
            nameBox.close(ClassName(Constants.Aggregate.LAUNCHER_PACKAGE_NAME, Constants.Aggregate.LAUNCHER_CLASS_NAME), asName = Constants.Aggregate.LAUNCHER_CLASS_NAME_AS)
            nameBox.close(ClassName(Constants.Aggregate.ROUTE_PACKAGE_NAME, Constants.Aggregate.QUERIES_CLASS_NAME), asName = Constants.Aggregate.QUERIES_CLASS_NAME_AS)
            nameBox.close(ClassName(Constants.Aggregate.ROUTE_PACKAGE_NAME, Constants.Aggregate.RESULTS_CLASS_NAME), asName = Constants.Aggregate.RESULTS_CLASS_NAME_AS)
            nameBox.import(Constants.Aggregate.MAPPING_PACKAGE_NAME, Constants.Aggregate.CAST_TO_TYPE_OF_T_FUNCTION_NAME)
            nameBox.import(Constants.Aggregate.MAPPING_PACKAGE_NAME, Constants.Aggregate.TO_TYPE_OF_T_FUNCTION_NAME)
            nameBox.import(Constants.Aggregate.MAPPING_PACKAGE_NAME, Constants.Aggregate.MAP_TO_TYPE_FUNCTION_NAME)
            nameBox.closeClassMirrorable(uri, context)
            addType(
                TypeSpec.classBuilder(className).addSuperinterface(
                    ClassName.bestGuess(Constants.Aggregate.INTERFACE_NAME)
                ).addSuperinterface(
                    ClassName.bestGuess("${Constants.Contexts.Declare.makeContextPackageName(uri)}.${context.routeActionsName}")
                ).addFunction( // onEvent
                    onEventFunction(context, nameBox)
                ).addOnRouteFunction( // onRoute
                    context, nameBox, methodSize
                ).addRouteActionsImplFunctions( // RouteActionsImpl
                    context.sections, nameBox
                ).addMappings(
                    context, nameBox
                ).addAggregateFactory(
                    uri, context, className
                ).addRGeneratedAnnotation(
                    "aggregate", context.version
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                        .build()
                ).addKdoc(
                    Constants.KDoc.aggregate(uri, context.version, kDocUserAndTime)
                ).build()
            ).addAggregateIdentity( // AggregateId
                context.aggregateIdName,
                uri,
                context.version,
                context.token,
                kDocUserAndTime
            ).process().build().writeTo(directory)
        }
    }
}