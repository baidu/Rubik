package com.rubik.apt.annotation

import com.blueprint.kotlin.lang.utility.findSuperType
import com.blueprint.kotlin.lang.utility.toKbpClassElement
import com.blueprint.kotlin.lang.utility.toKbpElement
import com.blueprint.kotlin.pool.ElementPool
import com.ktnail.x.pascalToSnake
import com.rubik.annotations.route.RObject
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.RRouteRepeatable
import com.rubik.annotations.route.function.RDefaultType
import com.rubik.annotations.route.function.RFunction
import com.rubik.annotations.route.function.RFunctionRepeatable
import com.rubik.annotations.route.page.RPage
import com.rubik.annotations.route.page.RPageRepeatable
import com.rubik.apt.Constants
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.plugin.PluginArguments
import com.rubik.apt.utility.defaultPath
import com.rubik.apt.utility.typeToStringInAnnotations
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class Route(element: Element, originAnnotation: Annotation) : AnnotationData(element, originAnnotation) {
    companion object {
        val OBTAINER = object : CodebaseObtainer<Route>() {
            override val annotations = arrayOf(
                RRoute::class.java,
                RFunction::class.java,
                RPage::class.java,
                RObject::class.java,
                RRouteRepeatable::class.java,
                RFunctionRepeatable::class.java,
                RPageRepeatable::class.java
            )

            override fun onObtain(
                element: Element,
                annotation: Annotation,
                action: (Route) -> Unit
            ) {
                if (annotation is RRoute || annotation is RFunction || annotation is RPage || annotation is RObject)
                    action(Route(element, annotation))
                else if (annotation is RRouteRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
                else if (annotation is RFunctionRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
                else if (annotation is RPageRepeatable)
                    annotation.value.forEach {
                        onObtain(element, it, action)
                    }
            }

        }
    }

    private fun uri(defaultScheme: String?) = when (annotation) {
        is RRoute -> annotationUri(annotation.uri.ifBlank { annotation.context }, defaultScheme)
        is RFunction -> annotationUri(annotation.uri, defaultScheme)
        is RPage -> annotationUri(annotation.uri, defaultScheme)
        is RObject -> annotationUri(annotation.uri, defaultScheme)
        else -> ""
    }

    private val path = when (annotation) {
        is RRoute -> annotation.path
        is RFunction -> annotation.path
        is RPage -> annotation.path
        is RObject -> ""
        else -> ""
    }

    private val version = when (annotation) {
        is RRoute -> annotation.version
        is RFunction -> annotation.version
        is RPage -> annotation.version
        is RObject -> ""
        else -> ""
    }

    private val navigationOnly = when (annotation) {
        is RRoute -> annotation.navigationOnly
        is RFunction -> annotation.navigationOnly
        is RPage -> annotation.navigationOnly
        is RObject -> true
        else -> false
    }

    private val resultType = when (annotation) {
        is RRoute -> typeToStringInAnnotations {  annotation.resultType.qualifiedName }
        is RFunction -> typeToStringInAnnotations {  annotation.resultType.qualifiedName }
        is RPage -> null
        is RObject -> null
        else -> null
    }

    private val syncReturn = when (annotation) {
        is RRoute -> annotation.forResult || annotation.syncReturn
        is RFunction -> annotation.forResult || annotation.syncReturn
        is RPage -> true
        is RObject -> true
        else -> true
    }

    private val pathSectionOptimize = when (annotation) {
        is RRoute -> false
        is RFunction -> true
        is RPage -> true
        is RObject -> true
        else -> false
    }

    override fun addToCodebase(
        context: (String) -> ContextCodeBase,
        elementPool: ElementPool,
        arguments: PluginArguments
    ) {
        if ((element as? TypeElement)?.findSuperType(Constants.Activities.ACTIVITY_CLASS_NAME) != null) {
            element.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                ActivityCodeBase(
                    kbpClassElement,
                    path.ifBlank { kbpClassElement.simpleNames.pascalToSnake(false, "-") },
                    version,
                    navigationOnly = navigationOnly,
                    pathSectionOptimize = pathSectionOptimize
                ).let { codeBase ->
                    if (codeBase.path.isNotBlank()) {
                        context(uri(arguments.defaultScheme)).addActivity(codeBase)
                    }
                }
            }
        } else {
            invokers(elementPool) { invoker ->
                ApiCodeBase(
                    invoker,
                    path.ifBlank { element.toKbpElement(elementPool)?.defaultPath() ?: path },
                    version,
                    resultType.let { if (it == RDefaultType::class.java.name) null else it },
                    navigationOnly = navigationOnly,
                    syncReturn = syncReturn,
                    pathSectionOptimize = pathSectionOptimize
                ).let { codeBase ->
                    if (codeBase.path.isNotBlank()) {
                        context(uri(arguments.defaultScheme)).addApi(codeBase) { added ->
                            codeBase.apply { resetCrashPath(added) }
                        }
                    }
                }
            }
        }
    }
}

