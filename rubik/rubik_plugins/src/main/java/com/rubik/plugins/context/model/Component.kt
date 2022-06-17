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
package com.rubik.plugins.context.model

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.camelToPascal
import com.ktnail.x.pascalToSnake
import com.ktnail.x.toPascal
import com.ktnail.x.toSnake
import com.rubik.plugins.basic.publish.maven.Publication
import com.rubik.plugins.basic.utility.DependencyType
import com.rubik.plugins.basic.utility.Ext
import com.rubik.plugins.basic.utility.flavorDependencyType
import com.rubik.plugins.basic.utility.propertyOr
import com.rubik.plugins.extension.context.ContextExtension
import org.gradle.api.Project
import java.io.File

/**
 * Model of business code of contexts.
 *
 * @since 1.4
 */
class Component(
    val project: Project,
    val context: ContextExtension,
    variant: BaseVariant,
    override val publicationFile: File
) : Publication {
    override val publicationName = toPascal(context.publishArtifactName, "component", variant.name.camelToPascal())
    override val devPublicationName = toPascal(context.publishArtifactName, "dev", "component", variant.name.camelToPascal())
    override val artifactId = context.publishComponentArtifactId(variant.name)
    override val groupId = context.publishGroupId
    override val version = project.propertyOr(Ext.R_PUB_VERSION) {
        context.versionToPublish.toString()
    }
    override val devVersion = project.propertyOr(Ext.R_PUB_VERSION) {
        context.versionToPublishDev.toString()
    }
    override val source: Publication? = null
    override val addDependencyTypes = arrayOf(
        DependencyType.API,
        DependencyType.IMPLEMENTATION,
        variant.name.flavorDependencyType(DependencyType.API),
        variant.name.flavorDependencyType(DependencyType.IMPLEMENTATION)
    )
    override val addDependencies: Array<Publication> = emptyArray()

    companion object {
        fun String.nameToComponentArtifactId(variantName: String) = toSnake(false, this, "component", variantName.pascalToSnake(false)).replace("_", "-")
    }
}
