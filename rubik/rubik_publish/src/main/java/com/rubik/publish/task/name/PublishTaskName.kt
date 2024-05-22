package com.rubik.publish.task.name

import com.rubik.context.extra.Context
import com.rubik.publish.publishTaskArtifactName


class PublishTaskName(
    val fullName: String,
    val meaning: PublishTaskNameMeaning
) {
    companion object {
        object GroupNames{
            const val LIBS_BY_NAME = "rubik-context-lib"
            const val COMPONENT_BY_NAME = "rubik-component"
            const val BY_NAME = "rubik-both"

            const val LIBS_BY_NAME_DEV = "rubik-context-lib-dev"
            const val COMPONENT_BY_NAME_DEV = "rubik-component-dev"
            const val BY_NAME_DEV = "rubik-both-dev"

            const val ALL = "rubik-all"
            fun byTag(tag: String) = "rubik-tag-${tag}"
        }

        fun fromFullName(fullName: String): PublishTaskName? {
            val meaning = PublishTaskNameMeaning.fromFullName(fullName)
            return if (null != meaning)
                PublishTaskName(fullName, meaning)
            else
                null
        }

        fun publishAll(
            publication: Publication,
            dev: Boolean = false
        ): PublishTaskName {
            return PublishTaskNameMeaning(
                action = if (dev) Action.PUBLISH_DEV else Action.PUBLISH,
                publication = publication,
                publishBy = PublishByMeaning.All()
            ).toTaskName().apply {
                group = GroupNames.ALL
            }
        }

        fun publishTag(
            publication: Publication,
            tag: String,
            dev: Boolean = false
        ): PublishTaskName {
            return PublishTaskNameMeaning(
                action = if (dev) Action.PUBLISH_DEV else Action.PUBLISH,
                publication = publication,
                publishBy = PublishByMeaning.ByTag(tag)
            ).toTaskName().apply {
                group = GroupNames.byTag(tag)
            }
        }

        fun publishPackingLink(
            publication: Publication,
            context: Context,
            dev: Boolean = false
        ): PublishTaskName {
            return PublishTaskNameMeaning(
                action = if (dev) Action.PUBLISH_DEV else Action.PUBLISH,
                publication = publication,
                publishBy = PublishByMeaning.PackingLink(context.publishTaskArtifactName)
            ).toTaskName().apply {
                group = when{
                    publication == Publication.CONTEXT_LIBS && !dev -> GroupNames.LIBS_BY_NAME
                    publication == Publication.CONTEXT_LIBS && dev -> GroupNames.LIBS_BY_NAME_DEV
                    publication == Publication.COMPONENT && !dev -> GroupNames.COMPONENT_BY_NAME
                    publication == Publication.COMPONENT && dev -> GroupNames.COMPONENT_BY_NAME_DEV
                    publication == Publication.COMPONENT_AND_CONTEXT_LIBS && !dev -> GroupNames.BY_NAME
                    publication == Publication.COMPONENT_AND_CONTEXT_LIBS && dev -> GroupNames.BY_NAME_DEV
                    else -> "rubik"
                }
            }
        }

        fun publish(
            publication: Publication,
            context: Context,
            variant: String?,
            dev: Boolean = false
        ): PublishTaskName {
            return PublishTaskNameMeaning(
                action = if (dev) Action.PUBLISH_DEV else Action.PUBLISH,
                publication = publication,
                publishBy = PublishByMeaning.ByName(context.publishTaskArtifactName, variant)
            ).toTaskName().apply {
                group = when{
                    publication == Publication.CONTEXT_LIBS && !dev -> GroupNames.LIBS_BY_NAME
                    publication == Publication.CONTEXT_LIBS && dev -> GroupNames.LIBS_BY_NAME_DEV
                    publication == Publication.COMPONENT && !dev -> GroupNames.COMPONENT_BY_NAME
                    publication == Publication.COMPONENT && dev -> GroupNames.COMPONENT_BY_NAME_DEV
                    publication == Publication.COMPONENT_AND_CONTEXT_LIBS && !dev -> GroupNames.BY_NAME
                    publication == Publication.COMPONENT_AND_CONTEXT_LIBS && dev -> GroupNames.BY_NAME_DEV
                    else -> "rubik"
                }
            }
        }

        fun assemble(
            publication: Publication,
            context: Context,
            buildType: String?
        ): PublishTaskName {
            return PublishTaskNameMeaning(
                action = Action.ASSEMBLE,
                publication = publication,
                publishBy = PublishByMeaning.ByName(context.publishTaskArtifactName, buildType)
            ).toTaskName()
        }
    }

    var group: String = "rubik"

    fun toLog(): String = "$fullName\n         -- ${meaning.toLog()}"

}
