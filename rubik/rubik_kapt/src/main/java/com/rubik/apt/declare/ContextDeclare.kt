package com.rubik.apt.declare

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

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
    companion object {
        fun createByJson(json: String): ContextDeclare =
            Gson().fromJson(json, object : TypeToken<ContextDeclare>() {}.type)
    }
}

