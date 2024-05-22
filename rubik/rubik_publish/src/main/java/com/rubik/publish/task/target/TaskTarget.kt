package com.rubik.publish.task.target

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.maven.PublicationType
import com.rubik.context.extra.Context
import com.rubik.publish.task.name.PublishByMeaning
import com.rubik.publish.task.name.PublishTaskName

sealed class ContextTaskTarget {
    companion object {
        fun doNotPublish(
            context: Context
        ): ContextTaskTarget = DoNotPublishContextTarget(context)

        fun publish(
            context: Context,
            targetTaskName: PublishTaskName,
            publishByTag: String?
        ): ContextTaskTarget = PublishContextTarget(context, targetTaskName, publishByTag)
    }

    abstract val context: Context

    abstract val targetTaskName: PublishTaskName?

    abstract val publishingContextLibs: Boolean

    abstract val publicationType: PublicationType?

    abstract val publishingComponent: Boolean

    abstract val publishByTag: String?

    val publishingSth
        get() = publishingContextLibs || publishingComponent

    val publishingBoth
        get() = publishingContextLibs && publishingComponent

    val publishingContextLibsOnly
        get() = publishingContextLibs && !publishingComponent

    val publishingComponentOnly
        get() = !publishingContextLibs && publishingComponent

    val publishingDev
        get() = publicationType == PublicationType.DEV

    fun publishingVariant(variant: BaseVariant) =
        publishingSth && (targetTaskName?.meaning?.publishBy as? PublishByMeaning.ByName)?.variant == variant.name

    class PublishContextTarget(
        override val context: Context,
        override val targetTaskName: PublishTaskName,
        override val publishByTag: String?
    ) : ContextTaskTarget() {
        override val publishingContextLibs: Boolean
            get() = targetTaskName.meaning.publication.containsContextLibs() && context.enableProvideRoute

        override val publishingComponent: Boolean
            get() = targetTaskName.meaning.publication.containsComponent() && context.enablePublishComponent

        override val publicationType: PublicationType
            get() = targetTaskName.meaning.publicationType

        val devWhenPublishingComponent
            get() = if (publishingComponent && publicationType == PublicationType.FORMAL) false else null

    }

    class DoNotPublishContextTarget(
        override val context: Context
    ) : ContextTaskTarget() {
        override val targetTaskName: PublishTaskName? = null

        override val publicationType: PublicationType? = null

        override val publishingContextLibs: Boolean = false

        override val publishingComponent: Boolean = false

        override val publishByTag: String? = null
    }

    fun toLog(): String = " ContextLibs:[${publishingContextLibs}] Component:[${publishingComponent}] $publicationType"

}

