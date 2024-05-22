/**
 * Copyright (C) Baidu Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rubik.plugins.shell

import com.ktnail.gradle.androidExtension
import com.ktnail.gradle.p
import com.ktnail.gradle.propertyOr
import com.ktnail.x.Logger
import com.rubik.pick.shellPackingLibWhat
import com.rubik.plugins.RubikPlugin
import com.rubik.plugins.basic.LogTags
import com.rubik.context.Ext
import com.rubik.plugins.basic.extra.addRubikRouterDependency
import com.rubik.picker.ContextPicker
import com.rubik.picker.extra.pickedContextsContainer
import com.rubik.plugins.shell.checker.context.CheckContextVersionTransform
import com.rubik.plugins.shell.checker.router.CheckRouterVersionTransform
import com.rubik.plugins.shell.controller.ShellConfigGeneratingController
import com.rubik.plugins.shell.controller.ShellSourceGeneratingController
import org.gradle.api.Project

/**
 * The the plugin of rubik shell project (application).
 * Provide the ability to pick  contexts.
 *
 * @ Since:1.5
 */
class ShellPlugin : RubikPlugin() {
    private val picker: ContextPicker by lazy { ContextPicker() }

    override fun apply(project: Project) {
        super.apply(project)

        //  project.fuzzyApplyRubikConfigFiles(project.projectDir)

        ShellSourceGeneratingController(project).createTasks()

        ShellConfigGeneratingController(project).createTasks()

        project.afterEvaluate {
            doPickContexts()
            project.addRubikRouterDependency()
        }

        if (project.propertyOr(Ext.RUBIK_ENABLE_CHECK_ROUTER_VERSION, false) && CheckRouterVersionTransform.RULES.isNotEmpty()) {
            Logger.p(LogTags.CHECK_CLASS, project) { " CHECK_ROUTER_VERSION enable ! " }
            project.androidExtension?.registerTransform(CheckRouterVersionTransform(project))
        }

        if (project.propertyOr(Ext.RUBIK_ENABLE_CHECK_CONTEXT_VERSION, false) && CheckContextVersionTransform.RULES.isNotEmpty()) {
            Logger.p(LogTags.CHECK_CLASS, project) { " CHECK_CONTEXT_VERSION enable ! " }
            project.androidExtension?.registerTransform(CheckContextVersionTransform(project))
        }
    }

    private fun doPickContexts() {
        Logger.p(LogTags.DO_PICK, project) { " SHELL PICK COMPONENT :" }
        picker.pick(pickedContextsContainer.pickCases(), project)

        Logger.p(LogTags.DO_PICK, project) { " SHELL PICK CONTEXT LIBS :" }
        picker.pick(shellPackingLibWhat(), project)

    }
}

