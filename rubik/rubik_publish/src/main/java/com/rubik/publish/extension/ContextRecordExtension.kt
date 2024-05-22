package com.rubik.publish.extension

import com.rubik.context.publication.BuildType
import com.rubik.publish.record.ContextPublicationRecords
import com.rubik.publish.record.PublicationRecord
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

open class ContextRecordExtension(
    val project: Project
) {
    val records = ContextPublicationRecords()

    fun latest(closure: Closure<PublicationContextRecordExtension>) {
        PublicationContextRecordExtension { record ->
            record.dev = false
            records.latest.add(record)
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    fun latestDev(closure: Closure<PublicationContextRecordExtension>) {
        PublicationContextRecordExtension { record ->
            record.dev = true
            records.latest.add(record)
        }.apply {
            ConfigureUtil.configure(closure, this)
        }
    }

    fun history(
        vararg publications: String
    ) {
        publications.forEach { history->
            records.history.add(history)
        }
    }
}

open class PublicationContextRecordExtension(
    val result: (PublicationRecord) -> Unit
) {
    fun component(publication: String, closure: Closure<PublicationRecord>) {
        PublicationRecord(publication).apply{
            ConfigureUtil.configure(closure, this)
            result(this)
        }
    }
    fun contextLibs(closure: Closure<PublicationRecord>) {
        PublicationRecord(BuildType.CONTEXT_LIB_BUILD_TYPE_NAME).apply{
            ConfigureUtil.configure(closure, this)
            result(this)
        }
    }
}