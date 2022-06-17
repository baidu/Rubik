package com.rubik.plugins.context.task.component

import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.context.model.Component
import com.rubik.plugins.basic.publish.maven.PublishingTaskProvider
import com.rubik.plugins.basic.task.TaskGraphic
import com.rubik.plugins.basic.task.graphicDependsOn
import com.rubik.plugins.basic.task.graphicFinalizedWith
import com.rubik.plugins.basic.utility.Ext
import com.rubik.plugins.basic.utility.p
import com.rubik.plugins.basic.utility.propertyOr
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import java.io.File

/**
 * Task graphic of business code of contexts,
 * graph publish tasks.
 *
 * @since 1.4
 */
class ContextComponentTaskGraphic(
    project: Project,
    componentTasks: ContextComponentTasks,
    publishingTaskProvider: PublishingTaskProvider,
    file: File
) : TaskGraphic() {
    private val component = Component(project, componentTasks.context, componentTasks.variant, file)

    private val publishComponentTask = componentTasks.publishTask
    private val publishDevComponentTask = componentTasks.publishDevTask

    init {
        val publishingTask = if (null != publishComponentTask) publishingTaskProvider.getTask(component).apply {
            doLast {
                Logger.p(LogTags.PUBLISH_COMPONENTS,null) { " <${component.groupId}:${component.artifactId}:${component.version}>  SUCCEEDED !!!" }
            }
        } else null
        val publishingDevTask = if (null != publishDevComponentTask) publishingTaskProvider.getTask(component,true).apply {
            doLast {
                Logger.p(LogTags.PUBLISH_COMPONENTS,null) { " <${component.groupId}:${component.artifactId}:${component.version}>  DEV SUCCEEDED !!!" }
            }
        } else null

        whenGraph {
            // clean > assembleProvider > publishLibTask > publishingTask
            if (project.propertyOr(Ext.RUBIK_CLEAN_BEFORE_TASK, true))
                componentTasks.variant.preBuildProvider.get().graphicDependsOn(project.tasks.getByName(BasePlugin.CLEAN_TASK_NAME))

            if (null != publishComponentTask && null != publishingTask) {
                publishComponentTask.graphicDependsOn(componentTasks.variant.assembleProvider)
                publishComponentTask.graphicFinalizedWith(publishingTask)
            }

            if (null != publishDevComponentTask && null != publishingDevTask) {
                publishDevComponentTask.graphicDependsOn(componentTasks.variant.assembleProvider)
                publishDevComponentTask.graphicFinalizedWith(publishingDevTask)
            }
        }
    }
}