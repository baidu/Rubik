package com.rubik.dsl.packing

import com.rubik.dsl.DSLRubik
import com.rubik.dsl.RDSL
import com.rubik.pick.ByTag
import com.rubik.pick.ByUri
import com.rubik.pick.Excepted
import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class DSLExcept(
    private val rubik: DSLRubik,
    private val forFlavor: String?,
    private val onExcept: (Excepted) -> Unit
) {

    @RDSL
    fun flavor(name: String, closure: Closure<DSLExcept>) {
        DSLExcept(rubik, name, onExcept).apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    @RDSL
    fun tag(vararg tags: String) {
        tags.forEach { tag ->
            onExcept(Excepted(forFlavor, ByTag(tag)))
        }
    }

    @RDSL
    fun uri(vararg uriOrAuthorityOrNames: String) {
        uriOrAuthorityOrNames.forEach { uriOrAuthorityOrName ->
            onExcept(Excepted(forFlavor, ByUri.create(rubik.authorityOrNameToUri(uriOrAuthorityOrName))))
        }
    }
}