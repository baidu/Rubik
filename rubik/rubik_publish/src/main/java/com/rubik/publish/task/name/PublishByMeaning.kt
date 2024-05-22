package com.rubik.publish.task.name

sealed class PublishByMeaning {
    abstract fun toPartTaskName(): String

    abstract fun toLog(): String

    /**
    task name simple :
    RUBIK-publish-ContextLibs-All
     */
    class All : PublishByMeaning() {
        override fun toPartTaskName() = PublishBy.ALL.value
        override fun toLog(): String = "ALL"
    }

    /**
    task name simple :
    RUBIK-publish-ContextLibs-ByTag-tag1
     */
    class ByTag(val tag: String) : PublishByMeaning() {
        override fun toPartTaskName() = PublishBy.BY_TAG.value + PublishTaskNameMeaning.SEPARATOR + tag
        override fun toLog(): String = "TAG(${tag})"
    }

    /**
    task name simple :
    RUBIK-publish-ContextLibs-myname
    RUBIK-PUBLISH-Component-myname-variant
     */
    open class ByName(val name: String, val variant: String?) : PublishByMeaning() {
        override fun toPartTaskName() = if (null != variant) name + PublishTaskNameMeaning.SEPARATOR + variant else name
        override fun toLog(): String = "NAME(${name}) variant($variant)"
    }

    /**
    task name simple :
    RUBIK-publish-Component-myname-PackingLink
     */
    class PackingLink(name: String) : ByName(name, null) {
        override fun toPartTaskName() = name + PublishTaskNameMeaning.SEPARATOR + PublishBy.PACKING_LINK.value
        override fun toLog(): String = "PackingLink(${name})"
    }

}
