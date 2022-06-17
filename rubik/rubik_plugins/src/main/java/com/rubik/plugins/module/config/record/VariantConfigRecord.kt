package com.rubik.plugins.module.config.record

data class VariantConfigRecord(
    var token: String? = null
) {
    fun getVersion() = token
}