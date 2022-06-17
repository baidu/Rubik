package com.rubik.plugins.module.task

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.ktnail.x.toCamel
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.publish.maven.PublishingTaskProvider
import com.rubik.plugins.basic.task.TaskGraphic
import com.rubik.plugins.basic.task.graphicFinalizedWith
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.module.ModuleController
import com.rubik.plugins.module.relation.Module
import com.rubik.plugins.module.relation.PubModule
import com.rubik.plugins.module.relation.localMode
import com.rubik.plugins.module.relation.localNoMavenMode
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Task graphic of all Modules in project from root,
 * graph publish all Modules from root.
 *
 * @since 1.8
 */
class ModulePublishTaskGraphic(
    private val rootProject: Project
) : TaskGraphic() {
    val provider = PublishingTaskProvider(rootProject)
    fun addRoot(
        project: Project,
        variant: BaseVariant,
        name: String
    ) : Task {
        Logger.p(LogTags.MODULE_LINK, project) { " ADD ROOT TASK name:(${name}) !!!" }
        return getRootTask(project, name).apply {
             if (project.isTaskTarget(name)) {
                 whenGraph {
                     graphicFinalizedWith( variant.javaCompileProvider.get())
                 }
             }
         }
    }

    private val graphed: MutableSet<Module> = mutableSetOf()
    fun addModule(module: PubModule, taskProvider: PublishingTaskProvider) {
        whenGraph {
            if(!graphed.contains(module)){
                if (rootProject.localNoMavenMode) {
                    graphLocalCopyTask(module)
                } else {
                    graphMavenPublishTask(module, taskProvider)
                }
                graphed.add(module)
            }
        }
    }

    private fun graphMavenPublishTask(module: PubModule, taskProvider: PublishingTaskProvider) {
        module.project.applyMaven()
        taskProvider.getTask(module.pub, module.project.localMode).doLast {
            module.configs.updateToken(module, module.pub.storeToken)
            Logger.p(LogTags.MODULE_LINK, module.project) { " PUBLISH MAVEN FINISH ！variant:(${module.variant.name}) token:(${module.pub.storeToken})" }
        }.let { publishingTask ->
            module.variant.preBuildProvider.graphicFinalizedWith(module.variant.assembleProvider)
            module.variant.assembleProvider.graphicFinalizedWith(publishingTask)
        }
    }

    private fun graphLocalCopyTask(module: PubModule) {
        module.variant.preBuildProvider.graphicFinalizedWith(
            module.variant.assembleProvider.get().doLast {
                module.pub.publicationFile.copyTo(module.pub.tmpDirFile,true)
                module.configs.updateToken(module, module.pub.storeToken)
                Logger.p(LogTags.MODULE_LINK, module.project) { " PUBLISH TMP_DIR FINISH ！variant:(${module.variant.name}) token:(${module.pub.storeToken})" }
            })
    }

    private fun getRootTask(project: Project, name: String): Task =
        project.getTasksByName(name, false).min() ?: project.rubikModuleTask(name)

}

val BaseVariant.publishAllTaskName
    get() = toCamel(ModuleController.PUBLISH_TASK_NAME_PREFIX, "All", name, ModuleController.PUBLISH_MODULE_TASK_NAME_SUFFIX)

val BaseVariant.publishSourceTaskName
    get() = toCamel(ModuleController.PUBLISH_TASK_NAME_PREFIX, "Source", name, ModuleController.PUBLISH_MODULE_TASK_NAME_SUFFIX)

val BaseVariant.publishChangedTaskName
    get() = toCamel(ModuleController.PUBLISH_TASK_NAME_PREFIX, "Changed", name, ModuleController.PUBLISH_MODULE_TASK_NAME_SUFFIX)
