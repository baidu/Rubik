package com.rubik.plugins.extension

import com.ktnail.gradle.maven.MavenDependency
import com.rubik.publish.extra.PublishModuleInjector
import com.rubik.context.extra.Context
import com.rubik.context.extra.ContextModuleInjector
import com.rubik.context.container.ContextsContainer
import com.rubik.dsl.global.GlobalExtension
import com.rubik.pick.MavenMode
import com.rubik.pick.PickHow
import com.rubik.pick.What
import com.rubik.pick.contextDependLibWhat
import com.rubik.picker.MavenPicker
import com.rubik.picker.extra.PickerModuleInjector
import com.rubik.picker.container.ContextPickHowContainer
import com.rubik.picker.container.PickedContextsContainer
import com.rubik.picker.exception.RubikMavenDependencyVersionNotSetException
import com.rubik.picker.exception.RubikMavenVariantNotSetException
import com.rubik.picker.exception.RubikMavenVersionNotSetException
import com.rubik.publish.allowAutoVersion
import com.rubik.publish.publication.Lib.Companion.versionToDevVersion
import com.rubik.publish.publishComponentArtifactId
import com.rubik.publish.publishGroupId
import com.rubik.publish.publishLibArtifactId
import com.rubik.publish.record.PublicationRecords
import com.rubik.publish.task.PublishContextTasksContainer
import com.rubik.publish.task.target.TaskTargetContainer
import org.gradle.api.Project

/**
 *  Rubik config extension of gradle plugins.
 *
 *  @since 1.3
 */
open class RubikSingletonExtension(
    val project: Project
) : ContextModuleInjector,
    PublishModuleInjector,
    PickerModuleInjector {

    // global
    val globalExtension = GlobalExtension(project)

    // context
    override  val contextsContainer = ContextsContainer()
    override val globalConfig = globalExtension.config

    // publish
    override val taskTargetContainer =  TaskTargetContainer()
    override val publicationRecords: PublicationRecords = mutableMapOf()
    override val publishTasksContainer = PublishContextTasksContainer()

    override fun contextToDependLibMavenDependencies(
        context: Context,
        version: String,
        dev: Boolean?
    ): List<Pair<String, MavenDependency>> =
        MavenPicker(
            contextDependLibWhat(context.uri, dev),
            MavenMode(version, null),
            context
        ).toDependencies(null)

    // picker
    override val pickedContextsContainer = PickedContextsContainer()
    override val pickHowContainer = ContextPickHowContainer()

    override fun contextToMavenDependency(
        context: Context,
        item: What,
        maven: PickHow,
        dev: Boolean?,
        forFlavor: String?
    ): MavenDependency {
        val devMode = dev ?: globalConfig.devEnable
        val variant = maven.variant

        val artifactId = when (item) {
            is What.Component -> {
                variant ?: throw RubikMavenVariantNotSetException(context.uri)
                context.publishComponentArtifactId(variant)
            }
            is What.Lib -> {
                context.publishLibArtifactId(item.type)
            }
        }

        val version = if (null != maven.version) {
            if (devMode) maven.version?.versionToDevVersion() else maven.version
        } else if (context.allowAutoVersion) {
            publicationRecords.getVersion(context.uri, item, maven, devMode)
        } else null

        version ?: throw when (item) {
            is What.Component -> RubikMavenVersionNotSetException(context.uri)
            else -> RubikMavenDependencyVersionNotSetException(context.uri)
        }

        return  MavenDependency(context.publishGroupId, artifactId, version, forFlavor)
    }

    private fun PublicationRecords.getVersion(uri: String, what: What, maven: PickHow, dev: Boolean): String? {
        val key = (if (what is What.Lib) com.rubik.context.publication.BuildType.CONTEXT_LIB_BUILD_TYPE_NAME else maven.variant) ?: return null
        return this[uri]?.latestVersion(key, dev)
    }
}