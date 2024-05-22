package com.rubik.plugins.basic

import com.ktnail.gradle.p
import com.ktnail.x.LogTag
import com.ktnail.x.Logger
import com.rubik.context.extra.Context
import org.gradle.api.Project

object LogTags {
    val PREPARE_CONTEXT = LogTag("<RUBIK> PREPARE_CONTEXT ", Logger.Level.DEFAULT)
    val DO_PICK = LogTag("<RUBIK> DO_PICK ", Logger.Level.LOW)

    val PLUGIN = LogTag("<RUBIK> PLUGIN ", Logger.Level.DEFAULT)
    val TRANSFORM_CLASSES = LogTag("<RUBIK> TRANSFORM_CLASSES ", Logger.Level.LOW)
    val CHECK_CLASS = LogTag("<RUBIK> CHECK_CLASS ", Logger.Level.LOW)

    private val RUBIK_STEP_APPLY_CONFIG =
        LogTag("<< - RUBIK_STEP_CHANGED - >> < - APPLY CONFIG - >  ", Logger.Level.LOW)
    private val RUBIK_STEP_APPLY_CONTEXT =
        LogTag("<< - RUBIK_STEP_CHANGED - >>  < - APPLY CONTEXT - >  ", Logger.Level.LOW)
    private val RUBIK_STEP_PREPARE_CONTEXT =
        LogTag("<< - RUBIK_STEP_CHANGED - >>  < - PREPARE_CONTEXT - >  ", Logger.Level.LOW)
    private val RUBIK_STEP_CREATE_TASK =
        LogTag("<< - RUBIK_STEP_CHANGED - >> < - CREATE_TASK - >  ", Logger.Level.LOW)
    private val RUBIK_STEP_PICK =
        LogTag("<< - RUBIK_STEP_CHANGED - >> < - PICK - >  ", Logger.Level.LOW)

    fun logApplyConfigFinish(project: Project) =
        Logger.p(RUBIK_STEP_APPLY_CONFIG, project) {
            " FINISH ! \n"
        }

    fun logApplyContext(project: Project) =
        Logger.p(RUBIK_STEP_APPLY_CONTEXT, project) {
            " START ! \n"
        }

    fun logPrepareContextFinish(project: Project, context: Context) =
        Logger.p(RUBIK_STEP_PREPARE_CONTEXT, project) {
            "  <${context.uri}> FINISH ! \n"
        }

    fun logCreateTaskFinish(project: Project) =
        Logger.p(RUBIK_STEP_CREATE_TASK, project) {
            " FINISH ! \n"
        }

    fun logPickFinish(project: Project) =
        Logger.p(RUBIK_STEP_PICK, project) {
            " FINISH ! \n"
        }
}

