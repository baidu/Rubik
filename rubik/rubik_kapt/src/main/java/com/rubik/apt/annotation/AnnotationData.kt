package com.rubik.apt.annotation

import com.blueprint.kotlin.lang.element.KbpConstructorElement
import com.blueprint.kotlin.lang.utility.toKbpElement
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase.Companion.toInvokeOriginalCodeBases
import com.rubik.apt.codebase.value.ValueCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

abstract class AnnotationData(val element: Element, val annotation: Annotation) {
    abstract fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    )

    fun invokers(
        elementPool: ElementPool,
        action: (InvokeOriginalCodeBase) -> Unit
    ) = element.toKbpElement(elementPool)?.toInvokeOriginalCodeBases(elementPool)?.forEach { codebase ->
        action(codebase)
    }
}

fun annotationUri(uri: String, defaultScheme: String?) =
    if (uri.contains("://") || uri.isBlank() || defaultScheme.isNullOrBlank()) uri else "$defaultScheme://$uri"

fun RoundEnvironment.elementsAndAnnotations(
    vararg annotations: Class<out Annotation>,
    action: (Element, Annotation) -> Unit
) {
    annotations.forEach { clazz ->
        elementsAndAnnotation(clazz) { element, annotation ->
            action(element, annotation)
        }
    }
}

fun <T : Annotation> RoundEnvironment.elementsAndAnnotation(
    annotation: Class<T>,
    action: (Element, T) -> Unit
) {
    getElementsAnnotatedWith(annotation)?.forEach { element ->
        action(element, element.getAnnotation(annotation))
    }
}
