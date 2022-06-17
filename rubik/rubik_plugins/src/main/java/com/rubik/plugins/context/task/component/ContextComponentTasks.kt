package com.rubik.plugins.context.task.component

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.toCamel
import com.ktnail.x.uriToPascal
import com.rubik.plugins.context.ContextPlugin
import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.basic.publish.maven.PublicationType
import com.rubik.plugins.basic.utility.isTaskTarget
import com.rubik.plugins.basic.utility.rubikComponentDevTask
import com.rubik.plugins.basic.utility.rubikComponentTask
import org.gradle.api.Project

class ContextComponentTasks(
    project: Project,
    val context: ContextExtension,
    val variant: BaseVariant
) {

    companion object {
        fun create(
            project: Project,
            context: ContextExtension,
            variant: BaseVariant
        ): ContextComponentTasks? =
            if (context.enablePublishComponent)
                ContextComponentTasks(project, context, variant)
            else
                null
    }

    val publishTask = if(context.enablePublish) project.rubikComponentTask(toCamel(ContextPlugin.PUBLISH_TASK_NAME_PREFIX, context.publishArtifactName.uriToPascal(), variant.name, ContextPlugin.PUBLISH_COMPONENT_TASK_NAME_SUFFIX)) else null
    val publishDevTask = if(context.enablePublishDev) project.rubikComponentDevTask(toCamel(ContextPlugin.PUBLISH_DEV_TASK_NAME_PREFIX, context.publishArtifactName.uriToPascal(), variant.name, ContextPlugin.PUBLISH_COMPONENT_TASK_NAME_SUFFIX)) else null

    fun configuringPublicationType(project: Project): PublicationType? {
        return when {
            publishTask?.name?.let { name -> project.isTaskTarget(name) } == true -> PublicationType.FORMAL
            publishDevTask?.name?.let { name -> project.isTaskTarget(name) } == true -> PublicationType.DEV
            else -> null
        }
    }
}