package com.rubik.dsl.context.source

import com.rubik.context.MavenSource
import com.rubik.dsl.RDSL
import com.rubik.dsl.packing.DSLPackingHow
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class DSLMavenSource : DSLPackingHow() {

    @set:RDSL
    var version: String? = null

    @set:RDSL
    var variant: String? = null

    val flavors = mutableMapOf<String, DSLMavenSource>()

    @RDSL
    fun version(version: String) {
        this.version = version
    }

    @RDSL
    fun variant(variant: String) {
        this.variant = variant
    }

    @RDSL
    fun forFlavor(flavorName: String, closure: Closure<DSLMavenSource>) {
        flavors[flavorName] = ConfigureUtil.configure(closure, DSLMavenSource())
    }

    fun toMavenSource(): MavenSource =
        MavenSource(version, variant, flavors.mapValues { (_, source) ->
            MavenSource(source.version, source.variant, null)
        })

    override fun toString() = "MavenExtension: version:$version variant:$variant"

}