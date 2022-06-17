package com.rubik.plugins.context

import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.publish.maven.PublicationType
import com.rubik.plugins.basic.publish.maven.PublishingTaskProvider
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.context.task.component.ContextComponentTaskGraphic
import com.rubik.plugins.context.task.component.ContextComponentTasks
import com.rubik.plugins.context.task.lib.ContextLibTaskGraphic
import com.rubik.plugins.context.task.lib.ContextLibTasks
import com.rubik.plugins.context.transform.MakeLibsTransform
import org.gradle.api.Action
import org.gradle.api.Project
import java.io.File

class ContextTaskController(
    private val project: Project,
    private val libTmpDirRoot: File = project.libTmpDirRoot
) {
    private val publishingTaskProvider = PublishingTaskProvider(project)

    private val publishContextLibTasks = mutableListOf<ContextLibTasks>()
    private val publishContextComponentTasks = mutableListOf<ContextComponentTasks>()

    private var operation: PublishOperation = PublishOperation.NOT_CONTEXT_TASK
    private var publicationType: PublicationType? = null

    init {
        initConfig()
    }

    fun addTasks(tasks: ContextLibTasks) {
        tasks.isConfiguring(project)?.let { type->
            operation = PublishOperation.PUBLISH_CONTEXT_LIB
            project.applyMaven()
            publicationType = type
            publishContextLibTasks.add(tasks)
            tasks.config()
            tasks.configKapt()
        }
        project.putKaptContext(tasks.context)
    }

    fun addTasks(tasks: ContextComponentTasks) {
        tasks.configuringPublicationType(project)?.let { type->
            operation = PublishOperation.PUBLISH_COMPONENT
            project.applyMaven()
            publicationType = type
            publishContextComponentTasks.add(tasks)
        }
    }

    fun compute(action: (PublishOperation, PublicationType?) -> Unit) {
        project.afterEvaluate {
            if (operation == PublishOperation.PUBLISH_CONTEXT_LIB) {
                publishContextLibTasks.forEach { tasks ->
                    tasks.graphic()
                }
            } else if (operation == PublishOperation.PUBLISH_COMPONENT) {
                publishContextComponentTasks.forEach { tasks ->
                    tasks.graphic()
                }
            }
            action(operation, publicationType)
        }
    }

    private fun initConfig(){
        if (project.autoGenerateAggregate) {
            enableRubikKapt()
            project.putKaptBooleanArgument(Arguments.Declare.AGGREGATE_ENABLE, true)
        } else {
            project.rubikExtension.listenAggregateSetChanged { path ->
                project.addToJavaSourceSet("main", path)
            }
        }
    }

    private fun ContextLibTasks.config() {
        project.androidExtension?.buildTypes(Action { container ->
            container.maybeCreate(context.buildTypeName()).apply {
                isDebuggable = true
                setMatchingFallbacks("debug")
            }
            Logger.p(LogTags.COMPUTE_CONTEXT_LIBS, project) { " ADD BUILD_TYPES (${context.buildTypeName()})" }
        })
        if (!context.useResetCompiler) {
            project.androidExtension?.registerTransform(MakeLibsTransform(project, context))
        }
    }

    private fun ContextLibTasks.configKapt(){
        enableRubikKapt()
        project.putKaptContextEnable(context)
        if (!project.autoGenerateAggregate) {
            project.rubikExtension.listenAggregateSetChanged { path ->
                project.putKaptArgument(Arguments.Declare.AGGREGATE_GENERATED, path)
            }
            project.putKaptBooleanArgument(Arguments.Declare.AGGREGATE_ENABLE, true)
        } else {
            project.putKaptBooleanArgument(Arguments.Declare.AGGREGATE_ENABLE, false)
        }
    }

    private fun ContextLibTasks.graphic() {
        project.firstVariant(context.buildTypeName()) { variant ->
            ContextLibTaskGraphic(project, this, publishingTaskProvider, context).graph(variant, libTmpDirRoot)
        }
    }

    private fun ContextComponentTasks.graphic() {
        variant.outputAar?.let{ output ->
            ContextComponentTaskGraphic(project, this, publishingTaskProvider, output).graph()
        }
    }

    private fun enableRubikKapt(){
        project.applyKapt()
        project.addRubikKaptDependency()
        project.rubikConfigExtension.listenDefaultSchemeChanged { scheme ->
            scheme?.let {
                Logger.p(LogTags.COMPUTE_CONTEXT_LIBS, project) { " DefaultScheme REGISTERED  " + "(${scheme})" }
                project.putKaptArgument(Arguments.Declare.DEFAULT_SCHEME, scheme)
            }
        }
    }

    enum class PublishOperation {
        PUBLISH_CONTEXT_LIB,
        PUBLISH_COMPONENT,
        NOT_CONTEXT_TASK
    }
}

