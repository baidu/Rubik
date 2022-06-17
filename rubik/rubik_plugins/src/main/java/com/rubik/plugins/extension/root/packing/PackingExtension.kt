package com.rubik.plugins.extension.root.packing

import com.rubik.plugins.extension.root.model.*
import com.rubik.plugins.extension.root.packing.mode.ModeExtension
import com.rubik.plugins.extension.root.packing.who.WhoExtension
import com.rubik.plugins.extension.root.packing.who.WhoMavenExtension
import com.rubik.plugins.extension.root.packing.who.WhoNoSourceExtension
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class PackingExtension(
    private val forFlavor: String?,
    private val onPick: (Picked) -> Unit
) {

    fun flavor(name: String, closure: Closure<PackingExtension>) {
        PackingExtension(name, onPick).apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    // pickWho
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

    // pickMode
    fun projectMode(closure: Closure<WhoExtension>) {
        WhoExtension { pickWho ->
            onPick(Picked(forFlavor, pickWho, ProjectMode()))
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    fun mavenMode(closure: Closure<WhoMavenExtension>) {
        WhoMavenExtension { pickWho, maven ->
            onPick(Picked(forFlavor, pickWho, MavenMode(maven)))
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }


    fun noSourceMode(closure: Closure<WhoNoSourceExtension>) {
        WhoNoSourceExtension { pickWho, noSource ->
            onPick(Picked(forFlavor, pickWho, NoSourceMode(noSource)))
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }
}