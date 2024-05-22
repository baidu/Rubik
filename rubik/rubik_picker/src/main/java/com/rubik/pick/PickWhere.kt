package com.rubik.pick

sealed class PickWhere{
    val tagName: String?
        get() = if (this is ByTag) tag else null
}

open class ByUri(
    val uri: String
) : PickWhere() {
    companion object {
        fun create(uri: String): PickWhere {
            return when {
                uri == "*" -> ByAll()
                uri.contains('*') -> ByFuzzyUri(uri)
                else -> ByUri(uri)
            }
        }
    }

    override fun toString(): String {
        return "byUri:$uri"
    }

    override fun equals(other: Any?): Boolean {
        return (other as? ByUri)?.uri == uri
    }

    override fun hashCode(): Int {
        return uri.hashCode()
    }
}

class ByFuzzyUri(
    private val fuzzyUri: String
) : PickWhere() {
    override fun toString(): String {
        return "byFuzzyUri:${fuzzyUri}"
    }

    fun matching(matchingUri: String): Boolean {
        if (matchingUri.isBlank() || fuzzyUri.isBlank())
            return false

        var fuzzyIndex = 0
        var givenIndex = 0
        var fuzzyMatching = false

        while (givenIndex < matchingUri.length) {

            val fuzzyChar = fuzzyUri.getOrNull(fuzzyIndex)
            val givenChar = matchingUri[givenIndex]

            if (fuzzyMatching) {
                if (fuzzyChar == givenChar) {
                    fuzzyIndex++
                    fuzzyMatching = false
                }
            } else if (fuzzyChar == '*') {
                fuzzyIndex++
                fuzzyMatching = true
            } else if (fuzzyChar == givenChar) {
                fuzzyIndex++
            } else {
                return false
            }
            givenIndex++
        }

        return fuzzyIndex == fuzzyUri.length
    }
}

data class ByTag(
    val tag: String
) : PickWhere() {
    override fun toString(): String {
        return "byTag:$tag"
    }

    override fun equals(other: Any?): Boolean {
        return (other as? ByTag)?.tag == tag
    }

    override fun hashCode(): Int {
        return tag.hashCode()
    }
}

class ByAll : PickWhere() {
    override fun toString(): String {
        return "all"
    }

    override fun equals(other: Any?): Boolean {
        return other is ByAll
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
