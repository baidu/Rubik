package com.rubik.apt.annotation

import com.blueprint.kotlin.pool.ElementPool
import com.rubik.annotations.context.assist.REventAssist
import com.rubik.annotations.context.assist.REventAssistRepeatable
import com.rubik.annotations.context.instance.REventInstance
import com.rubik.annotations.context.instance.REventInstanceRepeatable
import com.rubik.annotations.route.RInstance
import com.rubik.annotations.route.RInstanceRepeatable
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.event.EventInstanceCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.lang.model.element.Element

class EventInstance(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<EventInstance>() {
            override val annotations = arrayOf(
                RInstance::class.java,
                REventInstance::class.java,
                REventAssist::class.java,
                RInstanceRepeatable::class.java,
                REventInstanceRepeatable::class.java,
                REventAssistRepeatable::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (EventInstance) -> Unit
            ) {
                if (annotation is RInstance && annotation.provideForTag.isNotBlank() || annotation is REventInstance || annotation is REventAssist)
                    action(EventInstance(element, annotation))
                else if (annotation is RInstanceRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
                else if (annotation is REventInstanceRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
                else if (annotation is REventAssistRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
            }

        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is RInstance -> annotationUri(annotation.uri, defaultScheme)
        is REventInstance -> annotationUri(annotation.uri, defaultScheme)
        is REventAssist -> annotationUri(annotation.uri.ifBlank { annotation.context }, defaultScheme)
        else -> ""
    }

    private val forTag = when (annotation) {
        is RInstance -> annotation.provideForTag
        is REventInstance -> annotation.provideForTag
        is REventAssist -> annotation.assistForTag
        else -> ""
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        invokers(elementPool) { invoker ->
            EventInstanceCodeBase(invoker, forTag).let { codeBase ->
                if (codeBase.forTag.isNotBlank()) {
                    context(uri(arguments.defaultScheme)).addEventInstance(codeBase)
                }
            }
        }
    }
}