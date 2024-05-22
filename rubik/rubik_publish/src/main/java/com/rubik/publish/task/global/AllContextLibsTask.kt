package com.rubik.publish.task.global

import com.ktnail.gradle.maven.PublicationType
import com.rubik.publish.task.PublishTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

class AllContextLibsTask(
    project: Project,
    publicationType: PublicationType
) : PublishTask(
    project,
    PublishTaskName.publishAll(Publication.CONTEXT_LIBS, publicationType == PublicationType.DEV),
    publicationType
)