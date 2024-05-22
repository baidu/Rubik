package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.callback.FunctionCallbackCodeBase
import com.rubik.apt.codebase.callback.ObjectCallbackCodeBase
import com.rubik.apt.codebase.contextClassName
import com.rubik.apt.codebase.invoker.Callbackable
import com.rubik.apt.codebase.invoker.InvokeFunctionCodeBase
import com.rubik.apt.codebase.originalClassName
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.toParametersCode
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName

internal fun FunSpec.Builder.addCallbackTransformCode(
    query: QueryCodeBase,
    callback: Callbackable,
    nameBox: FileNameBox
) {
    if (callback is FunctionCallbackCodeBase) {
        callback.function.let { function ->
            val lambdaTypeCode  = "(${function.queries.joinToString(", ") {
                nameBox.closeSimpleName("${Any::class.qualifiedName}") + "?" 
            }}) -> ${
                if (callback.transformRMirrorResult && null != function.result) 
                        nameBox.closeSimpleName(function.result.originalType.name) + if (function.result.nullable) "?" else ""
                else nameBox.closeSimpleName(Unit::class.java.asClassName())
            }"
            beginControlFlow(
                "val ${Constants.Apis.toCallbackTransformerName(query.legalName)}: $lambdaTypeCode = { ${function.queries.joinToString(", ") { it.originalName }} -> ".noSpaces()
            )
            addCallbackTransformActionCode(null, callback, function, true, query.nullable, nameBox)
            endControlFlow()
        }
    } else if (callback is ObjectCallbackCodeBase) {
        addObjectCallbackTransformCode(
            "val ${Constants.Apis.toCallbackTransformerName(query.legalName)} =",
            null,
            callback,
            true,
            query.nullable,
            nameBox
        )
    }
}

fun FunSpec.Builder.addObjectCallbackTransformCode(
    receiveCode: String?,
    handleCode: String?,
    callback: ObjectCallbackCodeBase,
    toOriginal: Boolean,
    variableNullable: Boolean,
    nameBox: FileNameBox
) = apply {
    val className = if (toOriginal) callback.originalClassName else callback.contextClassName(nameBox.uri)
    beginControlFlow(
        "${if (receiveCode.isNullOrBlank()) "" else "$receiveCode "}object : ${
            nameBox.closeSimpleName(className).let { if (callback.isInterface ) it else "$it()" }
        }".noSpaces()
    )
    callback.functions.forEach { function ->
        val parameters =  if(toOriginal) function.originalParameters else function.contextParameters(nameBox.uri)
        beginControlFlow(
            "override fun ${function.name}(${
                parameters.joinToString(", ") { (name, type) ->
                    "${name}: ${nameBox.closeSimpleName(type)}" }.noSpaces()
            })".noSpaces()
        )
        addCallbackTransformActionCode(
            handleCode,
            callback,
            function,
            toOriginal,
            variableNullable,
            nameBox
        )
        endControlFlow()
    }
    endControlFlow()
}

private fun FunSpec.Builder.addCallbackTransformActionCode(
    handleCode: String?,
    callback: Callbackable,
    function: InvokeFunctionCodeBase,
    toOriginal: Boolean,
    variableNullable: Boolean,
    nameBox: FileNameBox
) = apply {
    val invokeCode =
        "${if (handleCode.isNullOrBlank()) "" else "$handleCode."}${function.legalName}${if (variableNullable) "?.invoke" else ""}(${
        function.queries.toParametersCode { type ->
            if(toOriginal)
                type.toContextCode(type.legalName, nameBox, castIfNot = callback is FunctionCallbackCodeBase)
            else
                type.toOriginalCode(type.legalName, nameBox, castIfNot = callback is FunctionCallbackCodeBase)
        }
    })".noSpaces()
    addCode(
        if (callback.transformRMirrorResult && null != function.result){
            if (toOriginal)
                function.result.toOriginalCode(invokeCode, nameBox)
            else
                function.result.toContextCode(invokeCode, nameBox)
        } else invokeCode
    )
}

