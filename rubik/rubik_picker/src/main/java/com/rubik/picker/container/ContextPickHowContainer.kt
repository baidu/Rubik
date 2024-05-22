package com.rubik.picker.container

import com.rubik.context.extra.Context
import com.rubik.picker.ContextPickHow

class ContextPickHowContainer {
    private val pickCases = mutableMapOf<String, ContextPickHow>()
    fun pickCase(context: Context) = pickCases.getOrPut(context.uri) { ContextPickHow(context) }
}