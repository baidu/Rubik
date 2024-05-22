package com.rubik.context.extra

import com.rubik.context.container.ContextsContainer
import com.rubik.global.GlobalConfig
import com.rubik.context.utility.Module
import com.rubik.context.utility.ModuleInjector


interface ContextModuleInjector : ModuleInjector {
    val globalConfig: GlobalConfig
    val contextsContainer: ContextsContainer
}

object ContextModule : Module<ContextModuleInjector>()

val globalConfig: GlobalConfig
    get() = ContextModule.content.globalConfig

val contextsContainer: ContextsContainer
    get() = ContextModule.content.contextsContainer

val allContexts: Map<String, Context>
    get() = contextsContainer.contexts