package com.rubik.dsl.context

import com.rubik.context.extra.Context
import com.rubik.dsl.DSLRubik
import com.rubik.dsl.RDSL
import com.rubik.dsl.context.dependency.DSLDependencies
import com.rubik.dsl.context.source.DSLSource
import com.rubik.dsl.exception.RubikDSLComponentUriException
import com.rubik.pick.FlavorHows
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

/**
 *  Rubik context extension of gradle plugins.
 *
 *  @since 1.3
 */
open class DSLContext(
   val rubik: DSLRubik
) {

    private var _uri: String? = null

    /**
     *  When defining a context, call this method once and only once.
     */
    @RDSL
    fun uri(uri: String) {
        if (null != _uri) throw RubikDSLComponentUriException(uri)
        _uri = rubik.authorityOrNameToUri(uri)
    }

    @RDSL
    fun authority(authority: String) {
        uri(authority)
    }

    @RDSL
    fun name(name: String) {
        uri(name)
    }

    // touching
    private val _touching = DSLDependencies(rubik)

    @RDSL
    fun dependencies(closure: Closure<DSLDependencies>) {
        ConfigureUtil.configure(closure, _touching)
    }

    @RDSL
    fun dependencies(vararg uriOrAuthorityOrNames: String) {
        uriOrAuthorityOrNames.forEach { uriOrAuthorityOrName ->
            _touching.uri(uriOrAuthorityOrName)
        }
    }

    @RDSL
    fun touching(closure: Closure<DSLDependencies>) {
        ConfigureUtil.configure(closure, _touching)
    }

    @RDSL
    fun touching(vararg uriOrAuthorityOrNames: String) {
        uriOrAuthorityOrNames.forEach { uriOrAuthorityOrName ->
            _touching.uri(uriOrAuthorityOrName)
        }
    }

    // packing
    private val _packing  = DSLDependencies(rubik)

    @RDSL
    fun packing(closure: Closure<DSLDependencies>) {
        ConfigureUtil.configure(closure, _packing)
    }

    @RDSL
    fun packing(vararg uriOrAuthorityOrNames: String) {
        uriOrAuthorityOrNames.forEach { uriOrAuthorityOrName ->
            _packing.uri(uriOrAuthorityOrName)
        }
    }

    // source
    private val _source = DSLSource()

    @RDSL
    fun source(closure: Closure<DSLSource>) {
        ConfigureUtil.configure(closure, _source)
    }

    // tag
   private val _tags = mutableMapOf<String, DSLSource?>()

    @RDSL
    fun tag(name: String, closure: Closure<DSLSource>) {
        DSLSource().apply {
            ConfigureUtil.configure(closure, this)
            _tags[name] = this
        }
    }

    @RDSL
    fun tag(vararg names: String) {
        names.forEach { tagName ->
            _tags[tagName] = null
        }
    }

    fun toContext(
        enableProvideRoute: Boolean,
        enablePublishComponent: Boolean
    ) = Context(
        rubik.project,
        enableProvideRoute,
        enablePublishComponent,
        _uri ?: throw RubikDSLComponentUriException(_uri.toString()),
        _touching.toDependencies(),
        _packing.toDependencies(),
        _source.toSource(rubik.project),
        _tags.mapValues { (_, source) -> source?.toSource(rubik.project) }
    )

    val sourcePickHow: FlavorHows
        get() = _source.pickHows


    override fun toString() =
        "DSLContext : uri:$_uri  touching:$_touching packing:$_packing  source:$_source "


}