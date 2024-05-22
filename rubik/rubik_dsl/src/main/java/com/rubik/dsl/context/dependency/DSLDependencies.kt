package com.rubik.dsl.context.dependency

import com.ktnail.x.uri.buildUri
import com.rubik.context.Dependency
import com.rubik.context.exception.RubikDSLDefaultGroupNotSetException
import com.rubik.context.exception.RubikDSLDefaultSchemeNotSetException
import com.rubik.context.extra.globalConfig
import com.rubik.dsl.DSLRubik
import com.rubik.dsl.RDSL
import com.rubik.context.utility.toAuthority
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class DSLDependencies(
    val rubik: DSLRubik
) {
    private val dependencies: MutableList<DSLDependency> = mutableListOf()

    @set:RDSL
    var uri: String
        get() = throw RuntimeException("It is only for DSL")
        set(value) = uri(value)

    @RDSL
    fun uri(uri: String) {
        uri(uri, null)
    }

    @RDSL
    fun uri(uri: String, closure: Closure<DSLDependency>?) {
        DSLDependency(rubik.authorityOrNameToUri(uri)).apply {
            dependencies.add(this)
            ConfigureUtil.configure(closure, this)
        }
    }

    @set:RDSL
    var authority: String
        get() = throw RuntimeException("It is only for DSL")
        set(value) = authority(value)

    @RDSL
    fun authority(authority: String) {
        authority(authority, null)
    }

    @RDSL
    fun authority(authority: String, closure: Closure<DSLDependency>?) {
        DSLDependency(
            buildUri(
                globalConfig.scheme ?: throw  RubikDSLDefaultSchemeNotSetException(),
                authority
            )
        ).apply {
            dependencies.add(this)
            ConfigureUtil.configure(closure, this)
        }
    }

    @set:RDSL
    var name: String
        get() = throw RuntimeException("It is only for DSL")
        set(value) = name(value)

    @RDSL
    fun name(name: String) {
        name(name, null)
    }

    @RDSL
    fun name(name: String, closure: Closure<DSLDependency>?) {
        DSLDependency(
            buildUri(
                globalConfig.scheme ?: throw  RubikDSLDefaultSchemeNotSetException(),
                toAuthority(globalConfig.group ?: throw  RubikDSLDefaultGroupNotSetException(), name)
            )
        ).apply {
            dependencies.add(this)
            ConfigureUtil.configure(closure, this)
        }
    }

    fun toDependencies() = dependencies.map {
        Dependency(it.uri, it.forFlavor)
    }

    override fun toString() = "DSLDependencies: dependencies:$dependencies "
}