package com.rubik.plugins.context.controller

import com.ktnail.gradle.*
import com.rubik.context.Ext
import com.rubik.context.extra.Context
import com.rubik.context.extra.contextsContainer
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikProjectNoKotlinAndroidPluginException
import com.rubik.plugins.basic.extra.*
import com.rubik.publish.extra.publishTasksContainer
import com.rubik.publish.task.GlobalPublishTasksController
import com.rubik.publish.task.target.TaskTargetController
import org.gradle.api.Project

class AllContextController(
    private val project: Project
) {
    private val tasksController = GlobalPublishTasksController(project)
    private val taskTargetController = TaskTargetController(project)
    private val projectSyncController = ProjectSyncController(project)

    fun init() {
        project.fuzzyApplyRubikPublicationRecordFiles()
        project.fuzzyApplyRubikConfigFiles(project.projectDir)
        project.fuzzyApplyGivenRubikConfigFileDirs()

        LogTags.logApplyConfigFinish(project)

        listenTasksRegistered()
        if (!project.isTaskTarget(ProjectSyncController.PROJECT_SYNC_TASK_NAME)) {
            processContextProjects()
            processShellProjects()
        }
        projectSyncController.init()
    }

    private fun listenTasksRegistered() {
        publishTasksContainer.listenTasksRegistered { task ->
            tasksController.contextTasks(task)
        }
    }

    private fun processContextProjects() {
        contextsContainer.obtainAny().forEach { context ->
            tasksController.initTags(context.tagNames)
            context.projectPath?.let { path ->
                project.findProject(path)?.let { subProject ->
                    processContextPackingLink(context)
                    taskTargetController.checkTaskTarget(subProject, context)
                    subProject.pluginManager.withPlugin(PluginName.KOTLIN_ANDROID) {
                        if (context.enableProvideRoute || context.enablePublishComponent) {
                            subProject.applyContext()
                        }
                    }
                    subProject.whenExecuted {
                        if (!subProject.pluginManager.hasPlugin(PluginName.KOTLIN_ANDROID)) {
                            throw RubikProjectNoKotlinAndroidPluginException(subProject.path)
                        }
                    }
                }
            }
        }
    }

    private fun processContextPackingLink(context: Context) {
        context.packingWho = contextsContainer.obtainPacking(context)
        context.whoPackingMe = contextsContainer.obtainPackingThis(context)
    }

    private fun processShellProjects() {
        project.allprojects { subProject ->
            val autoApplyShell = project.propertyOr(Ext.RUBIK_AUTO_APPLY_SHELL_PLUGIN, true)
            if (autoApplyShell) {
                subProject.ifAndroidApplication {
                    if (autoApplyShell) {
                        subProject.applyShell()
                    }
                }
            }
        }
    }

}