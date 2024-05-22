package com.rubik.route.mapping

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.reflect.TypeToken
import com.rubik.Rubik
import com.rubik.route.exception.BadTypeException
import com.rubik.route.exception.BadValueException
import com.rubik.router.annotations.RInvariant
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

@RInvariant
inline fun <reified T> Any?.toTypeOfT(): T = toType(typeOfT<T>()).castToTypeOfT()

@RInvariant
inline fun <reified T> typeOfT() = object : TypeToken<T>() {}.type ?: throw BadTypeException()

@RInvariant
inline fun <reified T> Any?.castToTypeOfT(): T = if (this is T) this else throw BadValueException(T::class.java.name, this)

fun Any?.toType(type: Type): Any? = when {
    null == this -> null
    Rubik.Properties.autoParcel -> toParcelable(type) ?: toLiveData(type) ?: toJsonToType(type)
    else -> toLiveData(type) ?: toJsonToType(type)
}

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


fun <F, T> F.mapToType(mapping: (F) -> T): T = mapping(this)
fun <F, T> List<F>.mapToType(mapping: (F) -> T): List<T> = map(mapping)
fun <F, T, K> Map<K, F>.mapToType(mapping: (F) -> T): Map<K, T> = mapValues { (_, v) -> v.mapToType(mapping) }
fun <F, T> Set<F>.mapToType(mapping: (F) -> T): Set<T> = map(mapping).toSet()
fun <F, T> LiveData<F>.mapToType(mapping: (F) -> T): LiveData<T> = Transformations.map(this){ v -> v.mapToType(mapping) }
@RInvariant
inline fun <F, reified T> Array<F>.mapToType(mapping: (F) -> T): Array<T> = map(mapping).toTypedArray()


@Deprecated(message = "change function name ", replaceWith = ReplaceWith("replace function name", "com.rubik.route.mapping.castToTypeOfT"))
@RInvariant
inline fun <reified T> Any?.caseToTypeOfT(): T = if (this is T) this else throw BadValueException(T::class.java.name, this)
