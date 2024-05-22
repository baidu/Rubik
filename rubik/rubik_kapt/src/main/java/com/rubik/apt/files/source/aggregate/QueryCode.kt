package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase
import com.rubik.apt.codebase.invoker.TypeCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.FunSpec

private fun queryVariableCode(
    originalInvoker: InvokeOriginalCodeBase,
    variableName:String,
    originalName: String,
    index: Int
) = "val $variableName = ${
    Constants.Aggregate.makeGetQueryCode(
        if (originalInvoker.type == InvokeElementType.HIGHER_ORDER_FUNC) "\"${originalName}\"" else "null",
        index
    )
}".noSpaces()

internal fun FunSpec.Builder.addQueryVariable(
    originalInvoker: InvokeOriginalCodeBase,
    query: QueryCodeBase,
    index: Int
) {
    addStatement(queryVariableCode(originalInvoker, query.legalName, query.originalName, index))
}

internal fun FunSpec.Builder.addQueryVariableCaseToContextType(
    originalInvoker: InvokeOriginalCodeBase,
    query: QueryCodeBase,
    index: Int,
    nameBox: FileNameBox
) {
    addStatement(
        queryVariableCode(originalInvoker, query.legalName, query.originalName, index) +
                TypeCodeBase.castToTypeCode("" , toType = nameBox.closeSimpleName(query.toContextTypeName(nameBox.uri))
    ))
}