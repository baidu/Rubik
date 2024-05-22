package com.rubik.plugins

import com.ktnail.x.Logger
import com.ktnail.x.now
import com.rubik.publish.extra.PublishModule
import com.rubik.context.extra.ContextModule
import com.rubik.context.extra.libTmpDirRoot
import com.rubik.picker.extra.PickerModule
import com.rubik.plugins.basic.extra.logLevel
import com.rubik.plugins.basic.extra.logWriteToFile
import com.rubik.plugins.basic.extra.rubikSingleton
import com.rubik.plugins.context.controller.AllContextController
import org.gradle.api.Project
import java.io.File

/**
 * The the plugin of rubik config project (gradle root).
 * Provide the ability to define contexts & ext configs.
 *
 * @ Since:1.3
 */
class MainPlugin : RubikPlugin() {

    override fun apply(project: Project) {
        Logger.level = project.logLevel
        Logger.writeToFile =
            if (project.logWriteToFile)
                File(project.libTmpDirRoot.absolutePath + File.separator + now() + ".log")
            else null

        ContextModule.inject { project.rubikSingleton }
        PublishModule.inject { project.rubikSingleton }
        PickerModule.inject { project.rubikSingleton }

        super.apply(project)

        AllContextController(project).init()

    }

}

