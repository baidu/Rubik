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
package com.rubik.plugins.root

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.toCamel
import com.rubik.plugins.RubikPlugin
import com.rubik.plugins.basic.exception.RubikMavenVariantNotSetException
import com.rubik.plugins.basic.exception.RubikMavenVersionNotSetException
import com.rubik.plugins.basic.exception.RubikPluginNotApplyException
import com.rubik.plugins.basic.exception.RubikProjectNotFoundException
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.extension.root.model.MavenMode
import com.rubik.plugins.extension.root.model.NoSourceMode
import com.rubik.plugins.extension.root.model.ProjectMode
import com.rubik.plugins.root.files.RubikExtSourceFiles
import com.rubik.plugins.root.pick.ContextPicker
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

/**
 * The the plugin of rubik root project (application).
 * Provide the ability to pick  contexts.
 *
 * @ Since:1.5
 */
class RootPlugin : RubikPlugin() {
    private var _picker: ContextPicker? = null
    private val picker: ContextPicker
        get() = _picker ?: throw RubikPluginNotApplyException()

    override fun apply(project: Project) {
        super.apply(project)

        project.fuzzyApplyRubikConfigFiles(project.projectDir)

        _picker = ContextPicker(project)

        makeGeneratedDirs { generateDir, variant ->
            variant.registerJavaGeneratingTask(
                makeGenerateFileTask(
                    variant,
                    generateDir
                ), generateDir
            )
        }

        project.afterEvaluate {
            doPickContexts()
            project.addRubikRouterDependency()
        }
    }


    private fun makeGenerateFileTask(variant: BaseVariant, generateDir: File): Task {
        return project.rubikTask(toCamel("generate", variant.name, "RubikRootSources")) { task ->
            task.doLast {
                doGenerate(generateDir, variant)
            }
        }
    }

    private fun doGenerate(generateDir: File, variant: BaseVariant) {
        RubikExtSourceFiles(
            picker.pickedContexts(variant),
            generateDir
        ).generate()
    }

    private fun doPickContexts(){
        picker.pick { picked, context, tagSource ->
            when (picked.mode) {
                is NoSourceMode -> { }
                is ProjectMode -> {
                    val path = context.firstPriorityProjectPath(tagSource)
                    if (path != project.path){
                        val projectToPick = try { project.project(path) } catch (e: Exception) { throw RubikProjectNotFoundException(context.uri, path) }
                        if (null != picked.forFlavor)
                            addImplementationDependency(picked.forFlavor, projectToPick)
                        else
                            addImplementationDependency(projectToPick)
                    }
                }
                is MavenMode -> {
                    val version = context.firstPriorityMavenVersion(picked, tagSource) ?: throw RubikMavenVersionNotSetException(context.uri)
                    val variant = context.firstPriorityMavenVariant(picked, tagSource) ?: throw RubikMavenVariantNotSetException(context.uri)
                    if (null != picked.forFlavor)
                        addImplementationDependency(picked.forFlavor, context.publishGroupId, context.publishComponentArtifactId(variant), version)
                    else
                        addImplementationDependency(context.publishGroupId, context.publishComponentArtifactId(variant), version)
                }
            }
        }
    }


}

