package com.rubik.picker.container

import com.android.build.gradle.api.BaseVariant
import com.rubik.context.extra.Context
import com.rubik.pick.*
import com.rubik.picker.*
import com.rubik.picker.extra.getShellPickCase
import com.rubik.picker.extra.holdPicked
import com.rubik.picker.extra.obtainContext
import com.rubik.picker.extra.releasePicked

class PickedContextsContainer {
    private val contexts = mutableMapOf<String, Context>()

    fun register(what: PickWhat, hows: FlavorHows?) {
        what.where.obtainContext().forEach { context ->
            context.holdPicked(hows, what)
            contexts[context.uri] = context
        }
    }

    fun unRegister(excepted: Excepted) {
        excepted.where.obtainContext().forEach { context ->
            if (null == excepted.forFlavor) {
                context.releasePicked(excepted)
                contexts.remove(context.uri)
            } else {
                if (context.releasePicked(excepted)) {
                    contexts.remove(context.uri)
                }
            }
        }
    }

    fun pickCases(variant: BaseVariant? = null) = contexts.mapNotNull { (_, context) ->
        context.getShellPickCase(variant)?.let { case->
            ContextPickCase(context, case)
        }
    }

}

data class ContextPickCase(val context: Context, val pickCase: PickCase)