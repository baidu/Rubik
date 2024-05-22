package com.rubik.publish.task.lib

import com.ktnail.gradle.firstVariant
import com.ktnail.gradle.maven.PublicationType
import com.ktnail.gradle.maven.PublishingTaskProvider
import com.rubik.context.extra.Context
import com.rubik.publish.extra.rubikTask
import com.rubik.publish.buildTypeName
import com.rubik.publish.compilerBuildTypeName
import com.rubik.publish.useResetCompiler
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import com.rubik.publish.task.target.ContextTaskTarget
import org.gradle.api.Project
import java.io.File

class ContextLibTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    byTag: String?,
    val assembleTask: AssembleTask,
    private val publishingTaskProvider: PublishingTaskProvider,
    private val libTmpDirRoot: File
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publish(Publication.CONTEXT_LIBS, context, null, publicationType == PublicationType.DEV),
    publicationType,
    byTag
) {
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            target: ContextTaskTarget,
            assembleTask: AssembleTask,
            publishingTaskProvider: PublishingTaskProvider,
            libTmpDirRoot: File
        ) = (ContextLibTask(
            project,
            context,
            PublicationType.FORMAL,
            target.publishByTag,
            assembleTask,
            publishingTaskProvider,
            libTmpDirRoot
        ) to ContextLibTask(
            project,
            context,
            PublicationType.DEV,
            target.publishByTag,
            assembleTask,
            publishingTaskProvider,
            libTmpDirRoot
        ))
    }

    override val startTaskNames =
        if (publicationType == PublicationType.FORMAL) arrayOf(assembleTask.name, name)
        else arrayOf(name)

    override fun onGraphic() {
        val graphic = ContextLibTaskGraphic(
            project,
            this,
            publishingTaskProvider,
            context
        )
        project.firstVariant(context.buildTypeName()) { variant ->
            if (context.useResetCompiler) {
                project.firstVariant(context.compilerBuildTypeName()) { compilerVariant ->
                    graphic.graph(variant, compilerVariant, libTmpDirRoot)
                }
            } else {
                graphic.graph(variant, null, libTmpDirRoot)
            }
        }
    }

    class AssembleTask(
        project: Project,
        context: Context
    ) {
        val name = PublishTaskName.assemble(Publication.CONTEXT_LIBS, context, null)
        val gradleTask = project.rubikTask(name)
    }
}

