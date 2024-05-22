package com.rubik.publish.task.both

import com.ktnail.gradle.maven.PublicationType
import com.rubik.context.extra.Context
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.PublishTaskPair
import com.rubik.publish.task.component.ComponentTask
import com.rubik.publish.task.lib.ContextLibTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import com.rubik.publish.task.target.ContextTaskTarget
import org.gradle.api.Project

class ComponentAndContextLibsTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    byTag: String?,
    val libTask: ContextLibTask,
    val componentTask: ComponentTask
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publish(Publication.COMPONENT_AND_CONTEXT_LIBS, context, null, publicationType == PublicationType.DEV),
    publicationType,
    byTag
) {
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            target: ContextTaskTarget,
            libTasks: PublishTaskPair<ContextLibTask>,
            componentTasks: PublishTaskPair<ComponentTask>
        ) = ComponentAndContextLibsTask(
            project, context, PublicationType.FORMAL,
            target.publishByTag,
            libTasks.first,
            componentTasks.first
        ) to ComponentAndContextLibsTask(
            project, context, PublicationType.DEV,
            target.publishByTag,
            libTasks.second,
            componentTasks.second
        )
    }

    override fun onGraphic() {
        ComponentAndContextLibsTaskGraphic(this).graph()
    }
}