package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.ClassMirrorable
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.contextClassName
import com.rubik.apt.codebase.invoker.TypeCodeBase
import com.rubik.apt.codebase.originalClassName
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.addRGeneratedAnnotation
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.toParametersCode
import com.squareup.kotlinpoet.*

fun TypeSpec.Builder.addMappings(
    context: ContextCodeBase,
    nameBox: FileNameBox
) = apply {
    if (context.objects.isNotEmpty() || context.valuesCreateByConstructor.isNotEmpty())
        addType(
            TypeSpec.objectBuilder(
                Constants.Object.OBJECT_MAPPINGS_NAME
            ).addObjectMappings(
                context, nameBox
            ).addCallbackMappings(
                context, nameBox
            ).addValueMapping(
                context, nameBox
            ).addRGeneratedAnnotation(
                "aggregate_mappings", context.version
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
            ).build()
        )
}

private fun TypeSpec.Builder.addObjectMappings(
    context: ContextCodeBase,
    nameBox: FileNameBox
) = apply {
    context.objects.forEach {(_, objekt) ->
        addMappings(
            context.aggregateName,
            objekt,
            nameBox,
            addToContextBody = { context, _ ->
                returnMappingBodyCodeOneLine("${nameBox.closeSimpleName(context)}.create(this)")
            }, addToOriginalBody = { _, _ ->
                returnMappingBodyCodeOneLine("this.${TypeCodeBase.castToTypeCode(Constants.Object.SUB_OBJECT_ORIGINAL_FILED_NAME)}".noSpaces())
            })
    }
}

private fun TypeSpec.Builder.addCallbackMappings(
    context: ContextCodeBase,
    nameBox: FileNameBox
) = apply {
    context.callbacks.forEach { callback ->
        addMappings(
            context.aggregateName,
            callback,
            nameBox,
            addToContextBody = { _, _ ->
                this.returnMappingBody {
                    addStatement("val mappingTarget = this".noSpaces())
                    addObjectCallbackTransformCode(
                        null,
                        "mappingTarget",
                        callback,
                        toOriginal = false,
                        variableNullable = false,
                        nameBox = nameBox
                    )
                }
            }, addToOriginalBody = { _, _ ->
                this.returnMappingBody {
                    addStatement("val mappingTarget = this".noSpaces())
                    addObjectCallbackTransformCode(
                        null,
                        "mappingTarget",
                        callback,
                        toOriginal = true,
                        variableNullable = false,
                        nameBox = nameBox
                    )
                }
            })
    }
}

private fun TypeSpec.Builder.addValueMapping(
    context: ContextCodeBase,
    nameBox: FileNameBox
) = apply {
    context.valuesCreateByConstructor.forEach { value ->
        val toContextParameterCode =  value.fields.toParametersCode { filed -> filed.toContextCode(filed.name, nameBox) }
        val toOriginalParameterCode =   value.fields.toParametersCode { filed -> filed.toOriginalCode(filed.name, nameBox) }
        addMappings(
            context.aggregateName,
            value,
            nameBox,
            addToContextBody = { context, _ ->
                returnMappingBodyCodeOneLine("${nameBox.closeSimpleName(context)}(${toContextParameterCode})")
            }, addToOriginalBody = { _, original ->
                returnMappingBodyCodeOneLine("${nameBox.closeSimpleName(original)}(${toOriginalParameterCode})")
            })
    }
}

