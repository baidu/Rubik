package com.rubik.plugins.extension.global

import com.ktnail.x.uri.buildUri
import com.rubik.plugins.context.model.Lib.Companion.versionToDevVersion
import com.rubik.plugins.basic.utility.toUriIfAuthority
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

open class GlobalExtension(val project: Project) {
    var scheme: String? = null
        set(value) {
            field = value
            schemeChangedListeners.forEach { action ->
                action(value)
            }
        }

    var mavenVariant: String? = null

    var devEnable = false

    private val mavenVersions = mutableMapOf<String, String>()
    private val mavenPublishVersions = mutableMapOf<String, String>()

    fun scheme(scheme: String) {
        this.scheme = scheme
    }

    fun maven(closure: Closure<GlobalMavenExtension>) {
        ConfigureUtil.configure(closure, GlobalMavenExtension(
            { variant ->
                this.mavenVariant = variant
            }, { uriOrAuthority, version ->
                mavenVersions[uriOrAuthority.toUriIfAuthority(project)] = version
            }, { uriOrAuthority, publishVersion ->
                mavenPublishVersions[uriOrAuthority.toUriIfAuthority(project)] = publishVersion
            }
        ))
    }

    fun devMode(enable: Boolean) {
        devEnable = enable
    }

    private fun uriOrAuthorityToUri(uriOrAuthority: String): String {
        val scheme = this.scheme
        return if (!uriOrAuthority.contains("://") && null != scheme)
            buildUri(scheme, uriOrAuthority)
        else
            uriOrAuthority
    }

    fun getMavenVersion(uri: String, default: String?, dev: Boolean = devEnable): String? {
        val version = default ?: getMavenFormalVersion(uri)
        return if (dev) version?.versionToDevVersion() else version
    }

    private fun getMavenFormalVersion(uri: String): String? = mavenVersions[uri]

    fun getMavenPublishVersion(uri: String): String? = mavenPublishVersions[uri]

    private val schemeChangedListeners = mutableListOf<(String?) -> Unit>()

    fun listenSchemeChanged(action: (String?) -> Unit) {
        action(scheme)
        schemeChangedListeners.add {
            action(scheme)
        }
    }
}