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

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.ktnail.x.toCamel
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikPluginNotApplyException
import com.rubik.plugins.basic.utility.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

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
        project.rubikExtension
        project.addRubikRepository()
    }

    fun makeGeneratedDirs(onMakeDir: (File, BaseVariant) -> Unit) {
        project.forEachVariant { variant ->
            makeBuildGeneratedDir(variant).let { dir ->
                dir.mkdirs()
                onMakeDir(dir, variant)
            }
        }
    }

    private fun makeBuildGeneratedDir(variant: BaseVariant): File =
        File(project.file(File(project.buildDir, "generated/source/rubikKotlin")), variant.name)

    fun addImplementationJarDirDependency(dir: File) {
        project.addDirDependency(DependencyType.IMPLEMENTATION, dir)
    }

    fun addImplementationDependency(groupId: String, artifactId: String, version: String) {
        project.addDependency(DependencyType.IMPLEMENTATION, groupId, artifactId, version)
    }

    fun addImplementationDependency(flavor:String, groupId: String, artifactId: String, version: String) {
        project.addDependency(DependencyType.IMPLEMENTATION, flavor, groupId, artifactId, version)
    }

    fun addImplementationDependency(depProject: Project) {
        project.addProjectDependency(DependencyType.IMPLEMENTATION, depProject)
    }

    fun addImplementationDependency(flavor:String, depProject: Project) {
        project.addProjectDependency(flavor.flavorDependencyType(DependencyType.IMPLEMENTATION), depProject)
    }

    fun addAndroidTestImplementationDependency(groupId: String, artifactId: String, version: String) {
        project.addDependency(DependencyType.ANDROID_TEST_IMPLEMENTATION, groupId, artifactId, version)
    }

    fun addAndroidTestImplementationDependency(flavor:String, groupId: String, artifactId: String, version: String) {
        project.addDependency(DependencyType.ANDROID_TEST_IMPLEMENTATION, flavor, groupId, artifactId, version)
    }

    fun addCompileOnlyJarDirDependency(dir: File) {
        project.addDirDependency(DependencyType.COMPILE_ONLY, dir)
    }

    fun addCompileOnlyDependency(groupId: String, artifactId: String, version: String) {
        project.addDependency(DependencyType.COMPILE_ONLY, groupId, artifactId, version)
    }

    fun addAndroidTestCompileOnlyDependency(groupId: String, artifactId: String, version: String) {
        project.addDependency(DependencyType.ANDROID_TEST_COMPILE_ONLY, groupId, artifactId, version)
    }

    fun addAndroidTestCompileOnlyDependency(flavor:String, groupId: String, artifactId: String, version: String) {
        project.addDependency(toCamel(flavor, DependencyType.ANDROID_TEST_COMPILE_ONLY), groupId, artifactId, version)
    }



}