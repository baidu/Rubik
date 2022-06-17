package com.rubik.plugins.extension.context.source


import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class SourceExtension {
    val project: ProjectExtension = ProjectExtension()
    var maven: MavenExtension = MavenExtension()

    fun project(path: String) {
        project.path(path)
    }

    fun project(closure: Closure<ProjectExtension>) {
        ConfigureUtil.configure(closure, project)
    }

    fun project(path: String, closure: Closure<ProjectExtension>) {
        project(path)
        project(closure)
    }

    fun maven(closure: Closure<MavenExtension>) {
        ConfigureUtil.configure(closure, maven)
    }

    fun updateProjectPathIfNull(path: String) {
        if (null == project.path)
            project.path = path
    }

    override fun toString() = "SourceExtension: project:$project  maven:$maven "

}