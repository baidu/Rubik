package com.rubik.publish.task.both

import com.ktnail.gradle.maven.PublicationType
import com.rubik.context.extra.Context
import com.rubik.publish.extra.publishTasksContainer
import com.rubik.publish.task.PublishContextTask
import com.rubik.publish.task.PublishTaskPair
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

class PackingLinkComponentAndContextLibsTask(
    project: Project,
    context: Context,
    publicationType: PublicationType,
    private val bothTask: PublishContextTask
) : PublishContextTask(
    project,
    context,
    PublishTaskName.publishPackingLink(Publication.COMPONENT_AND_CONTEXT_LIBS, bothTask.context, publicationType == PublicationType.DEV),
    publicationType,
    null
){
    companion object {
        operator fun invoke(
            project: Project,
            context: Context,
            bothTasks: PublishTaskPair<PublishContextTask>
        ) = PackingLinkComponentAndContextLibsTask(
            project,
            context,
            PublicationType.FORMAL,
            bothTasks.first
        ) to PackingLinkComponentAndContextLibsTask(
            project,
            context,
            PublicationType.DEV,
            bothTasks.second
        )
    }

    override fun onGraphic() {
        linkExecuteDependsOn(bothTask)
        publishTasksContainer.listenTasksRegistered(
            context.whoPackingMe.map { context -> context.uri }
        ) { packingMeTaskGroup ->
            if (packingMeTaskGroup.context?.whoPackingMe.isNullOrEmpty()) {
                if (publicationType == PublicationType.FORMAL)
                    packingMeTaskGroup.bothTasks?.first
                else packingMeTaskGroup.bothTasks?.second
            } else {
                if (publicationType == PublicationType.FORMAL)
                    packingMeTaskGroup.packingLinkBothTasks?.first
                else packingMeTaskGroup.packingLinkBothTasks?.second
            }?.let { depTask ->
                bothTask.linkExecuteDependsOn(depTask)
            }
        }
    }
}