package com.rubik.plugins.extension

import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikMultipleComponentDefinedException
import com.rubik.plugins.basic.utility.p
import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.extension.global.GlobalExtension
import org.gradle.api.Project
import org.gradle.api.Task

/**
 *  Rubik config extension of gradle plugins.
 *
 *  @since 1.3
 */
open class RubikConfigExtension(val project: Project) {
    // global
    val globalConfig = GlobalExtension(project)

    fun listenDefaultSchemeChanged(action: (String?) -> Unit) {
        globalConfig.listenSchemeChanged(action)
    }

    // contexts
    private val contexts = mutableMapOf<String, ContextExtension>()

    private val contextsProjectRegisteredListeners = mutableMapOf<String, MutableList<(ContextExtension) -> Unit>>()
    private val contextsUriRegisteredListeners = mutableMapOf<String, MutableList<(ContextExtension) -> Unit>>()
    private val contextsAllRegisteredListeners = mutableListOf<(ContextExtension) -> Unit>()

    fun register(context: ContextExtension) = context.apply {
        updateProjectPathIfNull(project.path)
        if (!contexts.containsKey(uri)) {
            contexts[uri] = this
        } else {
            throw RubikMultipleComponentDefinedException(uri)
        }


        projectPathToPublish.let { path ->
            contextsProjectRegisteredListeners[path]?.forEach { action ->
                action(this)
            }
        }

        contextsUriRegisteredListeners[uri]?.forEach { action ->
            action(this)
        }

        contextsAllRegisteredListeners.forEach { action ->
            action(this)
        }

        Logger.p(LogTags.CONTEXT_EXTENSION, project) { " REGISTER <$this>" }

    }

    val allContextsSoFar: List<ContextExtension>
        get() = contexts.map { entry -> entry.value }

    fun listenContextRegisteredByProjectPath(projectPath: String, action: (ContextExtension) -> Unit) {
        contexts.filter { entry ->
            entry.value.projectPathToPublish == projectPath
        }.forEach { entry ->
            action(entry.value)
        }
        contextsProjectRegisteredListeners.getOrPut(projectPath) { mutableListOf() }.add(action)
    }

    fun listenContextRegisteredByUri(uri: String, action: (ContextExtension) -> Unit) {
        contexts[uri]?.let(action)
        contextsUriRegisteredListeners.getOrPut(uri) { mutableListOf() }.add(action)
    }

    fun listenAnyContextRegistered(action: (ContextExtension) -> Unit) {
        contexts.forEach { entry ->
            action(entry.value)
        }
        contextsAllRegisteredListeners.add(action)
    }

    // lib task
    private val assembleContextLibTasks = mutableListOf<Task>()
    private val assembleContextLibTasksRegisteredListeners = mutableListOf<(Task) -> Unit>()
    private val publishContextLibTasks = mutableListOf<Task>()
    private val publishContextLibTasksRegisteredListeners = mutableListOf<(Task) -> Unit>()
    private val publishDevContextLibTasks = mutableListOf<Task>()
    private val publishDevContextLibTasksRegisteredListeners = mutableListOf<(Task) -> Unit>()

    fun registerLibTask(assembleTask: Task, publishTask: Task?, publishDevTask: Task?) {
        assembleContextLibTasks.add(assembleTask)
        Logger.p(LogTags.ALL_TASKS, project) { " TASK REGISTERED (${assembleTask.name})" }
        assembleContextLibTasksRegisteredListeners.forEach { action ->
            action(assembleTask)
        }

        publishTask?.let {
            publishContextLibTasks.add(publishTask)
            Logger.p(LogTags.ALL_TASKS, project) { " TASK REGISTERED (${publishTask.name})" }
            publishContextLibTasksRegisteredListeners.forEach { action ->
                action(publishTask)
            }
        }

        publishDevTask?.let {
            publishDevContextLibTasks.add(publishDevTask)
            Logger.p(LogTags.ALL_TASKS, project) { " TASK REGISTERED (${publishDevTask.name})" }
            publishDevContextLibTasksRegisteredListeners.forEach { action ->
                action(publishDevTask)
            }
        }

    }

    fun listenAssembleLibTaskRegistered(action: (Task) -> Unit) {
        assembleContextLibTasks.forEach { context ->
            action(context)
        }
        assembleContextLibTasksRegisteredListeners.add(action)
    }

    fun listenPublishLibTaskRegistered(action: (Task) -> Unit) {
        publishContextLibTasks.forEach { context ->
            action(context)
        }
        publishContextLibTasksRegisteredListeners.add(action)
    }

    fun listenPublishDevLibTaskRegistered(action: (Task) -> Unit) {
        publishDevContextLibTasks.forEach { context ->
            action(context)
        }
        publishDevContextLibTasksRegisteredListeners.add(action)
    }
}