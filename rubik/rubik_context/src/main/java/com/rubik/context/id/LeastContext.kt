package com.rubik.context.id

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class LeastContext(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: String
) {
    fun toJson(): String = Gson().toJson(this)
}
