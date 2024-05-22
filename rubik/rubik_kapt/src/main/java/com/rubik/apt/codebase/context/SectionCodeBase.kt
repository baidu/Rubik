package com.rubik.apt.codebase.context

import com.rubik.apt.codebase.RouteCodeBase

class SectionCodeBase<T : RouteCodeBase> {
    val nextLevel = mutableMapOf<String, SectionCodeBase<T>>()
    val items = mutableSetOf<T>()
    private val disableItems = mutableSetOf<T>()

    fun addItem(
        item: T,
        sections: List<String>,
        enable: (T) -> Boolean = { true }
    ) {
        if (enable(item)) {
            if (sections.isNotEmpty()) {
                getNextLevel(sections.first()).addItem(
                    item,
                    sections.takeLast(sections.size - 1),
                    enable
                )
            } else {
                items.add(item)
            }
        } else {
            disableItems.add(item)
        }
    }

    private fun getNextLevel(nextSection: String) = nextLevel.getOrPut(nextSection) {
        SectionCodeBase()
    }

    fun forEachRoute(action: (T) -> Unit) {
        items.forEach (action)
        nextLevel.forEach { (_, value) ->
            value.forEachRoute(action)
        }
        disableItems.forEach(action)
    }
}