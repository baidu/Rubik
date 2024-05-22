package com.rubik.plugins.context.controller

import com.ktnail.gradle.propertyOr
import com.rubik.context.Ext
import com.rubik.context.extra.rubikTask
import com.rubik.picker.extra.pickedContextsContainer
import com.rubik.plugins.context.generate.file.ProjectSettingsFile
import org.gradle.api.Project

class ProjectSyncController(private val rootProject: Project) {
    companion object {
        const val PROJECT_SYNC_TASK_NAME = "RUBIK-sync-projects"
    }

    private val rootTask by lazy { rootProject.rubikTask(PROJECT_SYNC_TASK_NAME) {} }

    fun init() {
        val paths = mutableMapOf<String, Boolean>()
        val syncCodeEnable = rootProject.propertyOr(Ext.RUBIK_SYNC_CODE_ENABLE, true)
        val generateSettings = rootProject.propertyOr(Ext.RUBIK_AUTO_GENERATE_SETTINGS, true)
        if (syncCodeEnable || generateSettings) {
            pickedContextsContainer.pickCases().forEach { pickedContext ->
                val source = pickedContext.context.source
                val path = source.projectPath
                if (null != path) {
                    if (syncCodeEnable) {
                        rootTask.doLast {
                            source.codeSyncer?.invoke()?.execute()
                        }
                    }
                    if (generateSettings) paths[path] = pickedContext.pickCase.projectMode
                }
            }
            if (generateSettings)
                rootTask.doLast {
                    ProjectSettingsFile(rootProject, paths).generate()
                }
        }
    }
}