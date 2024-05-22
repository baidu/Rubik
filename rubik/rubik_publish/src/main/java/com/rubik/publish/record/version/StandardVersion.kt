package com.rubik.publish.record.version

data class StandardVersion(val numbers: List<Int>) {
    constructor(version: String) : this(version.split(".").map { str -> str.toIntOrNull() ?: 0 }.let { numbers -> numbers.ifEmpty { listOf(0) } })

    override fun toString() = numbers.joinToString(".")

    fun biggerOne(other: StandardVersion): StandardVersion {
        val shortOne: StandardVersion
        val longOne: StandardVersion
        if (numbers.size < other.numbers.size) {
            shortOne = this
            longOne = other
        } else {
            shortOne = other
            longOne = this
        }

        for (i in shortOne.numbers.indices) {
            if (shortOne.numbers[i] > longOne.numbers[i])
                return shortOne
            if (longOne.numbers[i] > shortOne.numbers[i])
                return longOne
        }

        return longOne
    }

    fun plusOne(): String {
        val last = numbers.last() + 1
        val list = numbers.subList(0, numbers.size - 1).toMutableList()
        list.add(last)
        return StandardVersion(list).toString()
    }
}