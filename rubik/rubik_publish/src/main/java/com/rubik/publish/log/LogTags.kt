package com.rubik.publish.log

import com.ktnail.gradle.maven.PublicationType
import com.ktnail.x.LogTag
import com.ktnail.x.Logger

object LogTags {
    val PUBLISH = LogTag("<RUBIK> PUBLISH ", Logger.Level.DEFAULT)

    private val RUBIK_STEP_TASK_GRAPHIC =
        LogTag("<< - RUBIK_STEP_CHANGED - >> < - TASK_GRAPHIC - >  ", Logger.Level.LOW)
    private val RUBIK_STEP_PUBLISH =
        LogTag("<< - RUBIK_STEP_CHANGED - >> < - PUBLISH - >  ", Logger.Level.DEFAULT)

    fun logTaskGraphicFinish(type: String,name:String) =
        Logger.dta(RUBIK_STEP_TASK_GRAPHIC) {
            "  Type:[${type}] Name:[${name}] FINISH ! \n"
        }

    fun logPublishFinish(path: String, publicationType: PublicationType?) =
        Logger.dta(RUBIK_STEP_PUBLISH) {
            " $path DEV:${publicationType == PublicationType.DEV}  SUCCEEDED !!! \n"
        }
}