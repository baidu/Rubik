package com.rubik.picker

import com.android.build.gradle.internal.dsl.BuildType
import com.ktnail.gradle.addProjectDependency
import com.ktnail.gradle.androidExtension
import com.rubik.context.extra.Context
import com.rubik.pick.PickHow
import com.rubik.pick.PickWhat
import com.rubik.pick.What
import com.rubik.picker.exception.RubikProjectNotFoundException
import com.rubik.picker.exception.RubikProjectPathNotSetException
import org.gradle.api.Project

class ProjectPicker(
    override val what: PickWhat,
    override val how: PickHow?,
    override val context: Context,
    private val  exceptBuildType: ((BuildType) -> Boolean)? = null
) : Picker {
    override fun pick(forProject: Project, forFlavor: String?) {
        if (null == how) return
        val path = how.projectPath
        path ?: throw RubikProjectPathNotSetException(context.uri)
        if (path != forProject.path) {
            val projectToPick = try {
                forProject.project(path)
            } catch (e: Exception) {
                throw RubikProjectNotFoundException(context.uri, path)
            }
            what.items.filter { what -> what.condition(context) }.forEach { item ->
                if (item is What.Component) {
                    doPick(item, forProject, forFlavor, projectToPick)
                }
            }
        }
    }

    private fun doPick(
        component: What.Component, forProject: Project, forFlavor: String?, projectToPick: Project
    ) {
        when {
            null != forFlavor -> forProject.addProjectDependency(
                component.dependencyType,
                forFlavor,
                projectToPick
            )
            null != exceptBuildType -> forProject.androidExtension?.buildTypes?.all { buildType ->
                if (!exceptBuildType.invoke(buildType)) {
                    forProject.addProjectDependency(
                        component.dependencyType,
                        buildType.name,
                        projectToPick
                    )
                }
            }
            else -> forProject.addProjectDependency(
                component.dependencyType,
                projectToPick
            )
        }
    }
}