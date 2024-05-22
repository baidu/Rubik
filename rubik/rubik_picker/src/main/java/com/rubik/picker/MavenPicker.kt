package com.rubik.picker

import com.ktnail.gradle.addDependency
import com.ktnail.gradle.maven.MavenDependency
import com.rubik.context.extra.Context
import com.rubik.pick.PickHow
import com.rubik.pick.PickWhat
import com.rubik.picker.extra.toMavenDependency
import org.gradle.api.Project

class MavenPicker(
    override val what: PickWhat,
    override val how: PickHow?,
    override val context: Context,
    private val afterPick: ((ContextPicker.PickResult) -> Unit)? = null
) : Picker {

    override fun pick(forProject: Project, forFlavor: String?) {
        toDependencies(forFlavor).forEach { (type ,dependency)->
            val version = dependency.version ?: ""
            val variant = how?.variant ?: ""
            forProject.addDependency(type, dependency)
            afterPick?.invoke(ContextPicker.PickResult(context, version, variant))
        }
    }

    fun toDependencies(forFlavor: String?): List<Pair<String, MavenDependency>> =
        mutableListOf<Pair<String, MavenDependency>>().apply {
        if (null != how) {
            what.items.filter { what -> what.condition(context) }.forEach { item ->
                add(
                    item.dependencyType to context.toMavenDependency(
                        item,
                        how,
                        dev = what.dev,
                        forFlavor = forFlavor
                    )
                )
            }
        }
    }

    fun falsePick(forFlavor: String?) {
        toDependencies(forFlavor).forEach { (_, dependency) ->
            val version = dependency.version ?: ""
            val variant = how?.variant ?: ""
            afterPick?.invoke(ContextPicker.PickResult(context, version, variant))
        }
    }

}

