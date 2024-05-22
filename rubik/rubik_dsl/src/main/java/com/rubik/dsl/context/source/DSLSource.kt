package com.rubik.dsl.context.source


import com.ktnail.gradle.projectPathToFilePath
import com.rubik.context.Source
import com.rubik.dsl.RDSL
import com.rubik.dsl.packing.DSLPackingHow
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

open class DSLSource : DSLPackingHow() {
    private val project: DSLProjectSource = DSLProjectSource()
    var maven: DSLMavenSource = DSLMavenSource()

    @set:RDSL
    var allowAutoVersion: Boolean = true

    @RDSL
    fun project(path: String) {
        project.path(path)
    }

    @RDSL
    fun project(closure: Closure<DSLProjectSource>) {
        ConfigureUtil.configure(closure, project)
    }

    @RDSL
    fun project(path: String, closure: Closure<DSLProjectSource>) {
        project(path)
        project(closure)
    }

    @RDSL
    fun maven(closure: Closure<DSLMavenSource>) {
        ConfigureUtil.configure(closure, maven)
    }

    @RDSL
    fun allowAutoVersion(allow: Boolean) {
        allowAutoVersion = allow
    }

    fun toSource(gradleProject: Project) = Source(
        this.project.path,
        this.project.publishVersion,
        this.project.publishOriginalValue,
        allowAutoVersion,
        maven.toMavenSource(),
        this.project.path?.let { path ->
            {
                this.project.executor(gradleProject.projectDir, path.projectPathToFilePath())
            }
        }
    )

    override fun toString() = "SourceExtension: project:$project  maven:$maven "

}
