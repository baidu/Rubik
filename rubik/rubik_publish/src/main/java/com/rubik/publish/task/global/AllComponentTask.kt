package com.rubik.publish.task.global

import com.ktnail.gradle.maven.PublicationType
import com.rubik.publish.task.PublishTask
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project
import com.rubik.publish.task.name.Publication

class AllComponentTask(
    project: Project,
    publicationType: PublicationType
) : PublishTask(
    project,
    PublishTaskName.publishAll(Publication.COMPONENT, publicationType == PublicationType.DEV),
    publicationType
)