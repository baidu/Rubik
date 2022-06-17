package com.rubik.plugins.extension.context.source

open class ProjectExtension {
    var path: String? = null
    var publishVersion: String? = null
    var publishDevVersion: String? = null
    var publishOriginalValue = false

    fun path(path: String) {
        this.path = path
    }

    fun publishVersion(version: String) {
        publishVersion = version
    }

    fun publishDevVersion(version: String) {
        publishDevVersion = version
    }

    fun supportOriginalValue(publish: Boolean) {
        publishOriginalValue = publish
    }

    override fun toString() = "ProjectExtension: path:$path publishVersion:$publishVersion publishDevVersion:$publishDevVersion supportOriginalValue:$publishOriginalValue"

}