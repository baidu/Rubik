package com.rubik.context.utility

open class Module<T : ModuleInjector> {
    private var inj: (() -> T)? = null

    fun inject(
        injector: () -> T
    ) {
        inj = injector
    }

    val content
        get() = inj?.invoke() ?: throw RuntimeException(" ${this::class.java.simpleName} module not init.")
}

interface ModuleInjector
