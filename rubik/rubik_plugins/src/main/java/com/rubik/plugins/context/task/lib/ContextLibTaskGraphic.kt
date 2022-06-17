package com.rubik.plugins.context.task.lib

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikNoKaptTaskFoundException
import com.rubik.plugins.basic.publish.maven.PublishingTaskProvider
import com.rubik.plugins.basic.task.TaskGraphic
import com.rubik.plugins.basic.task.doLog
import com.rubik.plugins.basic.task.graphicDependsOn
import com.rubik.plugins.basic.task.graphicFinalizedWith
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.context.model.Lib
import com.rubik.plugins.context.task.provider.getRedirectDestinationTask
import com.rubik.plugins.extension.context.ContextExtension
import org.gradle.api.Project
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
    libTasks: ContextLibTasks,
    private val publishingTaskProvider: PublishingTaskProvider,
    private val context: ContextExtension
) : TaskGraphic() {

    private val assembleLibTask = libTasks.assembleTask
    private val publishLibTask = libTasks.publishTask
    private val publishDevLibTask = libTasks.publishDevTask

    fun graph(variant: BaseVariant, libTmpDirRoot: File) {
        whenGraph {
            /*
                clean <>> preBuildProvider
                               ^

                     ( kaptTask    <>>  assembleLibTask )
                                        assembleLibTask  <>>  publishDevLibTask
                (assembleProvider) <>>  assembleLibTask  <>>   publishLibTask
                                             ^                      ^
             */
            if(project.properties[Ext.RUBIK_CLEAN_BEFORE_TASK] != false)
                variant.preBuildProvider.get().doLog().graphicDependsOn(project.tasks.getByName(BasePlugin.CLEAN_TASK_NAME).doLog())

            if (!context.useResetCompiler) {
                assembleLibTask.graphicDependsOn(variant.assembleProvider.get().doLog())
            } else {
                assembleLibTask.graphicDependsOn((project.getKaptTask(variant.name) ?: throw RubikNoKaptTaskFoundException(
                    variant.name
                )).doLog())
            }

            publishLibTask?.graphicDependsOn(assembleLibTask)
            publishDevLibTask?.graphicDependsOn(assembleLibTask)
        }

        project.rubikConfigExtension.registerLibTask(
            assembleLibTask,
            publishLibTask,
            publishDevLibTask
        )

        createLibTasksAndGraph(Lib(context, project, Lib.Type.CONTEXT, variant, libTmpDirRoot))
        if (context.publishOriginalValue) {
            createLibTasksAndGraph(Lib(context, project, Lib.Type.ORIGINAL_VALUE, variant, libTmpDirRoot))
        }
        super.graph()
    }

    private fun createLibTasksAndGraph(lib: Lib) {
        val sourceTask = lib.getSourceTask(project)?.doLog()
        val jarTask = lib.getJarTask(project).doLog()
        val compileTask = lib.getCompileTask(project)?.apply { doLog() }
        val jarSourceTask = lib.getJarSourceTask(project)?.doLog()

        val publishingTask =
            if (null != publishLibTask) publishingTaskProvider.getTask(lib).doLog().apply {
                doLast {
                    Logger.p(LogTags.PUBLISH_CONTEXT_LIBS,null) { " <${lib.groupId}:${lib.artifactId}:${lib.version}>  SUCCEEDED !!!" }
                }
            } else null

        val publishingDevTask =
            if (null != publishDevLibTask) publishingTaskProvider.getTask(lib, true).doLog().apply {
                doLast {
                    Logger.p(LogTags.PUBLISH_CONTEXT_LIBS,null) { " <${lib.groupId}:${lib.artifactId}:${lib.version}>  DEV SUCCEEDED !!!" }
                }
            } else null

        whenGraph {
            /*
                ( kaptTask <>>  sourceTask   <>>  assembleLibTask )
                                                  assembleLibTask  >>>                                  jarTask
                                                                   ( redirectTask <>> compileTask  <>>  jarTask )
                                                ( assembleLibTask  >>>                               jarSourceTask <>>                           publishingTask )
                                                ( assembleLibTask  >>>                               jarSourceTask <>>                         publishingDevTask )
                                                                                                        jarTask  <>>                             publishingTask
                                                                                                        jarTask  <>>                           publishingDevTask
                                                  assembleLibTask  <>>                                                                           publishingTask
                                                  assembleLibTask  <>>                                                                         publishingDevTask
                                                                                                                         publishLibTask   >>>    publishingTask
                                                                                                                         publishLibTask   >>>  publishingDevTask
                          (assembleProvider) <>>  assembleLibTask  <>>                                                   publishLibTask
                                                          ^                                                                     ^
            */
            sourceTask?.let {
                assembleLibTask.graphicDependsOn(sourceTask)
                sourceTask.graphicDependsOn(
                    project.getKaptTask(lib.variant.name) ?: throw RubikNoKaptTaskFoundException(lib.variant.name)
                )
            }
            assembleLibTask.graphicFinalizedWith(jarTask)
            compileTask?.let {
                jarTask.graphicDependsOn(compileTask)
                compileTask.graphicDependsOn(compileTask.getRedirectDestinationTask(lib).doLog())
            }
            jarSourceTask?.let {
                assembleLibTask.graphicFinalizedWith(jarSourceTask)
                publishingTask?.graphicDependsOn(jarSourceTask)
                publishingDevTask?.graphicDependsOn(jarSourceTask)
            }
            if (null != publishLibTask && null != publishingTask) {
                publishingTask.graphicDependsOn(jarTask)
                publishLibTask.graphicFinalizedWith(publishingTask)
            }
            if (null != publishDevLibTask && null != publishingDevTask) {
                publishingDevTask.graphicDependsOn(jarTask)
                publishDevLibTask.graphicFinalizedWith(publishingDevTask)
            }
        }
    }
}