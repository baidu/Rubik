package com.rubik.publish.record.generate.file

import BY_PLUGIN_VERSION
import com.ktnail.gradle.propertyOfT
import com.ktnail.x.*
import com.rubik.context.Ext
import com.rubik.context.extra.rubikExtensionName
import com.rubik.context.publication.BuildType
import com.rubik.publish.extra.publicationRecordDirRoot
import com.rubik.publish.extra.publicationRecords
import com.rubik.publish.record.ContextPublicationRecords
import com.rubik.publish.record.PublicationRecord
import com.rubik.publish.record.PublicationRecords
import org.gradle.api.Project
import java.io.File

/**
 *  Generate rubik publication record files.
 *
 *  @since 1.10
 */
class RubikPublicationRecordFiles(
    private val project: Project,
    private val records: PublicationRecords,
    private val generatedDir: File
) {
    companion object{
        fun generate(
            project: Project,
            pubRecords: PublicationRecords? = null
        ) {
            RubikPublicationRecordFiles(
                project,
                pubRecords ?: publicationRecords,
                project.publicationRecordDirRoot
            ).generate()
        }
    }

    fun generate() {
        records.forEach { (uri, contextRecords) ->
            doGenerate(
                uri,
                contextRecords,
                File(generatedDir.absolutePath + File.separator + "rubik-${uri.uriToSnake()}-publication-records.gradle")
            )
        }
    }

    private fun doGenerate(uri: String, contextRecords: ContextPublicationRecords, file: File) {
        if (!contextRecords.isEmpty) {
            val lines = mutableListOf<String>()
            lines.addAll(KDoc.publicationRecord(uri))
            lines.add("${project.rubikExtensionName} {")
            lines.addLevel(1, "publications('${uri}') {")
            lines.addRecords(contextRecords.formalLatest, "latest")
            lines.addRecords(contextRecords.devLatest, "latestDev")
            lines.addHistory(contextRecords.history)
            lines.addLevel(1, "}")
            lines.add("}")
            file.replace { tmp->
                tmp.writeLines(lines)
            }
        }
    }

    private fun MutableList<String>.addRecords(
        list: List<PublicationRecord>,
        functionName: String
    ) {
        if (list.isNotEmpty()) {
            addLevel(2,"$functionName {")
            list.sortedBy { record -> record.time }.reversed().forEach { record ->
                if (record.key == BuildType.CONTEXT_LIB_BUILD_TYPE_NAME)
                    addLevel(3, "contextLibs {")
                else
                    addLevel(3, "component('${record.key}') {")
                addLevel(4,"version = '${record.version}' ")
                addLevel(4,"time = ${record.time} /* ${record.formatTime} */")
                addLevel(4,"user = '${record.user}' ")
                addLevel(4,"task = '${record.task}' ")
                addLevel(3, "}")
            }
            addLevel(2, "}")
        }
    }

    private fun MutableList<String>.addHistory(
        history: MutableList<String>
    ) {
        if (history.isNotEmpty()) {
            val configSubTo = project.propertyOfT<Int>(Ext.RUBIK_PUBLICATION_RECORD_HISTORY_MAX_SIZE) ?: 10
            val subHistory = history.subList(0, if (history.size > configSubTo) configSubTo else history.size)
            if(subHistory.isNotEmpty()){
                addLevel(2, "history (")
                subHistory.forEachIndexed() { index, record ->
                    addLevel(3, if (index != subHistory.size - 1) "'${record}'," else "'${record}'")
                }
                addLevel(2, ")")
            }
        }
    }

    object KDoc {
        fun publicationRecord(uri: String) =
            updateFileKDoc("generated by Rubik Gradle Plugin (${BY_PLUGIN_VERSION}).", null, "uri" to "[$uri]").kdocToLines()
    }
}
