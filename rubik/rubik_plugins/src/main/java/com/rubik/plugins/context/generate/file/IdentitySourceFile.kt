package com.rubik.plugins.context.generate.file

import com.ktnail.x.packageNameToFilePath
import com.rubik.annotations.source.RComponent
import com.rubik.context.extra.Context
import com.rubik.context.id.ContextIdHolder
import com.rubik.plugins.basic.Constants
import com.rubik.plugins.basic.utility.addRGeneratedAnnotation
import com.rubik.plugins.basic.utility.noSpaces
import com.rubik.plugins.basic.utility.toArrayCode
import com.rubik.plugins.basic.utility.toListOfCode
import com.rubik.publish.publishArtifactName
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class IdentitySourceFile(
    private val context: Context,
    private val pubVersion: String,
    private val pubVariant: String,
    private val generatedDir: File
) {
    fun generate() {
        val className = Constants.Identity.contextIdName(context.publishArtifactName)
        cleanSourceFiles()
        FileSpec.builder(
            Constants.Identity.makeComponentPackageName(context.uri),
            className
        ).addImport(
            Constants.Identity.COMPONENT_ID_SUPER_PACKAGE_NAME,
            Constants.Identity.COMPONENT_ID_SUPER_SIMPLE_CLASS_NAME
        ).addImport(
            Constants.Identity.COMPONENT_ID_SUPER_PACKAGE_NAME,
            Constants.Identity.CONTEXT_ID_SUPER_SIMPLE_CLASS_NAME
        ).addComponentId(
                context,
                className,
                pubVersion,
                pubVariant
            ).build().writeTo(generatedDir)
    }

    private fun cleanSourceFiles() {
        File("${generatedDir.absolutePath}${File.separator}${Constants.Identity.makeComponentPackageName(context.uri).packageNameToFilePath()}").deleteRecursively()
    }
}

private fun FileSpec.Builder.addComponentId(
    context: Context,
    className: String,
    pubVersion: String,
    pubVariant: String
) = apply {
    addType(
        TypeSpec.classBuilder(className).superclass(
            ClassName.bestGuess(Constants.Identity.COMPONENT_ID_SUPER_CLASS_NAME)
        ).addSuperclassConstructorParameter(
            ("uri = %S, version = %S, variant =%S,\n" +
                    "dependencies = ${context.id.idDependenciesCode},\n" +
                    "packed = ${context.id.idPackedCode}\n    "
                    ).noSpaces(),
            context.uri,
            pubVersion,
            pubVariant
        ).addAnnotation(
            AnnotationSpec.builder(RComponent::class.java)
                .addMember("uri = %S", context.uri)
                .addMember("version = %S", pubVersion)
                .addMember("variant = %S", pubVariant)
                .addMember("dependencies = [${context.id.dependenciesCode}]")
                .addMember("packed = [${context.id.packedCode}]")
                .build()
        ).addRGeneratedAnnotation(
            "component_id"
        ).addAnnotation(
            AnnotationSpec.builder(ClassName.bestGuess(Constants.Router.KEEP_ANNOTATION_CLASS_NAME))
                .build()
        ).addKdoc(
            Constants.KDoc.componentId(context.uri, pubVersion)
        ).build()
    )
}

private val ContextIdHolder.createComponentIdCode
    get() = "${Constants.Identity.COMPONENT_ID_SUPER_SIMPLE_CLASS_NAME}(uri = \"$uri\", version = \"$version\", variant = \"$variant\")".noSpaces()

private val ContextIdHolder.createContextIdCode
    get() = "${Constants.Identity.CONTEXT_ID_SUPER_SIMPLE_CLASS_NAME}(uri = \"$uri\", version = \"$version\")".noSpaces()

private val ContextIdHolder.idDependenciesCode
    get() = touched.toListOfCode { dep ->
        dep.createContextIdCode
    }

private val ContextIdHolder.idPackedCode
    get() = packed.toListOfCode { dep ->
        dep.createComponentIdCode
    }

private val ContextIdHolder.dependenciesCode
    get() = touched.toArrayCode{ dep -> "\"${dep.uri}@${dep.version}\"" }


private val ContextIdHolder.packedCode
    get() = packed.toArrayCode{ dep -> "\"${dep.uri}@${dep.version}@${dep.variant}\"" }