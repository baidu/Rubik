package com.rubik.plugins.context

import com.ktnail.x.Logger
import com.rubik.plugins.RubikPlugin
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikPluginNotApplyException
import com.rubik.plugins.basic.publish.maven.PublicationType
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.context.model.Lib
import com.rubik.plugins.context.task.component.ContextComponentTasks
import com.rubik.plugins.context.task.lib.ContextLibTasks
import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.extension.context.dependency.DependencyExtension
import com.rubik.plugins.extension.context.provider.ContextsProvider
import org.gradle.api.Project
import java.io.File

/**
 * The the plugin of rubik root project (application).
 * Provide the ability to publish contexts & Dependence other contexts.
 *
 * @ Since:1.3
 */
class ContextPlugin : RubikPlugin() {
    companion object {
        const val CONTEXT_LIB_BUILD_TYPE_NAME = "RContextLib"

        const val ASSEMBLE_TASK_NAME_PREFIX = "assembleRubik"
        const val PUBLISH_TASK_NAME_PREFIX = "publishRubik"
        const val PUBLISH_DEV_TASK_NAME_PREFIX = "publishRubikDev"
        const val PUBLISH_COMPONENT_TASK_NAME_SUFFIX = "RComponent"
    }

    private val tmpLibDirMode
        get() = project.propertyOr(Ext.RUBIK_TMP_LIB_DIR_MODE, false)

    private val dependencyContexts = mutableListOf<DependencyExtension>()

    private var _controller: ContextTaskController? = null
    private val controller: ContextTaskController
        get() = _controller ?: throw RubikPluginNotApplyException()

    override fun apply(project: Project) {
        super.apply(project)

        project.fuzzyApplyRubikConfigFiles(project.projectDir)

        _controller = ContextTaskController(project)

        ContextsProvider(project).observeProjectPathRegister(project.path) { context ->
            onContextRegistered(context)
        }

        controller.compute { operation, publicationType ->
            Logger.p(LogTags.COMPUTE_CONTEXT_LIBS, project) { "OPERATION:$operation PUBLICATION_TYPE:$publicationType" }

            dependencyContexts.forEach { dependency ->
                if (tmpLibDirMode)
                    dependContextLibsTmpDir(project.libTmpDirRoot, dependency)
                else
                    dependContextLibsMaven(
                        dependency,
                        operation == ContextTaskController.PublishOperation.PUBLISH_COMPONENT && publicationType == PublicationType.FORMAL
                    )
            }

            project.addRubikRouterDependency()
        }
    }

   private fun onContextRegistered(context: ContextExtension){
       Logger.p(LogTags.COMPUTE_CONTEXT_LIBS,project) { " CONTEXT REGISTERED <${context.uri}>" }

       ContextLibTasks.create(project, context, context.buildTypeName())?.apply {
           controller.addTasks(this)
       }

       project.forEachVariant { variant ->
           if (!variant.buildType.isContextLibBuildType()) {
               ContextComponentTasks.create(project, context, variant)?.apply {
                   controller.addTasks(this)
               }
           }
       }

       dependencyContexts.addAll(context.dependencyExtensions)
    }

    private fun dependContextLibsTmpDir(libTmpDirRoot: File, dependency: DependencyExtension) {
        addImplementationJarDirDependency(
            getLibsTmpDir(libTmpDirRoot, dependency.uri, Lib.Type.CONTEXT)
        )
        if (dependency.supportOriginalValue) {
            addCompileOnlyJarDirDependency(
                getLibsTmpDir(libTmpDirRoot, dependency.uri, Lib.Type.ORIGINAL_VALUE)
            )
        }
    }

    private fun dependContextLibsMaven(dependency: DependencyExtension, formalComponents: Boolean) {
        val devVersion = if (formalComponents) false else null
        addImplementationDependency(
            dependency.groupId, dependency.artifactId(Lib.Type.CONTEXT), dependency.versionToDependency(devVersion)
        )
        if (dependency.supportOriginalValue) {
            addCompileOnlyDependency(
                dependency.groupId, dependency.artifactId(Lib.Type.ORIGINAL_VALUE), dependency.versionToDependency(devVersion)
            )
        }
    }
}
