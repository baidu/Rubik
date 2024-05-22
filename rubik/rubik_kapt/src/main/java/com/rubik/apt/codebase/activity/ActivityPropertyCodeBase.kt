package com.rubik.apt.codebase.activity

import com.blueprint.kotlin.lang.type.KbpType
import com.ktnail.x.toCamel
import com.rubik.apt.Constants
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.invoker.TypeCodeBase

class ActivityPropertyCodeBase(
    private val name: String,
    originalType: KbpType,
    private val queryWithType: String? = null
) : TypeCodeBase(originalType) {

    val legalName
        get() = Constants.Apis.toLegalParameterName(name)

    val originalName
        get() = name

    fun makeAddToQueryCode(): String =
        "\"$originalName\" ${if (null != queryWithType) toCamel("with", queryWithType) else "with"} $legalName"

    override val tokenList
        get() = TokenList(name, queryWithType, originalType, key = "PRT", warp = false)

}

