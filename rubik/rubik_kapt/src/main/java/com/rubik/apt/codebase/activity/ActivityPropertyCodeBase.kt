package com.rubik.apt.codebase.activity

import com.blueprint.kotlin.lang.type.KbpType
import com.ktnail.x.toCamel
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.TypeCodeBase

class ActivityPropertyCodeBase(
    private val name: String,
    type: KbpType,
    private val queryWithType: String? = null
    ) : TypeCodeBase(type) {

    val legalName
        get() = Constants.Apis.toLegalParameterName(name)

    val originalName
        get() = name

    fun makeAddToQueryCode(): String =
        "\"$originalName\" ${if (null != queryWithType) toCamel("with", queryWithType) else "with"} $legalName"
}

