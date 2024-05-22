package com.rubik.dsl.global

import com.rubik.dsl.RDSL
import com.rubik.global.GlobalConfig
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

open class DSLGlobalMavenConfig(
    val project: Project,
    val config: GlobalConfig
) {
    /* version、variant */
    @RDSL
    fun variant(variant: String) {
        config.defaultVariant = variant
    }

    @RDSL
    fun version(
        uriOrAuthorityOrName: String,
        version: String
    ) {
        val uri = config.authorityOrNameToUri(uriOrAuthorityOrName)
        config.addVersion(uri, version)
    }

    @RDSL
    fun version(
        uriOrAuthorityOrName: String,
        closure: Closure<DSLGlobalContextConfig>
    ) {
        val uri = config.authorityOrNameToUri(uriOrAuthorityOrName)
        val maven = ConfigureUtil.configure(closure, DSLGlobalContextConfig())
        config.addMaven(uri, maven.toMavenSource())
        maven.publishVersion?.let { publishVersion ->
            config.publishVersions[uri] = publishVersion
        }
    }

    @RDSL
    fun version(
        uriOrAuthorityOrName: String,
        version: String,
        closure: Closure<DSLGlobalContextConfig>
    ) {
        version(uriOrAuthorityOrName, version)
        version(uriOrAuthorityOrName, closure)
    }

    @RDSL
    fun tagVersion(
        tagName: String,
        version: String
    ) {
        config.addTagVersion(tagName, version)
    }

    @RDSL
    fun tagVersion(
        tagName: String,
        closure: Closure<DSLGlobalContextConfig>
    ) {
        val maven = ConfigureUtil.configure(closure, DSLGlobalContextConfig())
        config.addTagMaven(tagName, maven.toMavenSource())
        maven.publishVersion?.let { publishVersion ->
            config.tagPublishVersions[tagName] = publishVersion
        }
    }

    @RDSL
    fun tagVersion(
        tagName: String,
        version: String,
        closure: Closure<DSLGlobalContextConfig>
    ) {
        tagVersion(tagName, version)
        tagVersion(tagName, closure)
    }


}