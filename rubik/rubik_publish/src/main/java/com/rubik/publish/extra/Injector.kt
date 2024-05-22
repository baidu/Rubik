package com.rubik.publish.extra

import com.ktnail.gradle.maven.MavenDependency
import com.rubik.context.extra.Context
import com.rubik.context.extra.rubikMainProject
import com.rubik.context.utility.Module
import com.rubik.context.utility.ModuleInjector
import com.rubik.publish.record.PublicationRecords
import com.rubik.publish.task.PublishContextTasksContainer
import com.rubik.publish.task.name.PublishTaskName
import com.rubik.publish.task.target.ContextTaskTarget
import com.rubik.publish.task.target.TaskTargetContainer
import org.gradle.api.Project

interface PublishModuleInjector : ModuleInjector {
    val publicationRecords: PublicationRecords
    val publishTasksContainer: PublishContextTasksContainer
    val taskTargetContainer: TaskTargetContainer
    fun contextToDependLibMavenDependencies(
        context: Context, version: String, dev: Boolean?
    ): List<Pair<String, MavenDependency>>
}

object PublishModule : Module<PublishModuleInjector>()

val publicationRecords: PublicationRecords
    get() = PublishModule.content.publicationRecords

val publishTasksContainer: PublishContextTasksContainer
    get() = PublishModule.content.publishTasksContainer

val Project.targetTaskName: PublishTaskName?
    get() = PublishModule.content.taskTargetContainer.let { container->
        container.projectTargetTaskName(this)?:   container.projectTargetTaskName(this.rubikMainProject)
    }

var Context.target: ContextTaskTarget
    set(value) { PublishModule.content.taskTargetContainer.taskTargets[uri] = value }
    get() = PublishModule.content.taskTargetContainer.taskTargets[uri] ?: ContextTaskTarget.doNotPublish(this)

fun Context.toDependLibMavenDependencies(
    version: String,
    dev: Boolean?
): List<Pair<String, MavenDependency>> = PublishModule.content.contextToDependLibMavenDependencies(this, version, dev)