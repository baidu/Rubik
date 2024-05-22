package com.rubik.dsl

import com.rubik.context.extra.Context
import com.rubik.dsl.context.DSLContext
import com.rubik.dsl.packing.DSLExcept
import com.rubik.dsl.packing.DSLPacking
import com.rubik.pick.ByUri
import com.rubik.pick.Excepted
import com.rubik.pick.FlavorHows
import com.rubik.pick.PickWhat
import com.rubik.context.extra.globalConfig
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

abstract class DSLRubik(
    val project: Project
) {

    fun authorityOrNameToUri(uriIfAuthorityOrName: String) =
        globalConfig.authorityOrNameToUri(uriIfAuthorityOrName)

    // components
    @RDSL
    fun router(closure: Closure<DSLContext>) {
        configTypeRouter(DSLContext(this), closure)
    }

    @RDSL
    fun router(uri: String, closure: Closure<DSLContext>) {
        DSLContext(this).apply {
            uri(uri)
            configTypeRouter(this, closure)
        }
    }

    @RDSL
    fun library(closure: Closure<DSLContext>) {
        configTypeLibrary(DSLContext(this), closure)
    }

    @RDSL
    fun library(uri: String, closure: Closure<DSLContext>) {
        DSLContext(this).apply {
            uri(uri)
            configTypeLibrary(this, closure)
        }
    }

    @RDSL
    fun component(closure: Closure<DSLContext>) {
        configTypeComponent(DSLContext(this), closure)
    }

    @RDSL
    fun component(uri: String, closure: Closure<DSLContext>) {
        DSLContext(this).apply {
            uri(uri)
            configTypeComponent(this, closure)
        }
    }

    @RDSL
    fun context(closure: Closure<DSLContext>) {
        configTypeComponent(DSLContext(this), closure)
    }

    // config type
    private fun configTypeRouter(dsl: DSLContext, closure: Closure<DSLContext>) {
        ConfigureUtil.configure(closure, dsl)
        onDefineContext(
            dsl.toContext(
                enableProvideRoute = true,
                enablePublishComponent = false
            ),
            dsl.sourcePickHow
        )
    }

    private fun configTypeLibrary(dsl: DSLContext, closure: Closure<DSLContext>) {
        ConfigureUtil.configure(closure, dsl)
        onDefineContext(
            dsl.toContext(
                enableProvideRoute = false,
                enablePublishComponent = true
            ),
            dsl.sourcePickHow
        )
    }

    private fun configTypeComponent(dsl: DSLContext, closure: Closure<DSLContext>) {
        ConfigureUtil.configure(closure, dsl)
        onDefineContext(
            dsl.toContext(
                enableProvideRoute = true,
                enablePublishComponent = true
            ),
            dsl.sourcePickHow
        )
    }

    // packing
    @RDSL
    fun pick(closure: Closure<DSLPacking>) {
        DSLPacking(this, null) { what, hows ->
            onDefinePacking(what, hows)
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    @RDSL
    fun packing(closure: Closure<DSLPacking>) {
        DSLPacking(this, null) { what, hows ->
            onDefinePacking(what, hows)
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    @RDSL
    fun except(closure: Closure<DSLPacking>) {
        DSLExcept(this, null) { excepted ->
            onDefineExcept(excepted)
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    @RDSL
    fun except(vararg uriOrAuthorityOrNames: String) {
        uriOrAuthorityOrNames.forEach { uriOrAuthorityOrName ->
            onDefineExcept(Excepted(null, ByUri.create(authorityOrNameToUri(uriOrAuthorityOrName))))
        }
    }

    abstract fun onDefineContext(context: Context, hows: FlavorHows?)

    abstract fun onDefinePacking(what: PickWhat, hows: FlavorHows?)

    abstract fun onDefineExcept(what: Excepted)

}