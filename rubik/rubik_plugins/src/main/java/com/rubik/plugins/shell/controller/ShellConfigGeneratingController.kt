package com.rubik.plugins.shell.controller

import com.ktnail.x.firstOrNull
import com.rubik.context.extra.allContexts
import com.rubik.context.extra.rubikTask
import com.rubik.pick.shellPackingLibWhat
import com.rubik.picker.extra.getPickCase
import com.rubik.picker.extra.pickedContextsContainer
import com.rubik.publish.record.PublicationRecords
import com.rubik.publish.record.generate.file.RubikPublicationRecordFiles
import com.rubik.publish.record.updateComponent
import com.rubik.publish.record.updateContextLibs
import org.gradle.api.Project

class ShellConfigGeneratingController(
    private val project: Project
) {
    fun createTasks() {
        project.rubikTask("generateRubikPublicationRecord") { task ->
            task.doLast {
                RubikPublicationRecordFiles.generate(project)
            }
        }

        project.rubikTask("transRubikPickVersionToPublicationRecord") { task ->
            task.doLast {
                val publicationRecords: PublicationRecords = mutableMapOf()

                pickedContextsContainer.pickCases().forEach { case ->
                    case.pickCase.flavorHows.forEach { (_, how) ->
                        how.version?.let { version ->
                            publicationRecords.updateComponent(
                                project,
                                case.context.uri,
                                how.variant ?: "",
                                version,
                                false
                            )
                        }
                    }
                }
                allContexts.forEach { (_, context) ->
                    if (context.enableProvideRoute)
                        context.getPickCase(shellPackingLibWhat()).flavorHows.firstOrNull()?.version?.let { version ->
                            publicationRecords.updateContextLibs(
                                project,
                                context.uri,
                                version,
                                false
                            )
                        }
                }
                RubikPublicationRecordFiles.generate(project, publicationRecords)
            }
        }
    }
}