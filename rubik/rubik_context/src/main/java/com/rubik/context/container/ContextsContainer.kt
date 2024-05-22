package com.rubik.context.container

import com.ktnail.x.Logger
import com.rubik.context.extra.Context
import com.rubik.context.exception.RubikComponentUndefinedException
import com.rubik.context.exception.RubikMultipleComponentDefinedException
import com.rubik.context.log.LogTags

class ContextsContainer {
    val contexts = mutableMapOf<String, Context>()

     fun register(context: Context) = context.apply {
        if (!contexts.containsKey(uri)) {
            contexts[uri] = this
        } else {
            throw RubikMultipleComponentDefinedException(uri)
        }

        Logger.dta(LogTags.CONTEXT) { " REGISTER <$this>" }
    }

    fun obtainAny() =
        contexts.map { (_, value) -> value }

    fun obtainByUri(uri: String, strict: Boolean = true) =
        contexts[uri] ?: if (strict) throw RubikComponentUndefinedException(uri) else null

    fun obtainByProject(projectPath: String): List<Context> =
        contexts.filterValues { value -> value.projectPath == projectPath }.map { (_, value) -> value }

    fun obtainByTag(tag: String) =
        contexts.filterValues { value -> value.tags.containsKey(tag) }.map { (_, value) -> value }

    fun obtainPackingLink(
        context: Context,
        acc: MutableList<Context> = mutableListOf()
    ): List<Context> = acc.apply {
        obtainPacking(context).let { packing ->
            addAll(packing)
            packing.forEach { packed ->
                obtainPackingLink(packed, acc)
            }
        }
    }

    fun obtainPacking(
        context: Context
    ): List<Context> =
        context.packing.mapNotNull { dep -> contexts[dep.uri] }

    fun obtainPackingThis(context: Context) =
        contexts.filterValues { value -> value.packing.any { dep -> dep.uri == context.uri } }
            .map { (_, value) -> value }
}
