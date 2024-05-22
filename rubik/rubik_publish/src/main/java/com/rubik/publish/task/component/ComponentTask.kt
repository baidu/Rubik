package com.rubik.publish.task.component

import com.ktnail.gradle.maven.PublicationType
import com.rubik.context.extra.Context
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import com.rubik.publish.task.target.ContextTaskTarget
import org.gradle.api.Project

class ComponentTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    byTag: String?
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publish(Publication.COMPONENT, context, null,publicationType == PublicationType.DEV),
    publicationType,
    byTag
) {
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            target: ContextTaskTarget
        ) = ComponentTask(
            project,
            context,
            PublicationType.FORMAL,
            target.publishByTag
        ) to ComponentTask(
            project,
            context,
            PublicationType.DEV,
            target.publishByTag
        )
    }

    val variantTasks: MutableList<VariantComponentTask> = mutableListOf()

    fun addVariantTask(task: VariantComponentTask) {
        variantTasks.add(task)
    }

    override fun onGraphic() {
        ComponentTaskGraphic(this).graph()
    }
}