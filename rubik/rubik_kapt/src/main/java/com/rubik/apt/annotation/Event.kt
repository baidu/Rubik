package com.rubik.apt.annotation

import com.blueprint.kotlin.pool.ElementPool
import com.rubik.annotations.context.REvent
import com.rubik.annotations.context.REventRepeatable
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.event.EventCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.lang.model.element.Element

class Event(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<Event>() {
            override val annotations = arrayOf(
                REvent::class.java,
                REventRepeatable::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (Event) -> Unit
            ) {
                if (annotation is REvent)
                    action(Event(element, annotation))
                else if (annotation is REventRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
            }

        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is REvent -> annotationUri(annotation.uri.ifBlank { annotation.context }, defaultScheme)
        else -> ""
    }

    private val msg = when (annotation) {
        is REvent -> annotation.msg
        else -> ""
    }

    private val tag = when (annotation) {
        is REvent -> annotation.tag
        else -> ""
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        invokers(elementPool) { invoker ->
            EventCodeBase(invoker, msg, tag).let { codeBase ->
                if (codeBase.msg.isNotBlank()) {
                    context(uri(arguments.defaultScheme)).events.getOrPut(codeBase.msg) { mutableListOf() }.add(codeBase)
                }
            }
        }
    }
}