package com.rubik.apt.codebase.invoker

import com.blueprint.kotlin.lang.element.KbpFunctionElement
import com.blueprint.kotlin.lang.element.KbpHighOrderFunctionElement
import com.blueprint.kotlin.pool.ElementPool
import com.ktnail.x.camelToPascal
import com.ktnail.x.pascalToSnake
import com.ktnail.x.toPascal
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.TokenName
import com.rubik.apt.codebase.api.QueryCodeBase
import com.rubik.apt.codebase.api.ResultCodeBase
import javax.lang.model.element.Modifier

open class InvokeFunctionCodeBase(
    val type: InvokeElementType,
    val name: String,
    val static: Boolean,
    val queries: List<QueryCodeBase>,
    val result: ResultCodeBase?
): RToken{
    companion object{
        fun KbpFunctionElement.toInvokeFunctionCodeBase(elementPool: ElementPool) =
            jmElement?.let { functionElement ->
                InvokeFunctionCodeBase(
                    type = InvokeElementType.METHOD,
                    name = name,
                    static = functionElement.modifiers.contains(Modifier.STATIC),
                    queries = QueryCodeBase(parameters, elementPool),
                    result = ResultCodeBase(returnType)
                )
            }

        fun KbpHighOrderFunctionElement.toInvokeFunctionCodeBase() =
            jmElement?.let { functionElement ->
                InvokeFunctionCodeBase(
                    type = InvokeElementType.HIGHER_ORDER_FUNC,
                    name = name,
                    static = functionElement.modifiers.contains(Modifier.STATIC),
                    queries = QueryCodeBase(parameters, Constants.Apis.FUNCTION_ARGUMENTS),
                    result = ResultCodeBase(returnType)
                )
            }
    }

    var useName: String = name

    val parameterPathSuffix
        get() = if (queries.isNotEmpty())
            "-" + toPascal(
                "By", queries.joinToString("") { query ->
                    query.originalName.camelToPascal()
                }).pascalToSnake(separator = "-")
        else ""

    val legalName: String
        get() = Constants.Apis.toLegalParameterName(useName)

    val containRMirror
        get() = null != queries.find { it.isRMirror } || result?.isRMirror == true

    val queriesKDoc
        get() =  queries.map { queryCodeBase -> "${queryCodeBase.originalName} : ${queryCodeBase.originalType}" }

    val resultKDoc
        get() =  result?.originalType.toString()

    val originalParameters = queries.map { query ->
        query.legalName to query.originalType.toTypeName()
    }

    fun contextParameters(uri: String) = queries.map { query ->
        query.legalName to query.toContextTypeName(uri)
    }

    fun contextResult(uri: String) = result?.toContextTypeName(uri)

    override val tokenList
        get() = TokenList(
            TokenName(name),
            queries,
            result,
            static,
            key = "IVF",
            warp = false
        )
}


