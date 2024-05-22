package com.rubik.publish.extra

import com.ktnail.gradle.exception.MavenRepositoryNotSetException
import com.ktnail.gradle.maven.MavenConfig
import com.ktnail.gradle.p
import com.ktnail.gradle.propertyOr
import com.ktnail.x.Logger
import com.ktnail.x.camelToPascal
import com.ktnail.x.toCamel
import com.rubik.context.Ext
import com.rubik.context.log.LogTags
import com.rubik.publish.task.name.PublishTaskName
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File
import java.net.URI

fun Project.rubikTask(name: PublishTaskName): Task =
    task(name.fullName) {}.apply {
        group = name.group
        Logger.p(LogTags.CREATE_TASK, this@rubikTask) { " CREATE $group TASK (${name.fullName})" }
    }

fun Project.getKaptTask(variantName: String) =
    tasks.findByName(toCamel("kapt", variantName.camelToPascal(), "kotlin"))

val Project.publicationRecordDirRoot: File
    get() = File(project.propertyOr(Ext.RUBIK_PUBLICATION_RECORD_FILES_DIR) {
        project.rootProject.rootDir.absolutePath + File.separator + "rubik_libs"
    })

val Project.rubikMavenRepository: URI
    get() = uri(propertyOr(Ext.RUBIK_MAVEN_REPOSITORY) { throw MavenRepositoryNotSetException() })

val Project.rubikMavenLocalRepository: URI
    get() = uri(propertyOr(Ext.RUBIK_MAVEN_LOCAL_REPOSITORY) { "./rubik_maven_local" })

val Project.mavenConfig: MavenConfig
    get() = MavenConfig(
        project.rubikMavenRepository,
        propertyOr(Ext.RUBIK_MAVEN_USERNAME) { throw MavenRepositoryNotSetException() },
        propertyOr(Ext.RUBIK_MAVEN_PASSWORD) { throw MavenRepositoryNotSetException() },
        rubikMavenLocalRepository
    )