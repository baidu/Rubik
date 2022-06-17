package com.rubik.plugins.extension.global

open class GlobalMavenVersionExtension(
    private val uriOrAuthority: String,
    val maven: GlobalMavenExtension
) {
    fun publish(version: String) {
        maven.publishVersionAction(uriOrAuthority, version)
    }
}