package com.rubik.context.utility

import com.ktnail.x.uri.buildUri
import com.rubik.context.exception.RubikDSLDefaultGroupNotSetException
import com.rubik.context.exception.RubikDSLDefaultSchemeNotSetException

fun String.toUriIfAuthorityOrName(
    globalScheme: String?,
    globalGroup: String?
) = when {
    contains("://") -> { // is full uri
        this
    }
    startsWith(".") -> { // name start with '.'
        buildUri(
            globalScheme ?: throw RubikDSLDefaultSchemeNotSetException(),
            toAuthority(
                globalGroup ?: throw RubikDSLDefaultGroupNotSetException(),
                this.removePrefix(".")
            )
        )
    }
    else -> { // authority
        buildUri(
            globalScheme ?: throw RubikDSLDefaultSchemeNotSetException(),
            this
        )
    }
}

fun toAuthority(group: String, name: String) = "$group.$name"