private fun TypeSpec.Builder.addMappings(
    aggregateName: String,
    mirror: ClassMirrorable,
    nameBox: FileNameBox,
    addToContextBody: FunSpec.Builder.(TypeName, TypeName) -> FunSpec.Builder,
    addToOriginalBody: FunSpec.Builder.(TypeName, TypeName) -> FunSpec.Builder
) {
    val contextClassName = mirror.contextClassName(nameBox.uri)
    val originalClassName = mirror.originalClassName

    val contextMappingName = Constants.Object.makeToTypeMappingFunctionName(nameBox.closeSimpleName(contextClassName))
    val originalMappingName = Constants.Object.makeToTypeMappingFunctionName(nameBox.closeSimpleName(originalClassName))

    val nullableContextMappingName = Constants.Object.makeToTypeMappingFunctionName(nameBox.closeSimpleName(contextClassName), true)
    val nullableOriginalMappingName = Constants.Object.makeToTypeMappingFunctionName(nameBox.closeSimpleName(originalClassName), true)

    val kDoc =  """ 
                | Mapping between :
                | ${originalClassName.canonicalName} < --- > $contextClassName 
            """.trimMargin().noSpaces()

    var kDocAdded = false

    nameBox.needAddMapping(mirror.originalType, toContext = {
        addProperty(
            PropertySpec.builder(
                contextMappingName,
                LambdaTypeName.get(
                    receiver = originalClassName,
                    returnType = contextClassName
                )
            ).mutable(false).getter(
                FunSpec.getterBuilder().addToContextBody(contextClassName, originalClassName).build()
            ).apply {
                if (!kDocAdded) addKdoc(kDoc)
                kDocAdded = true
            }.build()
        )

        nameBox.import(
            Constants.Aggregate.Declare.makeAggregatePackageName(nameBox.uri),
            listOf( aggregateName,Constants.Object.OBJECT_MAPPINGS_NAME, contextMappingName).joinToString(".")
        )
    }, toOriginal = {
        addProperty(
            PropertySpec.builder(
                originalMappingName,
                LambdaTypeName.get(
                    receiver = contextClassName,
                    returnType = originalClassName
                )
            ).mutable(false).getter(
                FunSpec.getterBuilder().addToOriginalBody(contextClassName, originalClassName).build()
            ).apply {
                if (!kDocAdded) addKdoc(kDoc)
                kDocAdded = true
            }.build()
        )

        nameBox.import(
            Constants.Aggregate.Declare.makeAggregatePackageName(nameBox.uri),
            listOf( aggregateName,Constants.Object.OBJECT_MAPPINGS_NAME, originalMappingName).joinToString(".")
        )
    }, toContextNullable = {
        addProperty(
            PropertySpec.builder(
                nullableContextMappingName,
                LambdaTypeName.get(
                    receiver = originalClassName.copy(nullable = true),
                    returnType = contextClassName.copy(nullable = true)
                )
            ).mutable(false).getter(
                FunSpec.getterBuilder().returnMappingBodyCodeOneLine("this?.$contextMappingName()").build()
            ).build()
        )

        nameBox.import(
            Constants.Aggregate.Declare.makeAggregatePackageName(nameBox.uri),
            listOf( aggregateName,Constants.Object.OBJECT_MAPPINGS_NAME, nullableContextMappingName).joinToString(".")
        )
    }, toOriginalNullable = {
        addProperty(
            PropertySpec.builder(
                nullableOriginalMappingName,
                LambdaTypeName.get(
                    receiver = contextClassName.copy(nullable = true),
                    returnType = originalClassName.copy(nullable = true)
                )
            ).mutable(false).getter(
                FunSpec.getterBuilder().returnMappingBodyCodeOneLine("this?.$originalMappingName()")
                    .build()
            ).build()
        )

        nameBox.import(
            Constants.Aggregate.Declare.makeAggregatePackageName(nameBox.uri),
            listOf(aggregateName, Constants.Object.OBJECT_MAPPINGS_NAME, nullableOriginalMappingName).joinToString(".")
        )
    })
}

private fun FunSpec.Builder.returnMappingBodyCodeOneLine(mappingCode: String) =apply {
    addStatement("return ${"{ $mappingCode }".noSpaces()}")
}

private fun FunSpec.Builder.returnMappingBody(mappingCode: FunSpec.Builder.() -> FunSpec.Builder) =
    apply {
        beginControlFlow("return ")
        mappingCode()
        endControlFlow()
    }


