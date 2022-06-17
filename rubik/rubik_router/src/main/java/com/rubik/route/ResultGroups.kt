package com.rubik.route

typealias ResultGroups = List<Results>

fun ResultGroups.set(index: Int, vararg results: Result) {
    getOrNull(index)?.set(*results)
}