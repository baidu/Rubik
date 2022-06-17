package com.rubik.plugins.extension.context.dependency

import com.rubik.plugins.extension.RubikExtension
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class DependenciesExtension(val rubik: RubikExtension) {
    val dependencies: MutableList<DependencyExtension> = mutableListOf()

    fun uri(uri: String) {
        DependencyExtension(rubik).apply {
            this.setUri(uri)
            dependencies.add(this)
        }
    }

    fun authority(authority: String) {
        DependencyExtension(rubik).apply {
            this.setAuthority(authority)
            dependencies.add(this)
        }
    }

    fun uri(uri: String, closure: Closure<DependencyExtension>) {
        DependencyExtension(rubik).apply {
            this.setUri(uri)
            dependencies.add(this)
            ConfigureUtil.configure(closure, this)
        }
    }

    fun authority(authority: String, closure: Closure<DependencyExtension>) {
        DependencyExtension(rubik).apply {
            this.setAuthority(authority)
            dependencies.add(this)
            ConfigureUtil.configure(closure, this)
        }
    }

    override fun toString() = "DependenciesExtension: dependencies:$dependencies "
}