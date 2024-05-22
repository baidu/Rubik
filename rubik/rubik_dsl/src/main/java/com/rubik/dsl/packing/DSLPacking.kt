package com.rubik.dsl.packing

import com.rubik.context.router.RouterRegister
import com.rubik.dsl.DSLRubik
import com.rubik.dsl.RDSL
import com.rubik.dsl.context.source.DSLMavenSource
import com.rubik.pick.*
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class DSLPacking(
    private val rubik: DSLRubik,
    private val forFlavor: String?,
    private val onPick: (PickWhat, FlavorHows?) -> Unit
) {

    @RDSL
    fun flavor(name: String, closure: Closure<DSLPacking>) {
        DSLPacking(rubik, name, onPick).apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    // pickWho
    @RDSL
    fun all(closure: Closure<DSLPackingHow>) {
        val how = DSLPackingHow()
        ConfigureUtil.configure(closure, how)
        onPick.invoke(shellPackingComponentWhat(forFlavor, ByAll()), how.pickHows)
    }

    @RDSL
    fun tag(tag: String, closure: Closure<DSLPackingHow>) {
        val how = DSLPackingHow()
        ConfigureUtil.configure(closure, how)
        onPick.invoke(shellPackingComponentWhat(forFlavor, ByTag(tag)), how.pickHows)
    }

    @RDSL
    fun uri(uriOrAuthorityOrName: String, closure: Closure<DSLPackingHow>) {
        val how = DSLPackingHow()
        ConfigureUtil.configure(closure, how)
        onPick.invoke(
            shellPackingComponentWhat(
                forFlavor, ByUri.create(rubik.authorityOrNameToUri(uriOrAuthorityOrName))
            ),
            how.pickHows
        )
    }

    // pickHow
    @get:RDSL
    val all
        get() = ByAll()

    @RDSL
    fun tag(tag: String) = ByTag(tag)

    @RDSL
    fun projectMode(vararg uriOrWheres: Any) {
        uriOrWheres.forEach { uriOrWhere ->
            uriOrWhere.pickWhereAction { how ->
                how.projectMode = true
            }
        }
    }

    @RDSL
    fun mavenMode(vararg uriOrWheres: Any) {
        uriOrWheres.forEach { uriOrWhere ->
            uriOrWhere.pickWhereAction { how ->
                how.mavenMode = true
            }
        }
    }

    @RDSL
    fun noSourceMode(vararg uriOrWheres: Any) {
        uriOrWheres.forEach { uriOrWhere ->
            uriOrWhere.pickWhereAction { how ->
                how.noSourceMode = true
            }
        }
    }

    @RDSL
    fun except(vararg uriOrWheres: Any) {
        uriOrWheres.forEach { uriOrWhere ->
            uriOrWhere.pickWhereAction { how ->
                how.pickHows[null] = NoSourceMode(RouterRegister.NONE)
            }
        }
    }

    @RDSL
    fun mavenMode(uriOrWhere: Any, closure: Closure<DSLMavenSource>) {
        uriOrWhere.pickWhereAction { how ->
            how.mavenMode(closure)
        }
    }

    @RDSL
    fun noSourceMode(uriOrWhere: Any, closure: Closure<DSLPackingNoSource>) {
        uriOrWhere.pickWhereAction { how ->
            how.noSourceMode(closure)
        }
    }

    @RDSL
    fun mavenMode(uriOrWheres: List<Any>, closure: Closure<DSLMavenSource>) {
        uriOrWheres.forEach { uriOrWhere ->
            uriOrWhere.pickWhereAction { how ->
                how.mavenMode(closure)
            }
        }
    }

    @RDSL
    fun noSourceMode(uriOrWheres: List<Any>, closure: Closure<DSLPackingNoSource>) {
        uriOrWheres.forEach { uriOrWhere ->
            uriOrWhere.pickWhereAction { how ->
                how.noSourceMode(closure)
            }
        }
    }

    private fun Any.pickWhereAction(action: (DSLPackingHow) -> Unit) =
        when (this) {
            is PickWhere -> this
            is String -> ByUri.create(rubik.authorityOrNameToUri(this))
            else -> null
        }?.let { where ->
            DSLPackingHow().let { how ->
                action(how)
                onPick.invoke(shellPackingComponentWhat(forFlavor, where), how.pickHows)
            }
        }
}