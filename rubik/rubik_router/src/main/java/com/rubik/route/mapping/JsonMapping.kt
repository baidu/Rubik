package com.rubik.route.mapping

import com.google.gson.Gson
import java.lang.reflect.Type

internal fun Any?.toJson() = Gson().toJson(this) ?: ""

internal fun <T> String.jsonToType(type: Type): T = Gson().fromJson(this, type)
internal fun <T> Any?.toJsonToType(type: Type): T = this.toJson().jsonToType(type)