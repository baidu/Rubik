package com.rubik.publish.task.target

import com.ktnail.gradle.mapFirstTaskTarget
import com.ktnail.gradle.p
import com.ktnail.x.Logger
import com.rubik.publish.log.LogTags
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

class TaskTargetContainer {
    private val taskTargetNames = mutableMapOf<String, PublishTaskName>()
    val taskTargets = mutableMapOf<String, ContextTaskTarget>()

    // todo Context.extra 中的 target
    fun projectTargetTaskName(project: Project): PublishTaskName? =
        taskTargetNames[project.path] ?: project.targetTaskNameInProject?.apply {
            taskTargetNames[project.path] = this
        }

    private val Project.targetTaskNameInProject
        get() = mapFirstTaskTarget { taskName ->
            PublishTaskName.fromFullName(taskName)
        }?.also { name ->
            Logger.p(LogTags.PUBLISH, this) { " START TASK NAME ($path) :${name.toLog()}" }
        }
}