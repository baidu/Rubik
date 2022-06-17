package com.rubik.plugins.extension.root.packing.who

import com.rubik.plugins.extension.root.model.All
import com.rubik.plugins.extension.root.model.ByTag
import com.rubik.plugins.extension.root.model.ByUri
import com.rubik.plugins.extension.root.model.PickWho

open class WhoExtension(
    private val onPick: (PickWho) -> Unit
) {
    fun all() {
        onPick(All())
    }

    fun tag(tag: String) {
        onPick(ByTag(tag))
    }

    fun uri(uriOrAuthority: String) {
        onPick(ByUri.create(uriOrAuthority))
    }
}