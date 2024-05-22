package com.rubik.dsl.global

import com.rubik.dsl.RDSL
import com.rubik.global.GlobalConfig
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil


open class GlobalExtension(val project: Project) {

    val config : GlobalConfig = GlobalConfig()

    /* devMode */
    @set:RDSL
    var devMode: Boolean
        set(value) {
            devMode(value)
        }
        get() = throw RuntimeException("It is only for DSL")

    @RDSL
    fun devMode(enable: Boolean) {
        config.devEnable = enable
    }

    /* scheme */
    @set:RDSL
    var scheme: String
        set(value) {
            scheme(value)
        }
        get() = throw RuntimeException("It is only for DSL")

    @RDSL
    fun scheme(scheme: String) {
        config.scheme = scheme
    }


    /* group */
    @set:RDSL
    var group: String
        set(value) {
            group(value)
        }
        get() = throw RuntimeException("It is only for DSL")

    @RDSL
    fun group(group: String) {
        config.group = group
    }

    /* force projectMode or mavenMode */
    @set:RDSL
    var forceMavenMode: Boolean
        set(value) {
            forceMavenMode(value)
        }
        get() = throw RuntimeException("It is only for DSL")

    @set:RDSL
    var forceProjectMode: Boolean
        set(value) {
            forceProjectMode(value)
        }
        get() = throw RuntimeException("It is only for DSL")

    @RDSL
    fun forceMavenMode(enable: Boolean) {
        config.forceMavenModeEnable = enable
    }

    @RDSL
    fun forceProjectMode(enable: Boolean) {
        config.forceProjectModeEnable = enable
    }


    /* maven */
    private val maven = DSLGlobalMavenConfig(project, config)

    @RDSL
    fun maven(closure: Closure<DSLGlobalMavenConfig>) {
        ConfigureUtil.configure(closure, maven)
    }

    fun variant(variant: String) {
        this.maven.variant(variant)
    }

    @RDSL
    fun uri(
        uriOrAuthorityOrName: String,
        closure: Closure<DSLGlobalContextConfig>
    ) {
        this.maven.version(uriOrAuthorityOrName, closure)
    }

    @RDSL
    fun tag(
        tagName: String,
        closure: Closure<DSLGlobalContextConfig>
    ) {
        this.maven.tagVersion(tagName, closure)
    }

}