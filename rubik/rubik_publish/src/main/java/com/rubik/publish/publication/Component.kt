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
package com.rubik.publish.publication

import com.android.build.gradle.api.BaseVariant
import com.ktnail.gradle.DependencyType
import com.ktnail.gradle.flavorDependencyType
import com.ktnail.gradle.maven.MavenDependency
import com.ktnail.gradle.maven.Publication
import com.ktnail.gradle.p
import com.ktnail.x.*
import com.rubik.context.extra.Context
import com.rubik.publish.log.LogTags
import com.rubik.publish.publishComponentArtifactId
import com.rubik.publish.publishGroupId
import com.rubik.publish.task.target.ContextTaskTarget
import com.rubik.publish.extra.target
import com.rubik.publish.extra.toDependLibMavenDependencies
import org.gradle.api.Project
import java.io.File

/**
 * Model of business code of contexts.
 *
 * @since 1.4
 */
class Component(
    val project: Project,
    private val context: Context,
    baseName: String,
    variant: BaseVariant,
    override val versionCreator: VersionCreator,
    override val publicationFile: File
) : Publication() {

    override val publicationName = toPascal(baseName, "component", variant.name.camelToPascal())

    override val artifactId = context.publishComponentArtifactId(variant.name)

    override val groupId = context.publishGroupId

    override val sourceFile: File? = null

    override val addDependencyTypes = mutableListOf<String>().apply {
        add(DependencyType.API)
        add(DependencyType.IMPLEMENTATION)
        variant.name.camelToPascal().pascalToWords().forEach { flavorOrBuildType->
            add(flavorOrBuildType.flavorDependencyType(DependencyType.API))
            add(flavorOrBuildType.flavorDependencyType(DependencyType.IMPLEMENTATION))
        }
    }

    override val exceptDependencies: List<MavenDependency>
        get() = context.id.packed.mapNotNull { dep ->
            val variant = dep.variant
            val context = dep.context
            if (null != variant) {
                val group  = context.publishGroupId
                val artifact = context.publishComponentArtifactId (variant)
                Logger.p(LogTags.PUBLISH, project) { " EXCEPT DEPENDENCY:[$group:$artifact]" }
                MavenDependency(group, artifact)
            } else null
        }

    override val extraDependencies: List<MavenDependency>?
        get() = if (context.target.publishingBoth) {
            val dev = (context.target as? ContextTaskTarget.PublishContextTarget)?.devWhenPublishingComponent
            mutableListOf<MavenDependency>().apply {
                context.toDependLibMavenDependencies(version, dev).forEach { (type, dependency) ->
                    if (type == DependencyType.API || type == DependencyType.IMPLEMENTATION)
                        add(dependency)
                }
            }
        } else null

    companion object {
        fun String.nameToComponentArtifactId(variantName: String) = toSnake(
            false, "-", this, "component", variantName.pascalToSnake(false)
        ).replace("_", "-")
    }
}
