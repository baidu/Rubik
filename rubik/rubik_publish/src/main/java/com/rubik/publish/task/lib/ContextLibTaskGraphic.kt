package com.rubik.publish.task.lib

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.maven.PublicationType
import com.ktnail.gradle.maven.PublishingTaskProvider
import com.ktnail.gradle.propertyOr
import com.ktnail.gradle.task.TaskGraphic
import com.ktnail.gradle.task.bindState
import com.ktnail.gradle.task.linkDependsOn
import com.ktnail.gradle.task.linkDependsTasks
import com.rubik.context.extra.Context
import com.rubik.context.Ext
import com.rubik.context.publication.LibType
import com.rubik.publish.extra.getKaptTask
import com.rubik.publish.extra.publicationRecords
import com.rubik.publish.exception.RubikNoKaptTaskFoundException
import com.rubik.publish.log.LogTags
import com.rubik.publish.publication.Lib
import com.rubik.publish.publication.getJarTask
import com.rubik.publish.record.updateContextLibs
import com.rubik.publish.task.lib.state.ContextLibStateMachine
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.BasePlugin
import java.io.File

/**
 * Task graphic of context libs,
 * graph assemble task、publish tasks、compile task & jar task.
 *
 * @since 1.4
 */
class ContextLibTaskGraphic(
    private val project: Project,
    private val libTask: ContextLibTask,
    private val publishingTaskProvider: PublishingTaskProvider,
    private val context: Context
) : TaskGraphic() {

    private val stateMachine = ContextLibStateMachine(project)

    fun graph(variant: BaseVariant, compilerVariant: BaseVariant?, libTmpDirRoot: File) {

        libTask.assembleTask.gradleTask.bindState(stateMachine, ContextLibStateMachine.States.ASSEMBLE_FINISH)
        libTask.gradleTask.bindState(stateMachine, ContextLibStateMachine.States.PUBLISH_FINISH)

        graphLib(
            Lib(
                context,
                project,
                LibType.CONTEXT,
                libTask.artifactName,
                variant,
                libTask.versionCreator,
                libTmpDirRoot
            ), compilerVariant
        )

        if (context.source.publishOriginalValue) {
            graphLib(
                Lib(
                    context,
                    project,
                    LibType.ORIGINAL_VALUE,
                    libTask.artifactName,
                    variant,
                    libTask.versionCreator,
                    libTmpDirRoot
                ), compilerVariant
            )
        }
        super.graph()
    }

    private fun graphLib(lib: Lib, compilerVariant: BaseVariant?) {
        val cleanTask = project.tasks.getByName(BasePlugin.CLEAN_TASK_NAME)

        whenGraph {
            /*  clean <->> preBuildTask  */
            val preBuildTask: Task = (compilerVariant?.preBuildProvider ?: lib.variant.preBuildProvider).get()
            if (project.properties[Ext.RUBIK_CLEAN_BEFORE_TASK] != false)
                preBuildTask.linkDependsOn(cleanTask)
        }

        val sourceTask = lib.getCopySourceTask(project)
        val jarTask = lib.getJarTask(project)
        val jarSourceTask = lib.source?.getJarSourceTask(project)

        if (null != compilerVariant) {
            graphByCompiler(lib, compilerVariant, jarTask, sourceTask, jarSourceTask)
        } else {
            graphByTransform(lib, jarTask, sourceTask, jarSourceTask)
        }

        val publishingTasks = publishingTaskProvider.getTask(lib, libTask.publicationType == PublicationType.DEV)

        val publishingTask = publishingTasks.publishingTask.apply {
            doLast {
                LogTags.logPublishFinish(
                    "${lib.groupId}:${lib.artifactId}:${lib.version}", libTask.publicationType
                )
                if (project.propertyOr(Ext.RUBIK_AUTO_UPDATE_PUBLICATION_RECORD, true))
                    publicationRecords.updateContextLibs(
                        project,
                        context.uri,
                        lib.version,
                        libTask.publicationType == PublicationType.DEV,
                        generate = true
                    )


            }
        }

        val createVersionTask = publishingTasks.createVersionTask

        val generatePomFileTask = publishingTasks.generatePomFileTask

        whenGraph {
            /*
             * assembleTask    <->>  createVersionTask  <->>  generatePomFileTask  <->>  publishingTask  <->>  startTask
             *     ^                                                                                               ^
             */
            linkDependsTasks(
                libTask.assembleTask.gradleTask,
                createVersionTask,
                generatePomFileTask,
                publishingTask.bindState(
                    stateMachine,
                    ContextLibStateMachine.States.PUBLISHING
                ),
                libTask.gradleTask
            )

        }
    }

    private fun graphByTransform(
        lib: Lib,
        jarTask: Task,
        sourceTask: Task?,
        jarSourceTask: Task?
    ){
        whenGraph {
            /*
             * assembleTask           <->>           jarTask    <->> assembleLibTask
             * assembleTask <->>  sourceTask <->> jarSourceTask <->> assembleLibTask
             *                                                             ^
             */
            val assembleTask = lib.variant.assembleProvider.get()
            linkDependsTasks(assembleTask, jarTask, libTask.assembleTask.gradleTask)

            if (null != sourceTask && null != jarSourceTask) {
                linkDependsTasks(
                    assembleTask.bindState(stateMachine, ContextLibStateMachine.States.ByTransform.ASSEMBLE_AND_TRANSFORM),
                    sourceTask,
                    jarSourceTask.bindState(stateMachine, ContextLibStateMachine.States.ByTransform.PACKING_JAR),
                    libTask.assembleTask.gradleTask
                )
            }
        }
    }

    private fun graphByCompiler(
        lib: Lib,
        compilerVariant: BaseVariant,
        jarTask: Task,
        sourceTask: Task,
        jarSourceTask: Task?
    ) {
        whenGraph {
            /*
             * kaptTask <->>  sourceTask
             *
             * sourceTask  <->> compileTask  <->>  jarTask   <->>  assembleLibTask
             * sourceTask          <->>       jarSourceTask  <->>  assembleLibTask
             *                                                            ^
             */
            val compileTask = lib.getKotlinCompileTask(project, compilerVariant)

            val kaptTask = (project.getKaptTask(lib.variant.name) ?: throw RubikNoKaptTaskFoundException(lib.variant.name))

            sourceTask.linkDependsOn(
                kaptTask.bindState(stateMachine, ContextLibStateMachine.States.ByCompiler.ANNOTATION_PROCESSING)
            )

            linkDependsTasks(
                sourceTask.bindState(stateMachine, ContextLibStateMachine.States.ByCompiler.MOVING_SOURCE),
                compileTask.bindState(stateMachine, ContextLibStateMachine.States.ByCompiler.COMPILING),
                jarTask.bindState(stateMachine, ContextLibStateMachine.States.ByCompiler.PACKING_JAR),
                libTask.assembleTask.gradleTask
            )

            jarSourceTask?.let {
                linkDependsTasks(sourceTask, jarSourceTask, libTask.assembleTask.gradleTask)
            }
        }
    }
}