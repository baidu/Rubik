package com.rubik.apt.annotation

import com.blueprint.kotlin.lang.utility.toKbpClassElement
import com.blueprint.kotlin.lang.utility.toKbpConstructorElement
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.annotations.route.RObject
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.objekt.ObjectCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class Object(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<Object>() {
            override val annotations = arrayOf<Class<out Annotation>>(
                RObject::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (Object) -> Unit
            ) {
                if (annotation is RObject) action(Object(element, annotation))
            }

        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is RObject -> annotationUri(annotation.uri, defaultScheme)
        else -> ""
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        if (element is TypeElement) {
            element.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                ObjectCodeBase(kbpClassElement)?.let { codebase ->
                    context(uri(arguments.defaultScheme)).addObject(codebase)
                }
            }
        } else if (element is ExecutableElement) {
            element.toKbpConstructorElement(elementPool)?.let { kbpConstructorElement ->
                (kbpConstructorElement.jmElement?.enclosingElement as? TypeElement)?.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                    ObjectCodeBase( kbpClassElement)?.let { codebase ->
                        context(uri(arguments.defaultScheme)).addObject(codebase)
                    }
                }
            }
        }

    }
}