package com.rubik.plugins.context.task.lib

import com.rubik.plugins.basic.task.TaskGraphic
import com.rubik.plugins.basic.utility.rubikLibsTask
import com.rubik.plugins.context.AllContextController
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Task graphic of all context libs in project,
 * graph assemble all task & publish all task.
 *
 * @since 1.5
 */
class AllContextLibsTaskGraphic(
    val project: Project
) : TaskGraphic() {

    private val assembleLibsTask: Task = project.rubikLibsTask(AllContextController.ASSEMBLE_ALL_LIBS_NAME)
    private val publishLibsTask: Task = project.rubikLibsTask(AllContextController.PUBLISH_ALL_LIBS_NAME)
    private val publishDevLibsTask: Task = project.rubikLibsTask(AllContextController.PUBLISH_ALL_DEV_LIBS_NAME)

    fun addAssembleLibTasks(task: Task) {
        // (task) > assembleLibsTask
        assembleLibsTask.finalizedBy(task)
    }

    fun addPublishLibTasks(task: Task) {
        // (task) > publishLibsTask
        publishLibsTask.finalizedBy(task)
    }

    fun addPublishDevLibTasks(task: Task) {
        // (task) > publishDevLibsTask
        publishDevLibsTask.finalizedBy(task)
    }
}