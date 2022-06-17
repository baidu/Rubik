package com.rubik.plugins.extension.root.packing

open class NoSourceExtension {
    var enableAggregate: Boolean = true
    var enableReflect: Boolean = false

    fun aggregate(enable: Boolean) {
        this.enableAggregate = enable
    }

    fun reflect(enable: Boolean) {
        this.enableReflect = enable
    }

    override fun toString() = "DynamicExtension: enableAggregate:$enableAggregate  enableReflect:$enableReflect"
}