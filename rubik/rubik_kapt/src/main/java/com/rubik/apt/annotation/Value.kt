package com.rubik.apt.annotation

import com.blueprint.kotlin.lang.utility.toKbpClassElement
import com.blueprint.kotlin.lang.utility.toKbpConstructorElement
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.annotations.route.RValue
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.value.ValueCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class Value(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<Value>() {
            override val annotations = arrayOf<Class<out Annotation>>(
                RValue::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (Value) -> Unit
            ) {
                if (annotation is RValue) action(Value(element, annotation))
            }

        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is RValue -> annotationUri(annotation.uri.ifBlank { annotation.context }, defaultScheme)
        else -> ""
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        if (element is TypeElement) {
            element.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                ValueCodeBase(
                    kbpClassElement,
                    ignoreAnnotations = arguments.ignoreValueAnnos
                )?.let { codebase ->
                    context(uri(arguments.defaultScheme)).values.add(codebase)
                }
            }
        } else if (element is ExecutableElement) {
            element.toKbpConstructorElement(elementPool)?.let { kbpConstructorElement ->
                (kbpConstructorElement.jmElement?.enclosingElement as? TypeElement)?.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                    ValueCodeBase(
                        kbpClassElement,
                        kbpConstructorElement,
                        arguments.ignoreValueAnnos
                    )?.let { codebase ->
                        context(uri(arguments.defaultScheme)).values.add(codebase)
                    }
                }
            }
        }
    }
}