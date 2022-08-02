package com.rubik.route

import com.rubik.route.exception.BadResultException

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

    fun value(
        index: Int
    ): Any? {
        return this[index].value
    }
}
