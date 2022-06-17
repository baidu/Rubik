package com.rubik.plugins.extension.root.packing.mode

import com.rubik.plugins.extension.context.source.MavenExtension
import com.rubik.plugins.extension.context.source.ProjectExtension
import com.rubik.plugins.extension.root.model.MavenMode
import com.rubik.plugins.extension.root.model.NoSourceMode
import com.rubik.plugins.extension.root.model.PickMode
import com.rubik.plugins.extension.root.model.ProjectMode
import com.rubik.plugins.extension.root.packing.NoSourceExtension
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

class ModeExtension(
    private val onPick: (PickMode) -> Unit
) {

    fun projectMode() {
        onPick(ProjectMode())
    }

    fun mavenMode() {
        onPick(MavenMode(null))
    }

    fun mavenMode(closure: Closure<MavenExtension>) {
        onPick(MavenMode(MavenExtension().apply {
            ConfigureUtil.configure(closure, this)
        }))
    }

    fun noSourceMode() {
        onPick(NoSourceMode(null))
    }

    fun noSourceMode(closure: Closure<NoSourceExtension>) {
        onPick(NoSourceMode(NoSourceExtension().apply {
            ConfigureUtil.configure(closure, this)
        }))
    }
}