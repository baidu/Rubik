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
package com.rubik.plugins

import com.ktnail.gradle.p
import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.context.exception.RubikPluginNotApplyException
import com.rubik.plugins.basic.extra.*
import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 *  The super class of all gradle plugin in rubik.
 *
 *  @since 1.3
 */
abstract class RubikPlugin : Plugin<Project> {
    private var _project: Project? = null
    val project: Project
        get() = _project ?: throw RubikPluginNotApplyException()

    override fun apply(project: Project) {
        _project = project
        Logger.p(LogTags.PLUGIN, project) { " APPLIED PLUGIN (${this::class.java})" }
        project.rubik
        project.addRubikRepository()
    }

}