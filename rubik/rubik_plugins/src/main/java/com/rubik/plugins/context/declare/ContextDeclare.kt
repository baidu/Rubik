package com.rubik.plugins.context.declare

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class ContextDeclare(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("dependencies")
    val dependencies: List<ContextDeclare>?,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: String
) {
    fun toJson(): String = Gson().toJson(this)
}

