package com.rubik.publish.task

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.arrayProperties
import com.ktnail.gradle.forEachVariant
import com.ktnail.gradle.maven.PublishingTaskProvider
import com.rubik.context.extra.Context
import com.rubik.context.Ext
import com.rubik.context.extra.isCustomVariant
import com.rubik.context.extra.libTmpDirRoot
import com.rubik.publish.extra.mavenConfig
import com.rubik.publish.task.both.ComponentAndContextLibsTask
import com.rubik.publish.task.both.PackingLinkComponentAndContextLibsTask
import com.rubik.publish.task.both.VariantComponentAndContextLibsTask
import com.rubik.publish.task.component.ComponentTask
import com.rubik.publish.task.component.PackingLinkComponentTask
import com.rubik.publish.task.component.VariantComponentTask
import com.rubik.publish.task.lib.ContextLibTask
import com.rubik.publish.task.target.ContextTaskTarget
import com.rubik.publish.extra.publishTasksContainer
import org.gradle.api.Project
import java.io.File

open class PublishContextTaskController(
    val project: Project,
    private val libTmpDirRoot: File = project.libTmpDirRoot
){

    init {
        project.afterEvaluate {
            graphTasks()
            registerToRoot()
        }
    }

    open val tasks = mutableMapOf<String, PublishContextTaskGroup>()

    private val tasksToGraph = mutableListOf<PublishTask>()

    private val publishingTaskProvider = PublishingTaskProvider(project, project.mavenConfig)

    private val exceptBuildTypes = project.arrayProperties(Ext.RUBIK_EXCEPT_COMPONENT_BUILD_TYPES)
    private val exceptFlavors = project.arrayProperties(Ext.RUBIK_EXCEPT_COMPONENT_FLAVORS)

    fun createTasks(
        context: Context,
        target: ContextTaskTarget,
        onPublishVariantComponent: (VariantComponentTask) -> Unit
    ) {
        val libsTasks = createContextLibTask(context, target)
        var componentTasks: PublishTaskPair<ComponentTask>? = null

        project.forEachVariant { variant ->
            if (!variant.isCustomVariant && !variant.isExceptedVariant) {
                createVariantComponentTask(context, target, variant)?.let { variantComponentTasks ->
                    if (componentTasks == null) {
                        createComponentTask(context, target)?.let {
                            componentTasks = it
                            createPackingLinkComponentTask(context, it)
                            if (null != libsTasks) {
                            val bothTasks = createComponentAndContextLibsTask(context, target, libsTasks, it)
                                createPackingLinkComponentAndContextLibsTask(context, bothTasks)
                            }
                        }
                    }

                    if (null != libsTasks) {
                        createVariantComponentAndContextLibsTask(context, target, variant, libsTasks, variantComponentTasks)
                    }

                    componentTasks?.first?.addVariantTask(variantComponentTasks.first)
                    componentTasks?.second?.addVariantTask(variantComponentTasks.second)

                    variantComponentTasks.first.ifExecute {
                        onPublishVariantComponent(variantComponentTasks.first)
                    }
                    variantComponentTasks.second.ifExecute {
                        onPublishVariantComponent(variantComponentTasks.second)
                    }
                }
            }
        }
    }

    private fun createContextLibTask(
        context: Context,
        target: ContextTaskTarget
    ) = if (context.enableProvideRoute)
        ContextLibTask(
            project,
            context,
            target,
            ContextLibTask.AssembleTask(project, context),
            publishingTaskProvider,
            libTmpDirRoot
        ).register()
    else null

    private fun createVariantComponentTask(
        context: Context,
        target: ContextTaskTarget,
        variant: BaseVariant
    ) = if (context.enablePublishComponent)
        VariantComponentTask(
            project,
            context,
            target,
            variant,
            publishingTaskProvider
        ).register()
    else null

    private fun createComponentTask(
        context: Context,
        target: ContextTaskTarget
    ) = if (context.enablePublishComponent)
        ComponentTask(
            project, context,
            target
        ).register()
    else null

    private fun createComponentAndContextLibsTask(
        context: Context,
        target: ContextTaskTarget,
        libTasks: PublishTaskPair<ContextLibTask>,
        componentTasks: PublishTaskPair<ComponentTask>
    ) = ComponentAndContextLibsTask(
            project,
            context,
            target,
            libTasks,
            componentTasks
        ).register()


    private fun createVariantComponentAndContextLibsTask(
        context: Context,
        target: ContextTaskTarget,
        variant: BaseVariant,
        libTasks: PublishTaskPair<ContextLibTask>,
        componentTasks: PublishTaskPair<VariantComponentTask>
    ) = VariantComponentAndContextLibsTask(
            project,
            context,
            target,
            variant,
            libTasks,
            componentTasks
        ).register()


    private fun createPackingLinkComponentTask(
        context: Context,
        componentTasks: PublishTaskPair<ComponentTask>
    ) = if (context.whoPackingMe.isNotEmpty())
        PackingLinkComponentTask(
            project,
            context,
            componentTasks
        ).register()
    else null

    private fun createPackingLinkComponentAndContextLibsTask(
        context: Context,
        bothTasks: PublishTaskPair<ComponentAndContextLibsTask>
    ) = if (context.whoPackingMe.isNotEmpty())
        PackingLinkComponentAndContextLibsTask(
            project,
            context,
            bothTasks
        ).register()
    else null

    private fun graphTasks() = tasksToGraph.forEach { task ->
        task.graphicIfTarget()
    }

    private fun <T : PublishContextTask> PublishTaskPair<T>.register() = apply {
        if (first.targetExecute)
            tasksToGraph.add(first)
        if (second.targetExecute)
            tasksToGraph.add(second)
        tasks.getOrPut(first.context.uri) { PublishContextTaskGroup() }.put(this)
    }

    private fun registerToRoot() {
        publishTasksContainer.registerContextTasks(tasks)
    }

    private val BaseVariant.isExceptedVariant: Boolean
        get() = (exceptBuildTypes.isNotEmpty() && buildType.name in exceptBuildTypes) ||
                (exceptFlavors.isNotEmpty() && productFlavors.any { flavor -> flavor.name in exceptFlavors })

}
