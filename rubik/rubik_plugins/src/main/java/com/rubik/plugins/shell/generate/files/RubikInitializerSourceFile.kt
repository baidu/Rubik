package com.rubik.plugins.shell.generate.files

import com.ktnail.x.NameBox
import com.rubik.plugins.basic.Constants
import com.rubik.plugins.basic.utility.addRGeneratedAnnotation
import com.rubik.picker.container.ContextPickCase
import com.rubik.context.router.RouterRegister
import com.rubik.publish.publishArtifactName
import com.squareup.kotlinpoet.*
import java.io.File

/**
 *  Generate rubik Initializer.
 *
 *  @since 1.3
 */
class RubikInitializerSourceFile(
    private val contextPickCases: List<ContextPickCase>,
    private val generatedDir: File
) {
    fun generate() {
        FileSpec.builder(
            Constants.Router.GENERATE_PACKAGE_NAME,
            Constants.Router.GENERATE_RUBIK_INITIALIZER_FILE_NAME
        ).generateRubikInit(
            contextPickCases
        ).build().writeTo(generatedDir)
    }

    private fun FileSpec.Builder.generateRubikInit(contextPickCases: List<ContextPickCase>) = apply {
        val aggregateNormal = mutableListOf<String>()
        val aggregateReflect = mutableListOf<String>()
        val nameBox = NameBox()
        contextPickCases.forEach { value ->
            if (value.context.enableProvideRoute) {
                when (value.pickCase.register) {
                    RouterRegister.NEW_INSTANCE -> {
                        val className =
                            Constants.Aggregate.Declare.makeAggregateClassName(value.context.publishArtifactName)
                        val memberName = MemberName(
                            Constants.Aggregate.Declare.makeAggregatePackageName(value.context.uri),
                            className
                        )
                        val useName = nameBox.useName(className)
                        if (useName != className) {
                            addAliasedImport(memberName, useName)
                        }
                        addImport(memberName.packageName, memberName.simpleName)
                        aggregateNormal.add(useName)
                    }
                    RouterRegister.REFLECT_INSTANCE -> {
                        val className =
                            Constants.Aggregate.Declare.makeAggregateClassName(value.context.publishArtifactName)
                        val memberName = MemberName(
                            Constants.Aggregate.Declare.makeAggregatePackageName(value.context.uri),
                            className
                        )
                        aggregateReflect.add(memberName.canonicalName)
                    }
                    else -> {}
                }
            }
        }

        addFunction(
            FunSpec.builder(
                Constants.Router.INIT_FUNCTION_NAME
            ).receiver(
                ClassName.bestGuess(Constants.Router.RUBIK_CLASS_NAME)
            ).addNormalRegisterStatement(
                aggregateNormal
            ).addReflectRegisterStatement(
                aggregateReflect
            ).addRGeneratedAnnotation(
                "init"
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Router.KEEP_ANNOTATION_CLASS_NAME))
                    .build()
            ).addKdoc(
                Constants.KDoc.init()
            ).build()
        )
    }

    private fun FunSpec.Builder.addNormalRegisterStatement(names: List<String>) = apply {
        names.forEach { className ->
            addStatement("$className.${Constants.Aggregate.REGISTER_FUNCTION_NAME}()")
        }
    }

    private fun FunSpec.Builder.addReflectRegisterStatement(names: List<String>) = apply {
        names.forEach { className ->
            addStatement("${Constants.Router.METHOD_REGISTER_NAME}(\"$className\")")
        }
    }
}


