package com.rubik.plugins.extension.root.packing.mode

import com.rubik.plugins.extension.root.model.*
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class PickExtension(
    private val forFlavor: String?,
    private val onPick: (Picked) -> Unit
) {

    fun flavor(name: String, closure: Closure<PickExtension>) {
        PickExtension(name, onPick).apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    fun all() {
        onPick.invoke(Picked(forFlavor, All(), MavenMode(null)))
    }

    fun tag(tag: String) {
        onPick.invoke(Picked(forFlavor, ByTag(tag), MavenMode(null)))
    }

    fun uri(uriOrAuthority: String) {
        onPick.invoke(Picked(forFlavor, ByUri.create(uriOrAuthority), MavenMode(null)))
    }

    fun all(closure: Closure<ModeExtension>) {
        ModeExtension { pickMode ->
            onPick.invoke(Picked(forFlavor, All(), pickMode))
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    fun tag(tag: String, closure: Closure<ModeExtension>) {
        ModeExtension { pickMode ->
            onPick.invoke(Picked(forFlavor, ByTag(tag), pickMode))
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    fun uri(uriOrAuthority: String, closure: Closure<ModeExtension>) {
        ModeExtension { pickMode ->
            onPick.invoke(Picked(forFlavor, ByUri.create(uriOrAuthority), pickMode))
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }
}