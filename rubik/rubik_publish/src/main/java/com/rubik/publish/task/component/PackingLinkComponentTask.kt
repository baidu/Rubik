package com.rubik.publish.task.component

import com.ktnail.gradle.maven.PublicationType
import com.rubik.context.extra.Context
import com.rubik.publish.extra.publishTasksContainer
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.PublishTaskPair
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

class PackingLinkComponentTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    val componentTask: ComponentTask
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publishPackingLink(Publication.COMPONENT, componentTask.context, publicationType == PublicationType.DEV),
    publicationType,
    null
) {
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            componentTasks: PublishTaskPair<ComponentTask>
        ) = PackingLinkComponentTask(
            project,
            context,
            PublicationType.FORMAL,
            componentTasks.first
        ) to PackingLinkComponentTask(
            project,
            context,
            PublicationType.DEV,
            componentTasks.second
        )
    }

    override fun onGraphic() {
        linkExecuteDependsOn(componentTask)
        publishTasksContainer.listenTasksRegistered(
            context.whoPackingMe.map { context -> context.uri }
        ) { packingMeTaskGroup ->
            if (packingMeTaskGroup.context?.whoPackingMe.isNullOrEmpty()) {
                if (publicationType == PublicationType.FORMAL)
                    packingMeTaskGroup.componentTasks?.first
                else packingMeTaskGroup.componentTasks?.second
            } else {
                if (publicationType == PublicationType.FORMAL)
                    packingMeTaskGroup.packingLinkComponentTasks?.first
                else packingMeTaskGroup.packingLinkComponentTasks?.second
            }?.let { depTask ->
                componentTask.linkExecuteDependsOn(depTask)
            }
        }
    }
}
