package com.rubik.apt.namebox

import com.ktnail.x.Logger

class MappingMatrix {
    // toOriginal , toOriginal（Nullable）
    // toContext , toContext（Nullable）
    private val matrix: MutableMap<String, Pair<Pair<Boolean, Boolean>, Pair<Boolean, Boolean>>> = mutableMapOf()

    fun register(typeSign: String) {
        matrix.putIfAbsent(typeSign, (false to false) to (false to false))
    }

    fun use(typeSign: String, toOriginal: Boolean, nullable: Boolean): Boolean {
        return null != matrix[typeSign]?.also { item ->
            if (toOriginal)
                matrix[typeSign] = (true to (item.first.second || nullable)) to item.second
            else
                matrix[typeSign] = item.first to (true to (item.second.second || nullable))
        }
    }

    fun addMappingAction(
        typeSign: String,
        toContext: () -> Unit,
        toOriginal: () -> Unit,
        toContextNullable: () -> Unit,
        toOriginalNullable: () -> Unit
    ){
        matrix[typeSign]?.let { item ->
            if (item.second.first)
                toContext()
            if (item.first.first)
                toOriginal()
            if (item.second.second)
                toContextNullable()
            if (item.first.second)
                toOriginalNullable()
        }
    }

    override fun toString(): String = matrix.toList().joinToString("\n") { (key, value) ->
        "$key - ${value.first.first} ${value.first.second} ${value.second.first} ${value.second.second}"
    }

}