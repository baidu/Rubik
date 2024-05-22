package com.rubik.apt.codebase

import com.blueprint.kotlin.lang.type.KbpType
import com.ktnail.x.NameMapping
import com.squareup.kotlinpoet.TypeName

interface RToken {
    val tokenList: TokenList
}

class TokenName(val name: String) {
    fun mapping(nameMapping: NameMapping) {
        nameMapping.mapping(name)
    }

    fun use(nameMapping: NameMapping?) = nameMapping?.use(name) ?: name
}

class TokenList(
    vararg tokens: Any?,
    private val key: String? = null,
    private val warp: Boolean = false
) {
    private val tokenList = tokens.toList()
    fun mappingNames(nameMapping: NameMapping) {
        tokenList.anyMappingNames(nameMapping)
    }

    fun toToken(nameMapping: NameMapping?): String {
        val token = if (warp) tokenList.joinToString("") { item -> "\n${item.anyToToken(nameMapping)}" }
        else tokenList.anyToToken(nameMapping)
        return if (null != key) "[${key}]${token}[<]" else token
    }

}

fun RToken.createToken() = NameMapping(5) { original ->
    when {
//        original.contains("/") -> original.split("/").let { paths->
//            if (paths.size > 2)  paths[paths.lastIndex - 1] + "/" to paths.last()
//            else original to null
//        }
        original.contains(".") -> {
            val simple = original.substringAfterLast(".")
            if (original.endsWith("?")) simple.removeSuffix("?") to "?"
            else simple to null
        }
        original.length > 10 -> original.substring(0, 10) to null
        else -> original to null
    }
}.let { nameMapping ->
    this.tokenList.mappingNames(nameMapping)

    this.tokenList.toToken(nameMapping) +
            nameMapping.mappingManyTimes.sortedBy { (_, simple) ->
                simple
            }.joinToString("") { (original, simple) ->
                "\n[MAP]$simple->$original[<]"
            }
}

private fun Any?.anyMappingNames(nameMapping: NameMapping) {
    when (this) {
        is RToken -> this.tokenList.mappingNames(nameMapping)
        is Collection<*> -> this.forEach { any -> any.anyMappingNames(nameMapping) }
        is Map<*, *> -> this.forEach { entry ->
            entry.key.anyMappingNames(nameMapping)
            entry.value.anyMappingNames(nameMapping)
        }
        is KbpType -> this.mappingTypes(nameMapping)
        is TokenName -> this.mapping(nameMapping)
        is TypeName -> nameMapping.mapping(this.toString())
    }
}


private fun Any?.anyToToken(nameMapping: NameMapping?): String = if (this == null) "N" else
    when (this) {
        is RToken -> this.tokenList.toToken(nameMapping)
        is Collection<*> -> if(this.isEmpty()) "E" else this.joinToString("|") { any -> any.anyToToken(nameMapping) }
        is Map<*, *> -> if(this.isEmpty()) "E" else this.map { entry -> "${entry.key.anyToToken(nameMapping)}:${entry.value.anyToToken(nameMapping)}" }.anyToToken(nameMapping)
        is KbpType -> this.toSign(nameMapping)
        is TokenName -> this.use(nameMapping)
        is TypeName -> nameMapping?.use(this.toString()) ?: this.toString()
        is Boolean -> if (this) "T" else "F"
        is String -> this.ifBlank { "B" }
        else -> "-"
    }