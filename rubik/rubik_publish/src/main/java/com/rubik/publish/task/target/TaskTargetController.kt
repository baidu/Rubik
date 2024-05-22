package com.rubik.publish.task.target

import com.ktnail.gradle.p
import com.ktnail.x.Logger
import com.rubik.context.extra.Context
import com.rubik.publish.publishTaskArtifactName
import com.rubik.context.extra.contextsContainer
import com.rubik.publish.extra.target
import com.rubik.publish.extra.targetTaskName
import com.rubik.publish.log.LogTags
import com.rubik.publish.task.name.PublishByMeaning
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

class TaskTargetController(private val rootProject: Project) {

    fun checkTaskTarget(subProject: Project, context: Context) =
        subProject.targetTaskName?.let { taskName ->
            doCheck(context, taskName).apply {
                context.target = this
                Logger.p(LogTags.PUBLISH, rootProject) { " TASK TARGET:[${context.uri}] " + this.toLog() }
            }
        } ?: ContextTaskTarget.doNotPublish(context)

    private fun doCheck(
        context: Context,
        publishTaskName: PublishTaskName
    ): ContextTaskTarget {
        return when (val publishBy: PublishByMeaning = publishTaskName.meaning.publishBy) {
            is PublishByMeaning.PackingLink -> {
                if (publishBy.name == context.publishTaskArtifactName || context.isPackingName(publishBy.name))
                    ContextTaskTarget.publish(context, publishTaskName, null)
                else null
            }
            is PublishByMeaning.All -> ContextTaskTarget.publish(context, publishTaskName, null)
            is PublishByMeaning.ByTag -> {
                val findTag = context.tagNames.firstOrNull { tag -> publishBy.tag == tag }
                if (null != findTag) ContextTaskTarget.publish(context, publishTaskName, findTag)
                else null
            }
            is PublishByMeaning.ByName-> {
                if (publishBy.name == context.publishTaskArtifactName)
                    ContextTaskTarget.publish(context, publishTaskName, null)
                else null
            }
        } ?: ContextTaskTarget.doNotPublish(context)
    }


    private fun Context.isPackingName(name: String) =
        contextsContainer.obtainPackingLink(this).any { packed -> name == packed.publishTaskArtifactName }

}