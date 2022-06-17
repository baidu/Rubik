package com.rubik.plugins.module.config.record

import com.rubik.plugins.module.relation.Module

typealias  ConfigRecords = MutableMap<String, ProjectConfigRecord>

fun ConfigRecords.getByModule(module: Module): VariantConfigRecord? {
    val project = module.project.path
    val variant = module.variant.name
    return get(project)?.variants?.get(variant)
}