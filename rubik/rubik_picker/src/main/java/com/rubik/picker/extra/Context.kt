package com.rubik.picker.extra

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.propertyOr
import com.rubik.context.*
import com.rubik.context.extra.contextsContainer
import com.rubik.context.extra.Context
import com.rubik.pick.*
import com.rubik.picker.PickCase
import org.gradle.api.Project


fun PickWhere.obtainContext(strict: Boolean = true): List<Context> {
    return when (this) {
        is ByFuzzyUri -> {
            contextsContainer.obtainAny().filter { context-> this.matching(context.uri) }
        }
        is ByUri -> {
            contextsContainer.obtainByUri(uri, strict)?.let { context ->
                listOf(context)
            }
        }
        is ByTag -> {
            contextsContainer.obtainByTag(this.tag)
        }
        is ByAll -> {
            contextsContainer.obtainAny()
        }
    } ?: emptyList()
}

//  pick case
fun Context.holdPicked(hows: FlavorHows?, what: PickWhat) {
    pickCase.hold(hows, what)
}

fun Context.releasePicked(exc: Excepted) = pickCase.release(exc)

fun Context.getShellPickCase(variant: BaseVariant? = null): PickCase? {
    return if (null != variant) pickCase.shellPickCases(variant) else return pickCase.shellPickCases()
}

fun Context.getPickCase(what: PickWhat): PickCase {
    return pickCase.pickCase(what)
}

val Context.sourcePickHows: FlavorHows
    get() = sourcePickHow ?: mutableMapOf()

val Source.configPickParameters: FlavorHows
    get() = mutableMapOf<String?, PickHow>().apply {
        put(null, toPickParameters())
        maven.flavors?.forEach { (flavor, flavorMaven) ->
            put(flavor, flavorMaven.toPickParameters())
        }
    }

fun Source.toPickParameters() =
    ImperfectParameters(maven.version, maven.variant, projectPath)

fun MavenSource.toPickParameters(defaultVariant: String? = null) =
    ImperfectParameters(version, variant ?: defaultVariant, null)

fun Dependency.componentPickWhat(project: Project): PickWhat =
    if (project.propertyOr(Ext.RUBIK_CONTEXT_PACKING_COMPILE_ONLY, true))
        contextDependComponentCompileOnlyWhat(uri, forFlavor)
    else contextDependComponentWhat(uri, forFlavor)

