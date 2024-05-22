package com.rubik.context.id

import com.rubik.context.extra.Context

class ContextIdHolder(val context: Context) {
    val uri: String
        get() = context.uri
    var version: String? = null
    var variant: String? = null
    val touched: MutableList<ContextIdHolder> = mutableListOf()
    val packed: MutableList<ContextIdHolder> = mutableListOf()

    fun addVersion(version: String){
        this.version = version
    }

    fun toJson() =
        LeastContext(
            uri,
            context.name,
            version ?: "unkown"
        ).toJson()

    fun addVariant(variant: String){
        this.variant = variant
    }

    fun addTouching(depContext: Context, version: String, variant: String?) {
        touched.add(ContextIdHolder(depContext).apply {
            this.version = version
            this.variant = variant
        })
    }

    fun addPacking(depContext: Context, version: String, variant: String?) {
        packed.add(ContextIdHolder(depContext).apply {
            this.version = version
            this.variant = variant
        })
    }

}
