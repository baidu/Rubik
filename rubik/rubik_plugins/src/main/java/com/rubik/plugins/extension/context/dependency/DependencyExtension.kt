package com.rubik.plugins.extension.context.dependency

import com.ktnail.x.uri.buildUri
import com.rubik.plugins.context.model.Lib.Companion.nameToLibArtifactId
import com.rubik.plugins.context.declare.ContextDeclare
import com.rubik.plugins.basic.exception.RubikDefaultSchemeNotSetException
import com.rubik.plugins.basic.exception.RubikMavenDependencyVersionNotSetException
import com.rubik.plugins.extension.RubikExtension

open class DependencyExtension(val rubik: RubikExtension) {
    private var _uri: String? = null
    private var _version: String? = null
    private var _supportOriginalValue = false

    private var _authority: String = ""
    private var _name: String = ""
    private var _group: String = ""

    fun setUri(uri: String) {
        _authority = uri.substringAfterLast("://")
        _name = _authority.substringAfterLast(".")
        _group = _authority.substringBeforeLast(".")
        _uri = uri
    }

    fun setAuthority(authority: String) {
        _authority = authority
        _name = _authority.substringAfterLast(".")
        _group = _authority.substringBeforeLast(".")
        _uri = buildUri(
            rubik.globalConfig.scheme ?: throw  RubikDefaultSchemeNotSetException(),
            authority
        )
    }

    fun version(version: String) {
        _version = version
    }

    fun supportOriginalValue(support: Boolean) {
        _supportOriginalValue = support
    }

    val groupId
        get() = _group

    fun versionToDependency(dev: Boolean?): String {
        return (if (null != dev) rubik.globalConfig.getMavenVersion(uri, _version, dev = dev)
        else rubik.globalConfig.getMavenVersion(uri, _version)) ?: throw RubikMavenDependencyVersionNotSetException()
    }

    val uri
        get() = _uri.toString()

    val supportOriginalValue
        get() = _supportOriginalValue

    fun artifactId(libTYpe: String) = _name.nameToLibArtifactId(libTYpe)

    fun toUriDeclare() = _uri?.let { uri -> ContextDeclare(uri, null, "", versionToDependency(false)) }

    override fun toString() = "DependencyExtension: uri:$_uri version:${versionToDependency(false)} supportOriginalValue:$_supportOriginalValue"
}