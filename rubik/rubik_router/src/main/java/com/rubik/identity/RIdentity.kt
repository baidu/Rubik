package com.rubik.identity

import com.ktnail.x.md5
import com.rubik.router.annotations.RInvariant

abstract class RIdentity {
    abstract val uri: String
    abstract val mark :RMark
    override fun toString() = " [$uri] \n - id:$mark \n "
}

open class RMark(
    val version: String,
    val token: String? = null,
    val variant: String? = null,
    val md5: String? = null
){
    fun checkVersion(other: RMark): Boolean {
        return version == other.version || version.checkIfAutoVersion(other.version) || other.version.checkIfAutoVersion(version)
        // && variant == other.variant
    }

    private fun String.checkIfAutoVersion(other: String): Boolean {
        return if (isCodeVersion()) {
            if (other.isDevVersion()) {
                codeVersionToVersion() == other.devVersionToVersion()
            } else {
                codeVersionToVersion() == other
            }
        } else false
    }

    fun checkToken(other: RMark): Boolean =
        null != token && null != other.token && token.processOldVersionToken() == other.token.processOldVersionToken()

    private fun String.processOldVersionToken() = if (startsWith("<CTX>")) md5() else this

    fun checkMd5(other: RMark) = null != md5 && null != other.md5 && md5 == other.md5

    override fun toString() = " version:${version} " +
            (if (null != variant) " variant:${variant} " else "") +
            (if (null != token) " token: HAS " else "") +
            (if (null != md5) " md5:${md5} " else "")

    fun toStringWithToken() = " version:${version} " +
            (if (null != variant) " variant:${variant} " else "") +
            (if (null != token) " token:${token} " else "") +
            (if (null != md5) " md5:${md5} " else "")
}

@RInvariant
open class RComponentId(
    override val uri: String,
    override val mark: RMark,
    val dependencies: List<RContextId>,
    val packed: List<RComponentId>
) : RIdentity() {
    constructor(
        uri: String,
        version: String,
        variant: String,
        dependencies: List<RContextId>,
        packed: List<RComponentId>
    ) : this(
        uri,
        RMark(version = version, variant = variant),
        dependencies,
        packed
    )

    constructor(
        uri: String,
        version: String,
        variant: String
    ) : this(
        uri,
        RMark(version = version, variant = variant),
        listOf(),
        listOf()
    )
    override fun toString() = " RComponent ${super.toString()}"
}


open class RAggregateId(
    override val uri: String,
    override val mark: RMark
) : RIdentity() {
    constructor(uri: String, version: String) : this(uri, RMark(version = version))
    constructor(uri: String, version: String, token: String) : this(uri, RMark(version = version, token = token))
    override fun toString() = " RAggregate ${super.toString()}"
}

open class RContextId(
    override val uri: String,
    override val mark: RMark
) : RIdentity() {
    constructor(uri: String, version: String) : this(uri, RMark(version = version))
    constructor(uri: String, version: String, token: String) : this(uri, RMark(version = version, token = token))
    override fun toString() = " RContext ${super.toString()}"
}

internal class MayBeNotSupport : RMark(version = "") {
    override fun toString() = " MayBeNotSupport ${super.toString()}"
}

internal fun String.isCodeVersion() = endsWith("-AUTO") || endsWith("-CODE")
internal fun String.isDevVersion() = endsWith("-DEV")
internal fun String.codeVersionToVersion() = removeSuffix("-AUTO").removeSuffix("-CODE")
internal fun String.devVersionToVersion() = removeSuffix("-DEV")
