package com.rubik.route.mapping

import com.google.gson.reflect.TypeToken
import com.rubik.route.exception.BadTypeException
import com.rubik.route.exception.BadValueException
import com.rubik.router.annotations.RInvariant
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

@RInvariant
inline fun <reified T> Any?.toTypeOfT(): T = toType(typeOfT<T>()).caseToTypeOfT()

@RInvariant
inline fun <reified T> typeOfT() = object : TypeToken<T>() {}.type ?: throw BadTypeException()

@RInvariant
inline fun <reified T> Any?.caseToTypeOfT(): T = if (this is T) this else throw BadValueException(T::class.java.name, this)

fun Any?.toType(type: Type): Any? = if (null == this)
    null
else
    toParcelable(type) ?: toLiveData(type) ?: toJsonToType(type)


internal val Type.typeArguments: List<Type>
    get() = mutableListOf<Type>().also { result ->
        if (this is ParameterizedType)
            actualTypeArguments.mapNotNull { type ->
                (if (type is WildcardType) type.lowerBounds.getOrNull(0) ?: type.upperBounds.getOrNull(0) else type)?.let { wildcardType ->
                    result.add(wildcardType)
                }
            }
    }

internal val Type.firstTypeArgument: Type?
    get() = typeArguments.getOrNull(0)
