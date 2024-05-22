package com.rubik.apt.declare

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class LeastContext(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: String
) {
    companion object {
        fun createByJson(json: String): LeastContext =
            Gson().fromJson(json, object : TypeToken<LeastContext>() {}.type)
    }
}

