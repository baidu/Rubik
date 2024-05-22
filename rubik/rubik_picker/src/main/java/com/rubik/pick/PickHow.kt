package com.rubik.pick

import com.rubik.context.router.RouterRegister


sealed class PickHow(
    private val parameters: How
) {
    data class How(
        val version: String? = null,
        val variant: String? = null,
        val projectPath: String? = null,
        val register: RouterRegister? = null
    ) {
        fun copy(other: How) = How(
            version = version ?: other.version,
            variant = variant ?: other.variant,
            projectPath = projectPath ?: other.projectPath,
            register = register ?: other.register
        )
    }

    val version: String? get() = parameters.version
    val variant: String? get() = parameters.variant
    val projectPath: String? get() = parameters.projectPath
    val register: RouterRegister? get() = parameters.register

    abstract fun copy(parameters: How): PickHow

    fun mergeLowerPriority(other: PickHow) = copy(parameters.copy(other.parameters))

    fun mergeHigherPriority(other: PickHow) = copy(other.parameters.copy(parameters))

    fun isSufficient() = this !is ImperfectParameters && null != version && null != variant && null != projectPath

    override fun toString() =
        "[${this::class.java.simpleName}] - [version:${version}] - [variant:${variant}] - " +
                "[project:${projectPath}] - [register:${register}]"

}

class ProjectMode(
    parameters: How
) : PickHow(parameters) {
    constructor(projectPath: String? = null) : this(How(projectPath = projectPath))

    override fun copy(parameters: How) = ProjectMode(parameters)
}

class MavenMode(
    parameters: How
) : PickHow(parameters) {
    constructor(version: String? = null, variant: String? = null) : this(How(version = version, variant = variant))

    override fun copy(parameters: How) = MavenMode(parameters)
}

class TmpDirMode(
    parameters: How
) : PickHow(parameters) {
    constructor() : this(How())

    override fun copy(parameters: How) = TmpDirMode(parameters)
}

class NoSourceMode(
    parameters: How
) : PickHow(parameters) {
    constructor(register: RouterRegister) : this(How(register = register))

    override fun copy(parameters: How) = NoSourceMode(parameters)
}

class ImperfectParameters(
    parameters: How
) : PickHow(parameters) {
    constructor(
        version: String?,
        variant: String?,
        projectPath: String?
    ) : this(How(version = version, variant = variant, projectPath = projectPath))

    override fun copy(parameters: How) = ImperfectParameters(parameters)
}

fun PickHow?.mergeLowerPriority(vararg lowers: PickHow?): PickHow? {
    val how = lowers.fold(this) { acc, lower ->
        if (null != lower && acc?.isSufficient() != true) when (acc) {
            null -> lower
            is ImperfectParameters -> lower.mergeHigherPriority(acc)
            else -> acc.mergeLowerPriority(lower)
        }
        else acc
    }
    return how
}

typealias FlavorHows = Map<String?, PickHow>

val FlavorHows.defaultPickOnly: Boolean
    get() = this.isEmpty() || (size == 1 && null != get(null))


fun FlavorHows.flavorMergeDefault() = toMutableMap().apply {
    forEach { (flavor, how) ->
        if (null != flavor) {
            how.mergeLowerPriority(this[null])?.let { newHow ->
                this[flavor] = newHow
            }
        }
    }
}

fun FlavorHows.forEachByFlavorOrDefault(
    byFlavor: (String, PickHow) -> Unit,
    default: ((PickHow) -> Unit)? = null
) {
    val hasFlavor = keys.any { key -> key != null }
    forEach { (flavor, packHow) ->
        if ((hasFlavor && flavor != null)) {
            byFlavor(flavor, packHow)
        } else if ((!hasFlavor && flavor == null) && null != default) {
            default(packHow)
        }
    }
}

fun FlavorHows?.toLog(tag: String): String {
    return if (this == null) "" else " \n${tag}" +
            if (this.isNotEmpty())
                this.toList().fold("") { acc, (flavor, pickHow) ->
                    acc + (if (this.size == 1) "" else "\n   ") + " -> FLAVOR:${flavor} HOW : $pickHow"
                }
            else " EMPTY! "
}