package com.rubik.pick

import com.ktnail.gradle.DependencyType
import com.rubik.context.extra.Context
import com.rubik.context.publication.LibType


class PickWhat(
    val where: PickWhere,
    val items: Array<What> = arrayOf(),
    val forFlavor: String? = null,
    val dev: Boolean? = null
) {

    override fun toString(): String =
        "[$where] - [${items.joinToString(",")}] - [flavor:${forFlavor}] - [dev:${dev}]"

    val itemsOnlyLib = null == items.find { what -> what is What.Component }

}

sealed class What(
    val dependencyType: String
) {
    open fun condition(context: Context) = true

    class Component(
        dependencyType: String = DependencyType.IMPLEMENTATION
    ) : What(dependencyType) {
        override fun toString() = "$dependencyType COMPONENT"
    }

    class Lib(
        dependencyType: String = DependencyType.IMPLEMENTATION,
        val type: String
    ) : What(dependencyType) {
        override fun condition(context: Context) =
            if (!context.enableProvideRoute)
                false
            else if (type == LibType.ORIGINAL_VALUE)
                context.source.publishOriginalValue
            else
                true

        override fun toString() = "$dependencyType LIB-${type}"
    }
}

fun shellPackingComponentWhat(
    forFlavor: String?,
    where: PickWhere
) = PickWhat(
    where,
    arrayOf(What.Component()),
    forFlavor = forFlavor
)

fun shellPackingLibWhat() = PickWhat(
    ByAll(),
    arrayOf(
        What.Lib(type = LibType.CONTEXT),
        What.Lib(dependencyType = DependencyType.COMPILE_ONLY, type = LibType.ORIGINAL_VALUE)
    )
)

fun contextDependLibWhat(
    uri: String,
    dev: Boolean?
) = PickWhat(
    ByUri(uri),
    arrayOf(
        What.Lib(type = LibType.CONTEXT),
        What.Lib(dependencyType = DependencyType.COMPILE_ONLY, type = LibType.ORIGINAL_VALUE)
    ), dev = dev
)


fun contextDependLibCompileOnlyWhat(
    uri: String,
    dev: Boolean?
) = PickWhat(
    ByUri(uri),
    arrayOf(
        What.Lib(dependencyType = DependencyType.COMPILE_ONLY, type = LibType.CONTEXT),
        What.Lib(dependencyType = DependencyType.COMPILE_ONLY, type = LibType.ORIGINAL_VALUE)
    ), dev = dev
)

fun contextDependComponentWhat(
    uri: String,
    forFlavor: String?
) = PickWhat(
    ByUri(uri),
    arrayOf(What.Component()),
    forFlavor = forFlavor
)


fun contextDependComponentCompileOnlyWhat(
    uri: String,
    forFlavor: String?
) = PickWhat(
    ByUri(uri),
    arrayOf(What.Component(dependencyType = DependencyType.COMPILE_ONLY)),
    forFlavor = forFlavor
)