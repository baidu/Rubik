package com.rubik.picker


import com.rubik.context.extra.Context
import com.rubik.pick.PickHow
import com.rubik.pick.PickWhat
import org.gradle.api.Project

interface Picker {
    val what: PickWhat
    val how: PickHow?
    val context: Context

    fun pick(forProject: Project, forFlavor: String?)
}

