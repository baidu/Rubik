package com.rubik.plugins.module.config.record


data class ProjectConfigRecord(
    var byteCodeModel: Boolean,
    val variants: MutableMap<String, VariantConfigRecord> = mutableMapOf()
)