package com.rubik.plugins.extension.context.source

open class MavenExtension {
    var version: String? = null
    var variant: String? = null

    fun version(version: String) {
        this.version = version
    }

    fun variant(variant: String) {
        this.variant = variant
    }

    override fun toString() = "MavenExtension: version:$version variant:$variant"

}