package com.rubik.plugins.extension.context.provider

import com.rubik.plugins.extension.context.ContextExtension

interface ContextsProvidable {
    val allContextsSoFar: List<ContextExtension>
    fun observeProjectPathRegister(projectPath: String, action: (ContextExtension) -> Unit)
    fun observeUriRegister(uri: String, action: (ContextExtension) -> Unit)
    fun observeAnyRegister(action: (ContextExtension) -> Unit)
}