package com.rubik.global

import com.rubik.context.MavenSource
import com.rubik.context.mergeOther
import com.rubik.context.utility.toUriIfAuthorityOrName

class GlobalConfig{
    var devEnable = false

    var scheme: String? = null
        set(value) {
            field = value
            schemeChangedListeners.forEach { action ->
                action(value)
            }
        }

    private val schemeChangedListeners = mutableListOf<(String?) -> Unit>()

    fun listenSchemeChanged(action: (String?) -> Unit) {
        action(scheme)
        schemeChangedListeners.add {
            action(scheme)
        }
    }

    var group: String? = null

    var forceMavenModeEnable = false
        set(value) {
            field = value
            if (value) {
                forceProjectModeEnable = false
            }
        }

    var forceProjectModeEnable = false
        set(value) {
            field = value
            if (value) {
                forceMavenModeEnable = false
            }
        }

    var defaultVariant: String? = null

    val uris = mutableMapOf<String, MavenSource>()
    val publishVersions = mutableMapOf<String, String>()

    val tags = mutableMapOf<String, MavenSource>()
    val tagPublishVersions = mutableMapOf<String, String>()

    fun publishVersion(uri: String): String? = publishVersions[uri]

    fun publishTagVersion(tagName: String): String? = tagPublishVersions[tagName]

    fun addVersion(uri: String, version: String) {
        uris[uri] = uris[uri].mergeOther(MavenSource(version, null, null))
    }

    fun addMaven(uri: String, maven: MavenSource) {
        uris[uri] = uris[uri].mergeOther(maven)
    }

    fun addTagVersion(tagName: String, version: String) {
        tags[tagName] = tags[tagName].mergeOther(MavenSource(version, null, null))
    }

    fun addTagMaven(tagName: String, maven: MavenSource) {
        tags[tagName] = tags[tagName].mergeOther(maven)
    }

    fun authorityOrNameToUri(uriIfAuthorityOrName: String) =
        uriIfAuthorityOrName.toUriIfAuthorityOrName(scheme, group)

}