package com.rubik.apt.codebase.invoker

import com.rubik.apt.Constants
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.noSpaces
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.TypeName

data class LambdaTypeCodeBase(
    val name: String,
    val parameters: List<TypeName>,
    val returnType: TypeName,
    val nullable: Boolean = true
) {
    fun toTypeName() =
        LambdaTypeName.get(
            returnType = returnType,
            parameters = *parameters.map { parameter -> parameter }.toTypedArray()
        ).copy(nullable = nullable)

    fun toTypeCode(nameBox: FileNameBox) =
        ("(${parameters.joinToString(", ") { type -> nameBox.closeSimpleName(type) }}) -> ${nameBox.closeSimpleName(returnType)}").let { lambda->
            if(nullable) "($lambda)?" else lambda
        }.noSpaces()

    private val parametersNames :List<String>
        get() =  parameters.mapIndexed { index, _ -> "${Constants.Apis.FUNCTION_ARGUMENTS}$index" }

    val queriesCode: String
        get() = parametersNames.joinToString(", ")

    val queriesRequestsCode: String
        get() = parametersNames.joinToString(",") { Constants.Aggregate.makeResultsCode(it) }
}