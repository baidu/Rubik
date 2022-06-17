package com.rubik.plugins.extension.root.packing.who

import com.rubik.plugins.extension.context.source.MavenExtension
import com.rubik.plugins.extension.root.model.All
import com.rubik.plugins.extension.root.model.ByTag
import com.rubik.plugins.extension.root.model.ByUri
import com.rubik.plugins.extension.root.model.PickWho
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class WhoMavenExtension(
    private val onPick: (PickWho, MavenExtension?) -> Unit
) {
    fun all() {
        onPick(All(), null)
    }

    fun tag(tag: String) {
        onPick(ByTag(tag), null)
    }

    fun uri(uriOrAuthority: String) {
        onPick(ByUri.create(uriOrAuthority), null)
    }

    fun all(closure: Closure<MavenExtension>) {
        onPick(All(), MavenExtension().apply {
            ConfigureUtil.configure(closure, this)
        })
    }

    fun tag(tag: String, closure: Closure<MavenExtension>) {
        onPick(ByTag(tag), MavenExtension().apply {
            ConfigureUtil.configure(closure, this)
        })
    }

    fun uri(uriOrAuthority: String, closure: Closure<MavenExtension>) {
        onPick(ByUri.create(uriOrAuthority), MavenExtension().apply {
            ConfigureUtil.configure(closure, this)
        })
    }
}