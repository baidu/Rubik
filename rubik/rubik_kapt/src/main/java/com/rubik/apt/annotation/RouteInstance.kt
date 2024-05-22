package com.rubik.apt.annotation

import com.blueprint.kotlin.pool.ElementPool
import com.rubik.annotations.route.RInstance
import com.rubik.annotations.route.RInstanceRepeatable
import com.rubik.annotations.route.assist.RRouteAssist
import com.rubik.annotations.route.assist.RRouteAssistRepeatable
import com.rubik.annotations.route.instance.RRouteInstance
import com.rubik.annotations.route.instance.RRouteInstanceRepeatable
import com.rubik.apt.codebase.api.ApiInstanceCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.plugin.PluginArguments
import javax.lang.model.element.Element

class RouteInstance(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<RouteInstance>() {
            override val annotations = arrayOf(
                RInstance::class.java,
                RRouteInstance::class.java,
                RRouteAssist::class.java,
                RInstanceRepeatable::class.java,
                RRouteInstanceRepeatable::class.java,
                RRouteAssistRepeatable::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (RouteInstance) -> Unit
            ) {
                if (annotation is RInstance && annotation.provideForPath.isNotBlank() || annotation is RRouteInstance || annotation is RRouteAssist)
                    action(RouteInstance(element, annotation))
                else if (annotation is RInstanceRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
                else if (annotation is RRouteInstanceRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
                else if (annotation is RRouteAssistRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
            }
        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is RInstance -> annotationUri(annotation.uri, defaultScheme)
        is RRouteInstance -> annotationUri(annotation.uri, defaultScheme)
        is RRouteAssist -> annotationUri(annotation.uri.ifBlank { annotation.context }, defaultScheme)
        else -> ""
    }

    private val forPath = when (annotation) {
        is RInstance -> annotation.provideForPath
        is RRouteInstance -> annotation.provideForPath
        is RRouteAssist -> annotation.assistForPath
        else -> ""
    }

    private val version = when (annotation) {
        is RInstance -> annotation.version
        is RRouteInstance -> annotation.version
        is RRouteAssist -> annotation.version
        else -> ""
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        invokers(elementPool) { invoker ->
            ApiInstanceCodeBase(invoker, forPath, version).let { codeBase ->
                if (codeBase.path.isNotBlank()) {
                    context(uri(arguments.defaultScheme)).addApiInstance(codeBase)
                }
            }
        }
    }
}