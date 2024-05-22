package com.rubik.publish.task

import com.ktnail.gradle.maven.PublicationType
import com.rubik.publish.task.global.*
import org.gradle.api.Project

/**
 * Task graphic of all context libs in project,
 * graph assemble all task & publish all task.
 *
 * @since 1.5
 */
class GlobalPublishTasksController(
    val project: Project
) {
    private val allContextLibsTasks =
        AllContextLibsTask(project, PublicationType.FORMAL) to AllContextLibsTask(project, PublicationType.DEV)
    private val tagContextLibsTasks =
        mutableMapOf<String, PublishTaskPair<TagContextLibsTask>>()

    private val allComponentTasks =
        AllComponentTask(project, PublicationType.FORMAL) to AllComponentTask(project, PublicationType.DEV)
    private val tagComponentTasks =
        mutableMapOf<String, PublishTaskPair<TagComponentTask>>()

    private val allComponentAndContextLibsTasks =
        AllComponentAndContextLibsTask(project, PublicationType.FORMAL) to AllComponentAndContextLibsTask(project, PublicationType.DEV)
    private val tagComponentAndContextLibsTasks =
        mutableMapOf<String, PublishTaskPair<TagComponentAndContextLibsTask>>()

    fun initTags(tags: Set<String>) {
        tags.forEach { tag ->
            tagContextLibsTasks.getOrPut(tag) {
                TagContextLibsTask(project, tag, PublicationType.FORMAL) to TagContextLibsTask(project, tag, PublicationType.DEV)
            }
            tagComponentTasks.getOrPut(tag) {
                TagComponentTask(project, tag, PublicationType.FORMAL) to TagComponentTask(project, tag, PublicationType.DEV)

            }
            tagComponentAndContextLibsTasks.getOrPut(tag) {
                TagComponentAndContextLibsTask(project, tag, PublicationType.FORMAL) to TagComponentAndContextLibsTask(project, tag, PublicationType.DEV)

            }
        }
    }

    fun contextTasks(taskGroup: PublishContextTaskGroup) {
        taskGroup.libTasks?.let { tasks ->
            allContextLibsTasks.linkExecuteDependsOn(tasks)
            taskGroup.tags?.forEach { tag ->
                tagContextLibsTasks[tag]?.linkExecuteDependsOn(tasks)
            }
        }
        taskGroup.componentTasks?.let { tasks ->
            allComponentTasks.linkExecuteDependsOn(tasks)
            taskGroup.tags?.forEach { tag ->
                tagComponentTasks[tag]?.linkExecuteDependsOn(tasks)
            }
        }
        taskGroup.bothTasks?.let { tasks ->
            allComponentAndContextLibsTasks.linkExecuteDependsOn(tasks)
            taskGroup.tags?.forEach { tag ->
                tagComponentAndContextLibsTasks[tag]?.linkExecuteDependsOn(tasks)
            }
        }
    }

}