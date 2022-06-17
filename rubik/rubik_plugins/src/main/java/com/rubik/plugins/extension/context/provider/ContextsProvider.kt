package com.rubik.plugins.extension.context.provider

import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.basic.utility.rubikConfigExtension
import org.gradle.api.Project

/**
 * Provide contexts define in any projects(with "rubik-context" plugin).
 * @since 1.3
 */
open class ContextsProvider(project: Project) : ContextsProvidable {
    private val extension = project.rubikConfigExtension

    override val allContextsSoFar
        get() = extension.allContextsSoFar

    override fun observeProjectPathRegister(projectPath: String, action: (ContextExtension) -> Unit) {
        extension.listenContextRegisteredByProjectPath(projectPath, action)
    }

    override fun observeUriRegister(uri: String, action: (ContextExtension) -> Unit) {
        extension.listenContextRegisteredByUri(uri, action)
    }

    override fun observeAnyRegister(action: (ContextExtension) -> Unit) {
        extension.listenAnyContextRegistered(action)
    }
}
