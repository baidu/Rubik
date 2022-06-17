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
package com.rubik.plugins.basic.publish.maven

import com.rubik.plugins.basic.exception.RubikMavenPublishingTaskNotFoundException
import com.rubik.plugins.basic.exception.RubikMavenRepositoryNotSetException
import com.rubik.plugins.basic.exception.RubikNoMavenPublishingExtensionFoundException
import com.rubik.plugins.basic.utility.*
import groovy.util.Node
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Dependency
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

open class PublishingTaskProvider(val project: Project) {
    companion object {
        const val RUBIK_REMOTE_REPOSITORY_NAME = "RubikRemoteMaven"
        const val RUBIK_LOCAL_REPOSITORY_NAME = "RubikLocalMaven"
    }

    private var _extension: PublishingExtension? = null
    protected val extension: PublishingExtension
        get() {
            if (null == _extension){
                _extension = (project.extensions.getByName("publishing") as? PublishingExtension)
                setRepositories()
            }
            return _extension ?: throw RubikNoMavenPublishingExtensionFoundException();
        }

    fun getTask(pub: Publication, dev: Boolean = false): Task {
        val publicationName = if (dev) pub.devPublicationName else pub.publicationName
        extension.publications { publications ->
            publications.register(
                publicationName,
                MavenPublication::class.java
            ) { publication ->
                publication.artifactId = pub.artifactId
                publication.groupId = pub.groupId
                publication.version = if (dev) pub.devVersion else pub.version
                publication.artifact(pub.publicationFile)
                pub.source?.let { source ->
                    publication.artifact(source.publicationFile) {
                        it.classifier = "sources"
                    }
                }
                if (pub.addDependencies.isNotEmpty() || pub.addDependencyTypes.isNotEmpty()) {
                    publication.pom { pom ->
                        pom.withXml { xmlProvider ->
                            xmlProvider.asNode().appendNode("dependencies").apply {
                                appendDependencyPom(pub.addDependencies, dev)
                                appendDependencyPom(project, pub.addDependencyTypes)
                            }
                        }
                    }
                }
            }
        }
        return project.tasks.getByName(
            if (dev) getPublishingLocalTaskName(publicationName)
            else getPublishingTaskName(publicationName)
        ) ?: throw RubikMavenPublishingTaskNotFoundException(
            publicationName
        )
    }

    private fun setRepositories() {
        extension.apply {
            repositories { repositories ->
                repositories.maven { maven ->
                    maven.name = RUBIK_REMOTE_REPOSITORY_NAME
                    maven.url = project.rubikMavenRepository
                    maven.credentials { credentials ->
                        credentials.username = project.propertyOr(Ext.RUBIK_MAVEN_USERNAME) { throw RubikMavenRepositoryNotSetException() }
                        credentials.password = project.propertyOr(Ext.RUBIK_MAVEN_PASSWORD) { throw RubikMavenRepositoryNotSetException() }
                    }
                }
                repositories.maven { maven ->
                    maven.name = RUBIK_LOCAL_REPOSITORY_NAME
                    maven.url = project.rubikMavenLocalRepository
                }
            }
        }
    }

    private fun Node.appendDependencyPom(
        addDependencies: Array<Publication>,
        dev: Boolean
    ) {
        addDependencies.forEach { pub ->
            appendNode("dependency").apply {
                appendNode("groupId", pub.groupId)
                appendNode("artifactId", pub.artifactId)
                appendNode("version",  if (dev) pub.devVersion else pub.version)
            }
        }
    }

    private fun Node.appendDependencyPom(
        project: Project,
        addDependencyTypes: Array<String>
    ) {
        addDependencyTypes.forEach { type ->
            project.getDependencySet(type)?.forEach { dependency ->
                this.appendDependencyPom(dependency)
            }
        }
    }

    private fun Node.appendDependencyPom(dependency: Dependency) {
        if (dependency.legal()) {
            appendNode("dependency").apply {
                appendNode("groupId", dependency.group)
                appendNode("artifactId", dependency.name)
                appendNode("version", dependency.version)
            }
        }
    }

    private fun Dependency.legal() = group.legalDependency() && name.legalDependency() && version.legalDependency()

    private fun String?.legalDependency() = this?.isNotBlank() == true && "unspecified" != this

    protected fun getPublishingTaskName(publicationName: String) = "publish${publicationName}PublicationTo${RUBIK_REMOTE_REPOSITORY_NAME}Repository"

    protected fun getPublishingLocalTaskName(publicationName: String) = "publish${publicationName}PublicationTo${RUBIK_LOCAL_REPOSITORY_NAME}Repository"
}