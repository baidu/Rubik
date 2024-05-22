package com.rubik.publish.task.both

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.maven.PublicationType
import com.rubik.context.extra.Context
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.PublishTaskPair
import com.rubik.publish.task.component.VariantComponentTask
import com.rubik.publish.task.lib.ContextLibTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import com.rubik.publish.task.target.ContextTaskTarget
import org.gradle.api.Project

class VariantComponentAndContextLibsTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    byTag: String?,
    val variant: BaseVariant,
    val libTask: ContextLibTask,
    val componentTask: VariantComponentTask
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publish(Publication.COMPONENT_AND_CONTEXT_LIBS, context, variant.name, publicationType == PublicationType.DEV),
    publicationType,
    byTag
) {
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            target: ContextTaskTarget,
            variant: BaseVariant,
            libTasks: PublishTaskPair<ContextLibTask>,
            componentTasks: PublishTaskPair<VariantComponentTask>
            ) = VariantComponentAndContextLibsTask(
            project, context, PublicationType.FORMAL,
            target.publishByTag, variant,
            libTasks.first,
            componentTasks.first
        ) to VariantComponentAndContextLibsTask(
            project, context, PublicationType.DEV,
            target.publishByTag, variant,
            libTasks.second,
            componentTasks.second
        )
    }

    override fun onGraphic() {
        VariantComponentAndContextLibsTaskGraphic(this).graph()
    }
}