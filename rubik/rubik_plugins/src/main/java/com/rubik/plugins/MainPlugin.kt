package com.rubik.plugins

import com.rubik.plugins.basic.exception.RubikPluginNotApplyException
import com.rubik.plugins.context.AllContextController
import com.rubik.plugins.module.ModuleController
import org.gradle.api.Project

/**
 * The the plugin of rubik config project (gradle root).
 * Provide the ability to define contexts & ext configs.
 *
 * @ Since:1.3
 */
class MainPlugin : RubikPlugin() {

    private var _moduleController: ModuleController? = null
    private val moduleController: ModuleController
        get() = _moduleController ?: throw RubikPluginNotApplyException()

    private var _contextController: AllContextController? = null
    private val contextController: AllContextController
        get() = _contextController ?: throw RubikPluginNotApplyException()

    override fun apply(project: Project) {
        super.apply(project)

        _contextController = AllContextController(project)
        contextController.init()

        _moduleController = ModuleController(project)
        moduleController.init()

    }


}

