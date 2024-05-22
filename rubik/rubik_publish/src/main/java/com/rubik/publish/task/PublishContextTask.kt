package com.rubik.publish.task

import com.ktnail.gradle.maven.PublicationType
import com.ktnail.gradle.task.linkDependsOn
import com.ktnail.gradle.whenExecuted
import com.ktnail.x.VersionCreator
import com.rubik.context.extra.Context
import com.rubik.publish.extra.rubikTask
import com.rubik.publish.extra.targetTaskName
import com.rubik.publish.doNotPublishVersion
import com.rubik.publish.log.LogTags
import com.rubik.publish.publishArtifactName
import com.rubik.publish.publishDevArtifactName
import com.rubik.publish.publishVersion
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

abstract class PublishTask(
    val project: Project,
    val name: PublishTaskName,
    val publicationType: PublicationType
) {
    private val targetTaskName
        get() = project.targetTaskName

    val gradleTask = project.rubikTask(name)

    open val startTaskNames: Array<PublishTaskName>
        get() = arrayOf(name)

    /**
     *   is the task you chose to execute
     */
    val targetExecute: Boolean
        get() = null != startTaskNames.find { name -> name.fullName == targetTaskName?.fullName }

    /**
     *   this task will be execute this time
     */
    var needExecute: Boolean = false
        get() = targetExecute || field

    // graphic
    private var graphiced = false

    open fun onGraphic() {}

    private fun graphic() {
        needExecute = true
        if (!graphiced) {
            graphiced = true
            onGraphic()
            LogTags.logTaskGraphicFinish(this::class.java.simpleName, name.fullName)
        }
        executeActions.forEach {action->
            action()
        }
        executeActions.clear()
    }

    fun graphicIfTarget() {
        if (targetExecute) {
            graphic()
        }
    }

    private fun graphicIfDepends() {
        project.whenExecuted {
            graphic()
        }
    }

    private val executeActions = mutableListOf<() -> Unit>()

    fun ifExecute(action: () -> Unit) {
        if (needExecute) {
            action()
        } else {
            executeActions.add(action)
        }
    }

    fun linkExecuteDependsOn(other: PublishTask) {
        gradleTask.linkDependsOn(other.gradleTask)
        graphicIfDepends()
        other.graphicIfDepends()
    }

}

abstract class PublishContextTask(
    project: Project,
    val context: Context,
    name: PublishTaskName,
    publicationType: PublicationType,
    val byTag: String?
) : PublishTask(project, name, publicationType) {

    private val baseVersion
        get() = if (needExecute) context.publishVersion(publicationType, byTag)
        else context.doNotPublishVersion()

    val versionCreator = VersionCreator { baseVersion }

    val version
        get() = versionCreator.version

    val artifactName: String
        get() =if (publicationType == PublicationType.DEV) context.publishDevArtifactName else context.publishArtifactName

    val tags
        get() = context.tagNames
}

typealias  PublishTaskPair<T> = Pair<T, T>

fun <L : PublishTask, R : PublishTask> PublishTaskPair<R>.linkExecuteDependsOn(other: PublishTaskPair<L>) {
    if (first.needExecute)
        first.linkExecuteDependsOn(other.first)
    if (second.needExecute)
        second.linkExecuteDependsOn(other.second)
}