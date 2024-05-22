package com.rubik.dsl.context.source

import com.rubik.dsl.RDSL
import com.synccode.command.dsl.DSLGit


open class DSLProjectSource: DSLGit() {
    @set:RDSL
    var path: String? = null

    @set:RDSL
    var publishVersion: String? = null

    @set:RDSL
    var publishOriginalValue = false

    @RDSL
    fun path(path: String) {
        this.path = path
    }

    @RDSL
    fun publishVersion(version: String) {
        publishVersion = version
    }

    @RDSL
    fun supportOriginalValue(publish: Boolean) {
        publishOriginalValue = publish
    }

    override fun toString() = "ProjectExtension: path:$path publishVersion:$publishVersion supportOriginalValue:$publishOriginalValue"

}