package com.rubik.plugins.basic.utility

import com.android.builder.model.BuildType
import com.ktnail.x.pascalToCamel
import com.ktnail.x.uriToPascal
import com.rubik.plugins.basic.exception.RubikProjectPathNotSetException
import com.rubik.plugins.context.ContextPlugin
import com.rubik.plugins.extension.context.ContextExtension
import com.rubik.plugins.extension.context.source.SourceExtension
import com.rubik.plugins.extension.root.model.MavenMode
import com.rubik.plugins.extension.root.model.Picked

fun ContextExtension.firstPriorityMavenVersion(picked: Picked?, tagSource: SourceExtension?) =
    (picked?.mode as? MavenMode)?.maven?.version ?: tagSource?.maven?.version ?: mavenVersion

fun ContextExtension.firstPriorityMavenVariant(picked: Picked?, tagSource: SourceExtension?) =
    (picked?.mode as? MavenMode)?.maven?.variant ?: tagSource?.maven?.variant ?: mavenVariant

fun ContextExtension.firstPriorityProjectPath(tagSource: SourceExtension?) =
    tagSource?.project?.path ?: projectPath ?: throw RubikProjectPathNotSetException(
        uri
    )

fun ContextExtension.buildTypeName() =
    publishArtifactName.uriToPascal().pascalToCamel() + ContextPlugin.CONTEXT_LIB_BUILD_TYPE_NAME

fun BuildType.isContextLibBuildType() = name.endsWith(ContextPlugin.CONTEXT_LIB_BUILD_TYPE_NAME)

val ContextExtension.useResetCompiler: Boolean
    get() = rubik.project.propertyOr(Ext.RUBIK_USE_RESET_COMPILER, false) && !publishOriginalValue