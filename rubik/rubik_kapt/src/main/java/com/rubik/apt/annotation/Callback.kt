package com.rubik.apt.annotation

import com.blueprint.kotlin.lang.utility.toKbpClassElement
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.annotations.route.RCallback
import com.rubik.apt.codebase.callback.ObjectCallbackCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class Callback(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<Callback>() {
            override val annotations = arrayOf<Class<out Annotation>>(
                RCallback::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (Callback) -> Unit
            ) {
                if (annotation is RCallback) action(Callback(element, annotation))
            }

        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is RCallback -> annotationUri(annotation.uri, defaultScheme)
        else -> ""
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        if (element is TypeElement) {
            element.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                ObjectCallbackCodeBase(elementPool, kbpClassElement)?.let { codebase ->
                    context(uri(arguments.defaultScheme)).addCallback(codebase)
                }
            }
        }
    }
}