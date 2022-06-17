package com.rubik.route

import com.rubik.route.exception.BadResultException
import com.rubik.route.mapping.caseToTypeOfT
import com.rubik.route.mapping.toType
import com.rubik.route.mapping.typeOfT
import com.rubik.router.annotations.RInvariant
import java.lang.reflect.Type

/**
 * The Results of rubik router.
 *
 * @see Result
 *
 * @since 1.0
 */
class Results(private val receiver: ((Results) -> Unit)?) {
    private val results: MutableList<Result> = mutableListOf()

    operator fun get(index: Int): Result {
        return results.getOrNull(index) ?: throw BadResultException(index)
    }

    operator fun set(index: Int, value: Result) {
        results[index] = value
        receiver?.invoke(this)
    }

    fun set(vararg value: Result){
        results.clear()
        results.addAll(value)
        receiver?.invoke(this)
    }

    @RInvariant
    inline fun <reified T> toTypeOfT(
        index: Int
    ): T {
        return toType(index, if (this[index].isRValue) typeOfT<T>() else null).caseToTypeOfT()
    }

    fun toType(index: Int, rValueType: Type?): Any? {
        val value = this[index].value
        return if (this[index].isRValue && null != rValueType) {
            value.toType(rValueType)
        } else {
            value
        }
    }


}