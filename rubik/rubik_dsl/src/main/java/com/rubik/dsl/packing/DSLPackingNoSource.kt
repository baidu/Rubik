package com.rubik.dsl.packing

import com.rubik.dsl.RDSL


open class DSLPackingNoSource {

    @set:RDSL
    var enableAggregate: Boolean = true

    @set:RDSL
    var enableReflect: Boolean = false

    @RDSL
    fun aggregate(enable: Boolean) {
        this.enableAggregate = enable
    }

    @RDSL
    fun reflect(enable: Boolean) {
        this.enableReflect = enable
    }

    override fun toString() = "DSLPackingNoSource: enableAggregate:$enableAggregate  enableReflect:$enableReflect"
}