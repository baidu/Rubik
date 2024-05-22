package com.rubik.plugins.shell.generate.files

import com.rubik.context.extra.Context
import com.rubik.plugins.basic.Constants
import com.rubik.plugins.basic.utility.addRGeneratedAnnotation
import com.rubik.plugins.basic.utility.noSpaces
import com.rubik.picker.container.ContextPickCase
import com.rubik.context.router.RouterRegister
import com.rubik.publish.publishArtifactName
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import java.io.File


/**
 *  Generate rubik ID checker.
 *
 *  @since 1.9
 */
class RubikIdCheckerSourceFile(
    private val contextPickCases: List<ContextPickCase>,
    private val allContexts: Map<String, Context>,
    private val generatedDir: File
) {
    fun generate() {
        FileSpec.builder(
            Constants.Router.GENERATE_PACKAGE_NAME,
            Constants.Router.GENERATE_RUBIK_ID_CHECKER_FILE_NAME
        ).addImport(
            Constants.IdChecker.ID_CHECKER_PACKAGE_NAME,
            Constants.IdChecker.ID_CHECKER_SIMPLE_CLASS_NAME
        ).generateRubikCheckIds(
            contextPickCases,
            allContexts
        ).build().writeTo(generatedDir)
    }

    private fun FileSpec.Builder.generateRubikCheckIds(
        contextPickCases: List<ContextPickCase>,
        allContexts: Map<String, Context>
    ) = apply {
        addFunction(
            FunSpec.builder(
                Constants.Router.ID_CHECKER_FUNCTION_NAME
            ).receiver(
                ClassName.bestGuess(Constants.Router.RUBIK_CLASS_NAME)
            ).returns(
                ClassName.bestGuess("${Constants.IdChecker.ID_CHECKER_PACKAGE_NAME}.${Constants.IdChecker.ID_CHECKER_SIMPLE_CLASS_NAME}")
            ).addCheckerStatement(
                contextPickCases,
                allContexts
            ).addRGeneratedAnnotation(
                "check-ids"
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Router.KEEP_ANNOTATION_CLASS_NAME))
                    .build()
            ).addKdoc(
                Constants.KDoc.init()
            ).build()
        )
    }

    private fun FunSpec.Builder.addCheckerStatement(
        contextPickCases: List<ContextPickCase>,
        allContexts: Map<String, Context>
    ) = apply {
        beginControlFlow("return ${Constants.IdChecker.ID_CHECKER_SIMPLE_CLASS_NAME}().apply")
        contextPickCases.forEach { value ->
            val context = value.context
            val register = value.pickCase.register
            val componentIdClassName =
                Constants.IdChecker.makeComponentIdClassName(
                    context.uri,
                    context.publishArtifactName
                )
            val aggregateIdClassName =
                if (context.enableProvideRoute && register != RouterRegister.NONE)
                    Constants.IdChecker.makeAggregateIdClassName(
                        context.uri,
                        context.publishArtifactName
                    )
                else "null"
            addStatement(
                ("${Constants.IdChecker.ID_CHECKER_FUNCTION_NAME_ADD_COMPONENT_ID}(\n" +
                        "\"${context.uri}\",\n" +
                        "${componentIdClassName},\n" +
                        "${aggregateIdClassName}\n" +
                        ")").noSpaces()
            )
        }
        allContexts.forEach { (_, context) ->
            if (context.enableProvideRoute) {
                val contextIdClassName =
                    Constants.IdChecker.makeContextIdClassName(
                        context.uri,
                        context.publishArtifactName
                    )
                addStatement(
                    ("${Constants.IdChecker.ID_CHECKER_FUNCTION_NAME_ADD_CONTEXT_ID}(\n" +
                            "\"${context.uri}\",\n" +
                            "${contextIdClassName}\n" +
                            ")").noSpaces()
                )
            }
        }
        endControlFlow()
    }

}


