package com.rubik.route.mapping

import com.google.gson.Gson
import java.lang.reflect.Type

val GSON = Gson()

internal fun Any?.toJson() = GSON.toJson(this) ?: ""

internal fun <T> String.jsonToType(type: Type): T = GSON.fromJson(this, type)
internal fun <T> Any?.toJsonToType(type: Type): T = this.toJson().jsonToType(type)