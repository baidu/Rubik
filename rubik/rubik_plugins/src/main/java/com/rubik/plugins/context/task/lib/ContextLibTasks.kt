package com.rubik.plugins.context.task.lib

import com.ktnail.x.Logger
import com.ktnail.x.toCamel
import com.rubik.plugins.MainPlugin
import com.rubik.plugins.context.ContextPlugin
import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.basic.publish.maven.PublicationType
import com.rubik.plugins.basic.utility.isTaskTarget
import com.rubik.plugins.basic.utility.rubikLibDevTask
import com.rubik.plugins.basic.utility.rubikLibTask
import com.rubik.plugins.context.AllContextController
import org.gradle.api.Project
import org.gradle.api.Task

class ContextLibTasks(
    project: Project,
    val context: ContextExtension,
    buildType: String
) {
    companion object {
        fun create(
            project: Project,
            context: ContextExtension,
            buildType: String
        ): ContextLibTasks? =
            if (context.enableProvideRoute)
                ContextLibTasks(project, context, buildType)
            else
                null
        }

    val assembleTask: Task = project.rubikLibTask(toCamel(ContextPlugin.ASSEMBLE_TASK_NAME_PREFIX, buildType))
    val publishTask: Task? = if(context.enablePublish) project.rubikLibTask(toCamel(ContextPlugin.PUBLISH_TASK_NAME_PREFIX, buildType)) else null
    val publishDevTask: Task? = if(context.enablePublishDev) project.rubikLibDevTask(toCamel(ContextPlugin.PUBLISH_DEV_TASK_NAME_PREFIX, buildType)) else null

    fun isConfiguring(project: Project): PublicationType? {
        return when {
            project.isTaskTarget(
                *arrayOf(
                    assembleTask.name,
                    publishTask?.name,
                    AllContextController.ASSEMBLE_ALL_LIBS_NAME,
                    AllContextController.PUBLISH_ALL_LIBS_NAME
                ).filterNotNull().toTypedArray()
            ) -> PublicationType.FORMAL
            project.isTaskTarget(
                *arrayOf(
                    publishDevTask?.name,
                    AllContextController.PUBLISH_ALL_DEV_LIBS_NAME
                ).filterNotNull().toTypedArray()
            ) -> PublicationType.DEV
            else -> null
        }
    }
}