package com.rubik.publish

import com.ktnail.gradle.maven.PublicationType
import com.ktnail.gradle.propertyOr
import com.ktnail.x.pascalToCamel
import com.ktnail.x.toPascal
import com.ktnail.x.uriToPascal
import com.rubik.context.extra.Context
import com.rubik.context.Ext
import com.rubik.context.extra.globalConfig
import com.rubik.publish.extra.publicationRecords
import com.rubik.publish.exception.RubikPublishVersionNotSetException
import com.rubik.publish.publication.Component.Companion.nameToComponentArtifactId
import com.rubik.publish.publication.Lib.Companion.nameToLibArtifactId
import com.rubik.publish.publication.Lib.Companion.versionToCodeVersion
import com.rubik.publish.publication.Lib.Companion.versionToDevVersion
import com.rubik.publish.record.getPublishVersion
import com.rubik.context.publication.BuildType as ContextBuildType

// publish
val Context.allowAutoVersion
    get() = source.allowAutoVersion && project.propertyOr(Ext.RUBIK_AUTO_USE_PUBLICATION_RECORD, true)

private fun Context.configPublishVersion(tag: String? = null) =
    project.properties[Ext.R_PUB_VERSION]?.toString()
        ?: source.publishVersion
        ?: tag?.let { globalConfig.publishTagVersion(it) }
        ?: globalConfig.publishVersion(uri)
        ?: if (allowAutoVersion) publicationRecords.getPublishVersion(uri) else null

fun Context.publishVersion(publicationType: PublicationType?, byTag: String? = null) =
    when (publicationType) {
        PublicationType.FORMAL -> configPublishVersion(byTag)
        PublicationType.DEV -> configPublishVersion(byTag)?.versionToDevVersion()
        else -> configPublishVersion(byTag)
    } ?: throw  RubikPublishVersionNotSetException(uri)

fun Context.doNotPublishVersion() =
    configPublishVersion()?.versionToCodeVersion() ?: throw RubikPublishVersionNotSetException(uri)

val Context.publishArtifactName
    get() = name
val Context.publishTaskArtifactName
    get() = name.uriToPascal()
val Context.publishDevArtifactName
    get() = toPascal(name, "Dev")
val Context.publishGroupId
    get() = group

fun Context.publishLibArtifactId(libType: String) = name.nameToLibArtifactId(libType)
fun Context.publishComponentArtifactId(variantName: String) =
    name.nameToComponentArtifactId(variantName)

fun Context.buildTypeName() =
    publishArtifactName.uriToPascal().pascalToCamel() + com.rubik.context.publication.BuildType.CONTEXT_LIB_BUILD_TYPE_NAME

fun Context.compilerBuildTypeName() =
    publishArtifactName.uriToPascal().pascalToCamel() + ContextBuildType.CONTEXT_LIB_COMPILER_BUILD_TYPE_NAME

val Context.useResetCompiler: Boolean
    get() = project.propertyOr(Ext.RUBIK_USE_RESET_COMPILER, true) && !source.publishOriginalValue