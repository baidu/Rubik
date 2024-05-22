package com.rubik.publish.task.name

import com.ktnail.gradle.maven.PublicationType

class PublishTaskNameMeaning(
    val action: Action,
    val publication: Publication,
    val publishBy: PublishByMeaning
) {
    companion object {

        const val SEPARATOR = "-"
        private const val PREFIX = "RUBIK"

        fun fromFullName(fullName: String): PublishTaskNameMeaning? {
            val nameSplit = fullName.split(SEPARATOR)
            return if (nameSplit.size >= 4 && nameSplit[0] == PREFIX) {
                val action = Action.values().firstOrNull { action -> action.value == nameSplit[1] }
                val publication = Publication.values().firstOrNull { publication -> publication.value == nameSplit[2] }
                if (action != null && publication != null) {
                    val publishByOrName = nameSplit[3]
                    when {
                        publishByOrName == PublishBy.ALL.value -> {
                            PublishTaskNameMeaning(action, publication, PublishByMeaning.All())
                        }
                        publishByOrName.startsWith(PublishBy.BY_TAG.value) && nameSplit.size >= 5 -> {
                            val tag = nameSplit[4]
                            PublishTaskNameMeaning(action, publication, PublishByMeaning.ByTag(tag))
                        }
                        else ->{
                            val variantOrPublishBy = nameSplit.getOrNull(4)
                            if (variantOrPublishBy == PublishBy.PACKING_LINK.value)
                                PublishTaskNameMeaning(action, publication, PublishByMeaning.PackingLink(publishByOrName))
                            else
                                PublishTaskNameMeaning(action, publication, PublishByMeaning.ByName(publishByOrName, variantOrPublishBy))
                        }
                    }
                } else null
            } else null
        }
    }

    val publicationType: PublicationType
        get() = if (action == Action.PUBLISH_DEV) PublicationType.DEV else PublicationType.FORMAL

    fun toTaskName(): PublishTaskName {
        val fullName = PREFIX + SEPARATOR +
                action.value + SEPARATOR +
                publication.value + SEPARATOR +
                publishBy.toPartTaskName()
        return PublishTaskName(fullName, this)
    }

    fun toLog(): String = " NAME_MEANING : action:${action.value} publication:${publication.value} By:${publishBy.toLog()}"

}

enum class Action(val value: String) {
    ASSEMBLE("assemble"),
    PUBLISH("publish"),
    PUBLISH_DEV("publishDEV")
}

enum class Publication(val value: String) {
    CONTEXT_LIBS("ContextLibs"),
    COMPONENT("Component"),
    COMPONENT_AND_CONTEXT_LIBS("ComponentAndContextLibs");

    fun contains(other: Publication): Boolean =
        when (this) {
            COMPONENT_AND_CONTEXT_LIBS -> other.containsComponent() || other.containsContextLibs()
            else -> other == this
        }

    fun containsContextLibs(): Boolean =
        this == CONTEXT_LIBS || this == COMPONENT_AND_CONTEXT_LIBS

    fun containsComponent(): Boolean =
        this == COMPONENT || this == COMPONENT_AND_CONTEXT_LIBS
}

enum class PublishBy(val value: String) {
    ALL("All"),
    BY_TAG("ByTag"),
    BY_NAME("ByName"),
    PACKING_LINK("PackingLink")
}