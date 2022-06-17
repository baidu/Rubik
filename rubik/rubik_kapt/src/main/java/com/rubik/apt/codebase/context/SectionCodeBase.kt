package com.rubik.apt.codebase.context

import com.rubik.apt.codebase.api.RouteCodeBase

class SectionCodeBase {
    val nextLevel = mutableMapOf<String, SectionCodeBase>()
    val items = mutableSetOf<RouteCodeBase>()

    fun addItem(item: RouteCodeBase, sections: List<String>) {
        if (sections.isNotEmpty()) {
            getNextLevel(sections.first()).addItem(item, sections.takeLast(sections.size - 1))
        } else {
            items.add(item)
        }
    }

    private fun getNextLevel(nextSection: String) = nextLevel.getOrPut(nextSection) {
        SectionCodeBase()
    }

}