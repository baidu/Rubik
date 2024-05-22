package com.rubik.publish.task.component

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.maven.PublicationType
import com.ktnail.gradle.maven.PublishingTaskProvider
import com.ktnail.gradle.outputAar
import com.rubik.context.extra.Context
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import com.rubik.publish.task.target.ContextTaskTarget
import org.gradle.api.Project

class VariantComponentTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    byTag: String?,
    val variant: BaseVariant,
    private val publishingTaskProvider: PublishingTaskProvider
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publish(Publication.COMPONENT, context, variant.name, publicationType == PublicationType.DEV),
    publicationType,
    byTag
) {
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            target: ContextTaskTarget,
            variant: BaseVariant,
            publishingTaskProvider: PublishingTaskProvider
        ) = VariantComponentTask(
            project,
            context,
            PublicationType.FORMAL,
            target.publishByTag, variant, publishingTaskProvider
        ) to VariantComponentTask(
            project,
            context,
            PublicationType.DEV,
            target.publishByTag, variant, publishingTaskProvider
        )
    }

    override fun onGraphic() {
        variant.outputAar?.let { output ->
            VariantComponentTaskGraphic(
                project,
                this,
                publishingTaskProvider,
                output
            ).graph()
        }
    }
}