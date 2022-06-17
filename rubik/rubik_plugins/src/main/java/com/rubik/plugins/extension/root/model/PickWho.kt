package com.rubik.plugins.extension.root.model

interface PickWho

open class ByUri(
    val uriOrAuthority: String
) : PickWho {
    companion object {
        fun create(uriOrAuthority: String): PickWho {
            return when {
                uriOrAuthority == "*" -> All()
                uriOrAuthority.contains('*') -> ByFuzzyUri(uriOrAuthority)
                else -> ByUri(uriOrAuthority)
            }
        }
    }

    override fun toString(): String {
        return "byUri:$uriOrAuthority"
    }

    override fun equals(other: Any?): Boolean {
        return (other as? ByUri)?.uriOrAuthority == uriOrAuthority
    }

    override fun hashCode(): Int {
        return uriOrAuthority.hashCode()
    }
}

class ByFuzzyUri(
    private val fuzzyUri: String
) : PickWho {
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
) : PickWho {
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

class All : PickWho {
    override fun toString(): String {
        return "all"
    }

    override fun equals(other: Any?): Boolean {
        return other is All
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
