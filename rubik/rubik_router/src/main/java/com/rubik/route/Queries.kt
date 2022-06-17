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
package com.rubik.route

import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import com.rubik.route.exception.BadQueryException
import com.rubik.route.mapping.caseToTypeOfT
import com.rubik.route.mapping.putJson
import com.rubik.route.mapping.toType
import com.rubik.route.mapping.typeOfT
import com.rubik.router.annotations.RInvariant
import java.io.Serializable
import java.lang.reflect.Type

/**
 * The Queries of rubik router.
 *
 * @see Query
 *
 * @since 1.0
 */
open class Queries {
    private val valuesList: MutableList<Query> = mutableListOf()

    fun add(query: Query) {
        valuesList.add(query)
    }

    fun addAll(values: List<Query>) {
        valuesList.addAll(values)
    }

    operator fun get(index: Int, name: String? = null): Query {
        return name?.let {
            valuesList.findLast { query -> query.name == name }
        } ?: valuesList.getOrNull(index) ?: throw BadQueryException(index, name)
    }

    @RInvariant
    inline fun <reified T> toTypeOfT(
        index: Int,
        name: String? = null,
        isRValue: Boolean = false
    ): T {
        return toType(index, name, if (isRValue) typeOfT<T>() else null).caseToTypeOfT()
    }

    @RInvariant
    fun toType(
        index: Int,
        name: String? = null,
        rValueType: Type? = null
    ): Any? {
        val value = this[index, name].value
        return if (null != rValueType) { // is RValue
            value.toType(rValueType)
        } else {
            value
        }
    }

    fun toBundle() = Bundle(valuesList.size).apply {
        valuesList.forEachIndexed { _, query ->
            val key = query.name
            val value = query.value
            when (query.type) {
                QueryType.VALUE -> {
                    putJson(key, value)
                }
                QueryType.PARCELABLE -> {
                    if (!putParcelableQuery(key, value)) {
                        putJson(key, value)
                    }
                }
                QueryType.SERIALIZABLE -> {
                    if (!putSerializableQuery(key, value)) {
                        putJson(key, value)
                    }
                }
                else ->{
                    when (value) {
                        null -> putString(key, null) // Any nullable type will suffice.

                        // Scalars
                        is Boolean -> putBoolean(key, value)
                        is Byte -> putByte(key, value)
                        is Char -> putChar(key, value)
                        is Double -> putDouble(key, value)
                        is Float -> putFloat(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is Short -> putShort(key, value)

                        // References
                        is Bundle -> putBundle(key, value)
                        is CharSequence -> putCharSequence(key, value)

                        // Scalar arrays
                        is BooleanArray -> putBooleanArray(key, value)
                        is ByteArray -> putByteArray(key, value)
                        is CharArray -> putCharArray(key, value)
                        is DoubleArray -> putDoubleArray(key, value)
                        is FloatArray -> putFloatArray(key, value)
                        is IntArray -> putIntArray(key, value)
                        is LongArray -> putLongArray(key, value)
                        is ShortArray -> putShortArray(key, value)

                        // Reference arrays
                        is Array<*> -> {
                            if (!putReferenceArraysQuery(key, value)) {
                                putJson(key, value)
                            }
                        }

                        else -> {
                            if (Build.VERSION.SDK_INT >= 18 && value is Binder) {
                                putBinder(key, value)
                            } else if (Build.VERSION.SDK_INT >= 21 && value is Size) {
                                putSize(key, value)
                            } else if (Build.VERSION.SDK_INT >= 21 && value is SizeF) {
                                putSizeF(key, value)
                            } else {
                                putJson(key, value)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Bundle.putParcelableQuery(key: String, value: Any?): Boolean {
        when (value) {
            is Parcelable -> {
                putParcelable(key, value)
                return true
            }
            is Array<*> -> {
                value::class.java.componentType?.let { componentType ->
                    if (Parcelable::class.java.isAssignableFrom(componentType)) {
                        @Suppress("UNCHECKED_CAST")  // Checked by reflection.
                        putParcelableArray(key, value as? Array<Parcelable>)
                        return true
                    }
                }
            }
            is ArrayList<*> -> {
                @Suppress("UNCHECKED_CAST")  // Checked by catch.
                putParcelableArrayList(key, value as? ArrayList<Parcelable>)
                return true
            }
        }
        return false
    }

    private fun Bundle.putSerializableQuery(key: String, value: Any?) :Boolean{
        return if (value is Serializable) { // all arrays are serializable.
            putSerializable(key, value)
            true
        } else {
            false
        }
    }

    private fun Bundle.putReferenceArraysQuery(key: String, value: Array<*>) :Boolean{
        value::class.java.componentType?.let {componentType->
            @Suppress("UNCHECKED_CAST") // Checked by reflection.
            when {
                String::class.java.isAssignableFrom(componentType) -> {
                    putStringArray(key, value as? Array<String>)
                    return true
                }
                CharSequence::class.java.isAssignableFrom(componentType) -> {
                    putCharSequenceArray(key, value as? Array<CharSequence>)
                    return true
                }
                else -> {}
            }
        }
        return false
    }
}

