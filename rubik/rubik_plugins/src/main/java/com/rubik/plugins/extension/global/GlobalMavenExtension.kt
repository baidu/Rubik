package com.rubik.plugins.extension.global

import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class GlobalMavenExtension(
    val variantAction: (String) -> Unit,
    val versionAction: (String, String) -> Unit,
    val publishVersionAction: (String, String) -> Unit
) {
    fun variant(variant: String) {
        variantAction(variant)
    }

    fun version(
        uriOrAuthority: String,
        version: String
    ) {
        versionAction(uriOrAuthority, version)
    }

    fun version(
        uriOrAuthority: String,
        version: String,
        closure: Closure<GlobalMavenVersionExtension>
    ) {
        version(uriOrAuthority, version)
        ConfigureUtil.configure(closure, GlobalMavenVersionExtension(uriOrAuthority, this))
    }

}