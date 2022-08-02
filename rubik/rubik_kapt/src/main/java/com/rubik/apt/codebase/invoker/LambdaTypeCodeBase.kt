package com.rubik.apt.codebase.invoker

import com.rubik.apt.Constants
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.TypeName

data class LambdaTypeCodeBase(
    val name: String,
    val parameters: List<TypeName>,
    val returnType: TypeName,
    val nullable: Boolean = true
) {
    fun toTypeName() = LambdaTypeName.get(
        returnType = returnType,
        parameters = *parameters.toTypedArray()
    ).copy(nullable = nullable)

    private val parametersNames :List<String>
        get() =  parameters.mapIndexed { index, _ -> "arg$index" }

    val queriesCode: String
        get() = parametersNames.joinToString(",")

    val queriesRequestsCode: String
        get() = parametersNames.joinToString(",") { Constants.Aggregate.makeResultsCode(it) }
}