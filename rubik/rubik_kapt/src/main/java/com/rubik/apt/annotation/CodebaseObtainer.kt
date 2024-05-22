package com.rubik.apt.annotation

import com.blueprint.kotlin.pool.ElementPool
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

abstract class CodebaseObtainer<T : AnnotationData> {
    abstract val annotations: Array<Class<out Annotation>>

    abstract fun onObtain(element: Element, annotation: Annotation, action: (T) -> Unit)

    private fun annotationData(environment: RoundEnvironment, action: (T) -> Unit) {
        environment.elementsAndAnnotations(*annotations) { element, annotation ->
            onObtain(element, annotation) { data ->
                action(data)
            }
        }
    }

    fun addToContext(
        environment: RoundEnvironment,
        elementPool: ElementPool,
        arguments: PluginArguments,
        context: (String) -> ContextCodeBase
    ) {
        annotationData(environment) { data ->
            data.addToCodebase(context, elementPool, arguments)
        }
    }
}