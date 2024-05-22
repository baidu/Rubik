package com.rubik.dsl.packing

import com.rubik.context.router.RouterRegister
import com.rubik.dsl.RDSL
import com.rubik.dsl.context.source.DSLMavenSource
import com.rubik.pick.MavenMode
import com.rubik.pick.NoSourceMode
import com.rubik.pick.PickHow
import com.rubik.pick.ProjectMode
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class DSLPackingHow {
    var pickHows = mutableMapOf<String?, PickHow>()

    @set:RDSL
    var projectMode = false
        get() = throw java.lang.RuntimeException("It is only for DSL")
        set(value) {
            projectMode(value)
            field = value
        }

    @RDSL
    fun projectMode() {
        pickHows[null] = ProjectMode()
    }

    @RDSL
    fun projectMode(flag: Boolean) {
        if (flag) projectMode() else mavenMode()
    }

    @set:RDSL
    var mavenMode = false
        get() = throw java.lang.RuntimeException("It is only for DSL")
        set(value) {
            mavenMode(value)
            field = value
        }

    @RDSL
    fun mavenMode() {
        pickHows[null] = MavenMode()
    }

    @RDSL
    fun mavenMode(flag: Boolean) {
        if (flag) mavenMode() else projectMode()
    }

    @RDSL
    fun mavenMode(closure: Closure<DSLMavenSource>) {
        val maven = ConfigureUtil.configure(closure, DSLMavenSource())
        pickHows[null] = MavenMode(maven.version, maven.variant)
        maven.flavors.forEach { (flavor, dsl) ->
            pickHows[flavor]  = MavenMode(dsl.version, dsl.variant)
        }
    }

    @set:RDSL
    var noSourceMode = false
        get() = throw java.lang.RuntimeException("It is only for DSL")
        set(value) {
            noSourceMode(value)
            field = value
        }

    @RDSL
    fun noSourceMode() {
        pickHows[null] = NoSourceMode(RouterRegister.NEW_INSTANCE)
    }

    @RDSL
    fun noSourceMode(flag: Boolean) {
        if (flag) noSourceMode() else projectMode()
    }

    @RDSL
    fun noSourceMode(closure: Closure<DSLPackingNoSource>) {
        pickHows[null] = NoSourceMode(DSLPackingNoSource().apply {
            ConfigureUtil.configure(closure, this)
        }.let { dsl ->
            if (!dsl.enableAggregate) RouterRegister.NONE
            else if (dsl.enableReflect) RouterRegister.REFLECT_INSTANCE
            else RouterRegister.NEW_INSTANCE
        }
        )
    }
}