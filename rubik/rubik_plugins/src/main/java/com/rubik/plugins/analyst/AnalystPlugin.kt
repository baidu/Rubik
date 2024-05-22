package com.rubik.plugins.analyst

import com.ktnail.x.Logger
import com.rubik.plugins.RubikPlugin
import org.gradle.api.Project
import java.io.File

/**
 *
 *  @since 1.8
 */
class AnalystPlugin : RubikPlugin() {
    override fun apply(project: Project) {
        super.apply(project)

        project.beforeEvaluate {
            Logger.e("beforeEvaluate : ${it.path} executed : ${it.state.executed}")
        }
        project.afterEvaluate {
            Logger.e("afterEvaluate : ${it.path} executed : ${it.state.executed}")
        }
        project.gradle.allprojects{
            Logger.e("allprojects : ${it.path} executed : ${it.state.executed}")

        }
        project.gradle.projectsEvaluated{
            Logger.e("projectsEvaluated")

        }
        project.gradle.afterProject{
            Logger.e("afterProject: ${it.path} executed : ${it.state.executed}")
        }
        project.gradle.settingsEvaluated{
            Logger.e("settingsEvaluated")

        }
        project.gradle.projectsLoaded{
            Logger.e("projectsLoaded")

        }
    }
}

