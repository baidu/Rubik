package com.rubik.plugins.context.controller

import com.ktnail.gradle.*
import com.ktnail.x.Logger
import com.rubik.context.Ext
import com.rubik.context.extra.*
import com.rubik.context.utility.*
import com.rubik.plugins.basic.LogTags
import com.rubik.publish.publishVersion
import com.rubik.plugins.basic.extra.*
import com.rubik.publish.extra.target
import org.gradle.api.Project

class ContextKaptController(
    private val project: Project
) {
    private var sourceSetEnabled = false
    private var kaptEnabled = false

    fun configKapt(context: Context) {
        if (context.enableProvideRoute && !sourceSetEnabled) {
            configAggregateSourceSet()
            sourceSetEnabled = true
        }

        if (context.target.publishingSth) {
            enableRubikKapt()
        }

        project.forEachVariant { variant ->
            if (variant.buildType.isContextLibBuildType() && context.target.publishingContextLibs) {
                val version = context.publishVersion(context.target.publicationType,context.target.publishByTag)
                variant.putKaptContext(context, version)
                variant.putKaptContextLibsEnable(context)
                if (!project.generateAggregateInBuildDir) {
                    variant.putKaptAggregateEnable(context)
                }
            }
            if (!variant.isCustomVariant && (context.target.publishingComponent || (context.target.context.enableProvideRoute && project.autoGenerateAggregate))) {
                val version = context.publishVersion(context.target.publicationType,context.target.publishByTag)
                variant.putKaptContext(context, version)
                variant.putKaptAggregateEnable(context)
            }
        }
    }

    private fun configAggregateSourceSet() {
        if (project.autoGenerateAggregate) {
            if (!project.generateAggregateInBuildDir) {
                project.rubik.listenAggregateSetChanged { path ->
                    project.addToJavaSourceSet("main", path)
                    enableRubikKapt()
                    project.putKaptArgument(Arguments.Declare.AGGREGATE_GENERATED, path)
                }
            }
        } else {
            project.rubik.listenAggregateSetChanged { path ->
                project.addToJavaSourceSet("main", path)
            }
        }
    }

    private fun enableRubikKapt() {
        if (!kaptEnabled) {
            project.applyKapt()
            project.addRubikKaptDependency()
            globalConfig.listenSchemeChanged { scheme ->
                scheme?.let {
                    Logger.p(LogTags.PREPARE_CONTEXT, project) { " DefaultScheme REGISTERED  (${scheme})" }
                    project.putKaptArgument(Arguments.Declare.DEFAULT_SCHEME, scheme)
                    project.putKaptArgument(
                        Arguments.Declare.AGGREGATE_METHOD_SIZE,
                        project.propertyOfT<Int>(Ext.RUBIK_ON_ROUTE_METHOD_MAX_SIZE) ?: 100
                    )
                }
            }
            project.putKaptBooleanArgument(
                Arguments.Declare.CONTEXT_ROUTER_ENABLE,
                project.propertyOr(Ext.RUBIK_GENERATE_ROUTER_CONTEXT, false)
            )
            project.putKaptBooleanArgument(
                Arguments.Declare.AGGREGATE_USER_AND_TIME_ENABLE,
                project.propertyOr(Ext.RUBIK_AGGREGATE_KDOC_ADD_USER_ADD_TIME, true)
            )
            project.putKaptArgument(
                Arguments.Declare.CONTEXT_IGNORE_VALUE_ANNOS,
                project.arrayProperties(Ext.RUBIK_IGNORE_VALUE_ANNOTATIONS).joinToString("|")
            )
            kaptEnabled = true
        }
    }


}