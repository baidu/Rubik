package com.rubik.publish.record

import com.ktnail.x.formatBy
import com.rubik.context.publication.BuildType
import com.rubik.publish.record.version.StandardVersion

open class PublicationRecord(
    val variant: String
) {
    var version: String? = null
    var task: String? = null
    var user: String? = null
    var time: Long? = null
    var dev = false

    var state = RecordStatus.LOAD_FROM_CONFIG

    constructor(
        variant: String,
        version: String,
        task: String,
        user: String,
        timestamp: Long,
        dev:Boolean
    ) : this(variant) {
        this.version = version
        this.task = task
        this.user = user
        this.time = timestamp
        this.dev = dev
        this.state = RecordStatus.NEW_UPDATE
    }

    val standardVersion: StandardVersion
        get() = StandardVersion(version ?: "")

    val key: String
        get() = variant

    val formatTime
        get() = time?.formatBy("yyyy-MM-dd HH:mm:ss")

    fun toHistory() = "[$version] - ${
        if (variant == BuildType.CONTEXT_LIB_BUILD_TYPE_NAME) "ContextLibs"
        else "Component:[$variant]"
    } - Time:[${formatTime}] - User:[${user}] - $task"

}

enum class RecordStatus {
    LOAD_FROM_CONFIG,
    NEW_UPDATE,
    FAIL_OUT
}