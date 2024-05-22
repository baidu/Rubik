package com.rubik.dsl.context.dependency

import com.rubik.dsl.RDSL


open class DSLDependency(val uri: String) {

    var forFlavor: String? = null

    @RDSL
    fun forFlavor(flavor: String) {
        forFlavor = flavor
    }

    override fun toString() = "DSLDependency: uri:$uri "

}