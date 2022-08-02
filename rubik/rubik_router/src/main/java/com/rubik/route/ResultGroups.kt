package com.rubik.route

class ResultGroups {
    private val groups: MutableList<Results> = mutableListOf()

    fun load(results: Results) {
        groups.add(results)
    }

    fun set(index: Int, vararg results: Result) {
        groups.getOrNull(index)?.set(*results)
    }

}

