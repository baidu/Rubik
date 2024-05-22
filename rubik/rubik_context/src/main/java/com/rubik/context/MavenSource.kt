package com.rubik.context

data class MavenSource(
    var version: String?,
    var variant: String?,
    val flavors: Map<String, MavenSource>?
)

fun MavenSource?.mergeOther(other: MavenSource): MavenSource =
    if (this == null) other else
        MavenSource(version ?: other.version, variant ?: other.variant, flavors ?: other.flavors)