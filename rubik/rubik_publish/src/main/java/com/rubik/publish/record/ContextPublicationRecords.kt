package com.rubik.publish.record

import BY_PLUGIN_VERSION
import com.ktnail.x.now
import com.ktnail.x.sysMyName
import com.rubik.context.publication.BuildType
import com.rubik.publish.record.generate.file.RubikPublicationRecordFiles
import com.rubik.publish.record.version.StandardVersion
import org.gradle.api.Project

typealias PublicationRecords = MutableMap<String, ContextPublicationRecords>

class ContextPublicationRecords {
    val latest = mutableListOf<PublicationRecord>()
    val history = mutableListOf<String>()

    val isEmpty
        get() = latest.isEmpty()

    val formalLatest: List<PublicationRecord>
        get() = latest.filter { record -> !record.dev && record.state != RecordStatus.FAIL_OUT }

    val devLatest: List<PublicationRecord>
        get() = latest.filter { record -> record.dev && record.state != RecordStatus.FAIL_OUT }

    fun update(record: PublicationRecord) {
        latest.find { latestRecord -> latestRecord.dev == record.dev && latestRecord.key == record.key }?.let { out ->
            out.state = RecordStatus.FAIL_OUT
            if (!out.dev)
                history.add(0, out.toHistory())
        }
        latest.add(record)
    }

    fun biggestLatestVersion(): StandardVersion? {
        return latest.fold<PublicationRecord, StandardVersion?>(null) { acc, record ->
            if (record.state == RecordStatus.NEW_UPDATE || record.dev) {
                acc
            } else if (null == acc) {
                record.standardVersion
            } else {
                record.standardVersion.biggerOne(acc)
            }
        }
    }

    fun latestVersion(key: String, dev: Boolean): String? =
        latest.find { record -> record.dev == dev && record.key == key && record.state != RecordStatus.NEW_UPDATE }?.version

}

fun PublicationRecords.updateContextLibs(
    project: Project,
    uri: String,
    version: String,
    dev: Boolean,
    generate: Boolean = false
) {
    update(project, uri, BuildType.CONTEXT_LIB_BUILD_TYPE_NAME, version, dev, generate)
}

fun PublicationRecords.updateComponent(
    project: Project,
    uri: String,
    variant: String,
    version: String,
    dev: Boolean,
    generate: Boolean = false
) {
    update(project, uri, variant, version, dev, generate)
}
private fun PublicationRecords.update(
    project: Project,
    uri: String,
    key: String,
    version: String,
    dev: Boolean,
    generate: Boolean
) {
    getOrPut(uri) {
        ContextPublicationRecords()
    }.let { contextRecord ->
        val task = "PluginVersion:[$BY_PLUGIN_VERSION] Task:[${project.gradle.startParameter.taskNames.joinToString(",")}]"
        val user = sysMyName()
        val time = now()
        contextRecord.update(PublicationRecord(key, version, task, user, time, dev))
        if (generate) {
            RubikPublicationRecordFiles.generate(project, mutableMapOf(uri to contextRecord))
        }
    }
}

fun PublicationRecords.getPublishVersion(uri: String): String? {
    return this[uri]?.biggestLatestVersion()?.plusOne()
}