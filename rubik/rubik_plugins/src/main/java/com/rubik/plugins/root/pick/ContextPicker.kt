package com.rubik.plugins.root.pick

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikException
import com.rubik.plugins.basic.utility.p
import com.rubik.plugins.basic.utility.rubikExtension
import com.rubik.plugins.basic.utility.toUriIfAuthority
import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.extension.context.provider.ContextsProvider
import com.rubik.plugins.extension.context.source.SourceExtension
import com.rubik.plugins.extension.root.model.*
import org.gradle.api.Project

class ContextPicker(private val rootProject:Project){

    private val consumedPickedContexts = mutableMapOf<String, PickedContext>()
    private val consumedFlavorPickedContexts = mutableMapOf<String, MutableMap<String, PickedContext>>()

    fun pickedContexts(variant: BaseVariant) =
        mutableMapOf<String, PickedContext>().apply {
            this += consumedPickedContexts
            variant.productFlavors.mapNotNull { flavor -> flavor.name }.forEach { flavor ->
                consumedFlavorPickedContexts[flavor]?.let { pickedContexts ->
                    this += pickedContexts
                }
            }
    }

    fun pick(action: (Picked, ContextExtension, SourceExtension?) -> Unit) {
        ContextsProvider(rootProject).let { provider ->
            rootProject.rubikExtension.pickedContextsSoFar.forEach { picked ->
                when (picked.who){
                    is ByFuzzyUri -> {
                        provider.observeAnyRegister { extension ->
                            if (picked.who.matching(extension.uri)) {
                                consumePickedContext(picked, extension) { action(picked, extension, null) }
                            }
                        }
                    }
                    is ByUri -> {
                        provider.observeUriRegister(picked.who.uriOrAuthority.toUriIfAuthority(rootProject)) { extension ->
                            consumePickedContext(picked, extension) { action(picked, extension, null) }
                        }
                    }
                    is ByTag -> {
                        provider.observeAnyRegister { extension ->
                            if (extension.tags.containsKey(picked.who.tag)) {
                                consumePickedContext(picked, extension) { action(picked, extension, extension.tags[picked.who.tag]) }
                            }
                        }
                    }
                    is All -> {
                        provider.observeAnyRegister { extension ->
                            consumePickedContext(picked, extension) { action(picked, extension, null) }
                        }
                    }
                }
            }
        }
    }

    private fun consumePickedContext(
        picked: Picked,
        context: ContextExtension,
        action: () -> Unit
    ) {
        try {
            if (null != picked.forFlavor) {
                consumedFlavorPickedContexts.getOrPut(picked.forFlavor) { mutableMapOf() }.let { pickedContexts ->
                    if (!pickedContexts.containsKey(context.uri)) {
                        action()
                        pickedContexts[context.uri] = PickedContext(picked, context).apply {
                            Logger.p(LogTags.PICK_CONTEXT, null) { " $this" }
                        }
                    }
                }
            } else {
                if (!consumedPickedContexts.containsKey(context.uri)) {
                    action()
                    consumedPickedContexts[context.uri] = PickedContext(picked, context).apply {
                        Logger.p(LogTags.PICK_CONTEXT, null) { " $this" }
                    }
                }
            }
        } catch (e: RubikException) {
            e.printStackTrace()
        }
    }
}

data class PickedContext(val picked: Picked, val extension: ContextExtension){
    override fun toString(): String {
        return "${extension.uri} BY: {$picked}"
    }
}