package com.rubik.publish.task.component

import com.ktnail.gradle.maven.PublicationType
import com.ktnail.gradle.maven.PublishingTaskProvider
import com.ktnail.gradle.propertyOr
import com.ktnail.gradle.task.TaskGraphic
import com.ktnail.gradle.task.linkDependsOn
import com.ktnail.gradle.task.linkDependsTasks
import com.rubik.context.Ext
import com.rubik.publish.extra.publicationRecords
import com.rubik.publish.publication.Component
import com.rubik.publish.log.LogTags
import com.rubik.publish.record.updateComponent
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import java.io.File

/**
 * Task graphic of business code of contexts,
 * graph publish tasks.
 *
 * @since 1.4
 */
class VariantComponentTaskGraphic(
    project: Project,
    task: VariantComponentTask,
    publishingTaskProvider: PublishingTaskProvider,
    file: File
) : TaskGraphic() {
    private val component = Component(
        project,
        task.context,
        task.artifactName,
        task.variant,
        task.versionCreator,
        file
    )

    init {
        val publishingTasks = publishingTaskProvider.getTask(component, task.publicationType == PublicationType.DEV)

        val publishingTask = publishingTasks.publishingTask.apply {
            doLast {
                LogTags.logPublishFinish(
                    "${component.groupId}:${component.artifactId}:${component.version}", task.publicationType
                )
                if (project.propertyOr(Ext.RUBIK_AUTO_UPDATE_PUBLICATION_RECORD, true))
                    publicationRecords.updateComponent(
                        project,
                        task.context.uri,
                        task.variant.name,
                        component.version,
                        task.publicationType == PublicationType.DEV,
                        generate = true
                    )
            }
        }

        val createVersionTask = publishingTasks.createVersionTask

        val generatePomFileTask = publishingTasks.generatePomFileTask

        whenGraph {
            /*  clean <->> preBuildTask */
            if (project.propertyOr(Ext.RUBIK_CLEAN_BEFORE_TASK, true))
                task.variant.preBuildProvider.get().linkDependsOn(project.tasks.getByName(BasePlugin.CLEAN_TASK_NAME))
            /*  assembleProvider  <->>  createVersionTask  <->>  generatePomFileTask  <->>  publishingTask  <->>  startTask  */
            linkDependsTasks(
                task.variant.assembleProvider.get(),
                createVersionTask,
                generatePomFileTask,
                publishingTask,
                task.gradleTask
            )
        }
    }
}