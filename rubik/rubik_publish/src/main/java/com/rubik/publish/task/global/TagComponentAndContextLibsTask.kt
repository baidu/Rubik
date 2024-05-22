package com.rubik.publish.task.global

import com.ktnail.gradle.maven.PublicationType
import com.rubik.publish.task.PublishTask
import com.rubik.publish.task.name.Publication
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project

class TagComponentAndContextLibsTask(
    project: Project,
    tag: String,
    publicationType: PublicationType
) : PublishTask(
    project,
    PublishTaskName.publishTag(Publication.COMPONENT_AND_CONTEXT_LIBS, tag, publicationType == PublicationType.DEV),
    publicationType
)