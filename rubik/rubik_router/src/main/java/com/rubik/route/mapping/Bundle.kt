/**
 * Copyright (C) Baidu Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rubik.route.mapping

import android.content.Intent
import android.os.Bundle
import com.rubik.router.annotations.RInvariant
import java.lang.reflect.Type

const val KEY_JSON_VALUE_PREFIX = "R_KEY_JSON_VALUE_"

internal fun Bundle.isJsonExtras(key: String) = !getString("${KEY_JSON_VALUE_PREFIX}${key}").isNullOrBlank()

internal fun Bundle.putJson(key: String, value: Any?) {
    putString("${KEY_JSON_VALUE_PREFIX}$key", value.toJson())
}

@RInvariant
fun Intent.query(name: String, type: Type): Any? = extras?.query(name, type)

@RInvariant
fun Bundle.query(name: String, type: Type): Any? {
    return this.run {
        if (isJsonExtras(name)) {
            (getString("${KEY_JSON_VALUE_PREFIX}${name}") ?: "").jsonToType(type)
        } else {
            get(name).caseToTypeOfT()
        }
    }
}

fun <T> Intent.valueProperty(name: String, type: Type): T? = extras?.valueProperty(name, type)

fun <T> Bundle.valueProperty(name: String, type: Type): T? {
    return this.run {
        if (isJsonExtras(name)) {
            (getString("${KEY_JSON_VALUE_PREFIX}${name}") ?: "").jsonToType<T>(type)
        } else null
    }
}