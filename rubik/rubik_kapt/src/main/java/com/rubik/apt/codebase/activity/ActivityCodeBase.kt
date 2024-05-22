package com.rubik.apt.codebase.activity

import com.blueprint.kotlin.lang.element.KbpRooterElement
import com.rubik.annotations.route.RProperty
import com.rubik.apt.Constants
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.TokenName
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.noSpaces

/**
 * The code structure of Router Activity.
 *
 * @since 1.1
 */
class ActivityCodeBase(
    path: String,
    version: String,
    navigationOnly: Boolean,
    pathSectionOptimize: Boolean,
    val className: String,
    private val properties: List<ActivityPropertyCodeBase>
) : RouteCodeBase(path, version, navigationOnly, pathSectionOptimize), RToken {
    companion object {
        operator fun invoke(
            classElement: KbpRooterElement,
            path: String,
            version: String,
            navigationOnly: Boolean,
            pathSectionOptimize: Boolean
        ): ActivityCodeBase {
            return ActivityCodeBase(
                path,
                version,
                navigationOnly,
                pathSectionOptimize,
                classElement.qualifiedName,
                classElement.properties.mapNotNull { entry ->
                    entry.value.jmElement?.let { jmElement ->
                        val annotation = jmElement.getAnnotation(RProperty::class.java)
                        if (null != annotation && (annotation.forPath.isEmpty() || path in annotation.forPath)) {
                            ActivityPropertyCodeBase(
                                annotation.name.let { it.ifBlank { jmElement.simpleName.toString() } },
                                entry.value.type,
                                annotation.extra.let { it.ifBlank { null } }
                            )
                        } else null
                    }
                } + classElement.functions.mapNotNull { entry ->
                    entry.value.jmElement?.let { jmElement ->
                        val annotation = jmElement.getAnnotation(RProperty::class.java)
                        if (null != annotation && (annotation.forPath.isEmpty() || path in annotation.forPath) && null != entry.value.propertyNameIfAsGetter) {
                            ActivityPropertyCodeBase(
                                annotation.name.let { it.ifBlank { entry.value.propertyNameIfAsGetter.toString() } },
                                entry.value.returnType,
                                annotation.extra.let { it.ifBlank { null } }
                            )
                        } else null
                    }
                }
            )
        }
    }

    val sortedProperties
        get() = properties.sortedWith(compareBy { it.originalName })

    val propertiesKDoc
        get() =  sortedProperties.map { queryCodeBase -> "${queryCodeBase.originalName} : ${queryCodeBase.originalType}" }

    fun startCode(path: String, nameBox: FileNameBox): String =
        "${Constants.Aggregate.LAUNCHER_CLASS_NAME_AS}().${Constants.Aggregate.METHOD_LAUNCH_NAME}(${
            nameBox.closeSimpleName(className)
        }::class.java, ${Constants.Aggregate.ROUTE_PARAMETER_QUERIES_NAME}, ${
            if (Constants.Aggregate.isParameterPath(path)) Constants.Aggregate.makeGetPathQueriesCode(path) else "null"
        }, ${Constants.Aggregate.ROUTE_PARAMETER_RESULTS_NAME})".noSpaces()

    override fun location() = className

    override val tokenList
        get() = TokenList(
            path,
            version,
            navigationOnly,
            pathSectionOptimize,
            TokenName(className),
            properties,
            key = "ACT",
            warp = false
        )

}