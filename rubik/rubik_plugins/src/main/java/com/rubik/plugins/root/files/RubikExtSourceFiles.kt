package com.rubik.plugins.root.files

import com.ktnail.x.packageNameToFilePath
import com.rubik.plugins.basic.Constants
import com.rubik.plugins.basic.utility.addRGeneratedAnnotation
import com.rubik.plugins.root.pick.PickedContext
import com.rubik.plugins.root.router.RouterRegister
import com.squareup.kotlinpoet.*
import java.io.File

/**
 *  The super class of all gradle plugin in rubik.
 *
 *  @since 1.3
 */
class RubikExtSourceFiles(
    private val pickedContexts: MutableMap<String, PickedContext>,
    private val generatedDir: File
) {
    fun generate() {
        cleanSourceFiles()

        FileSpec.builder(
            Constants.Router.GENERATE_PACKAGE_NAME,
            Constants.Router.GENERATE_FILE_NAME
        ).generateRubikInit(
            pickedContexts
        ).build().writeTo(generatedDir)

    }

    private fun cleanSourceFiles() {
        File("${generatedDir.absolutePath}${File.separator}${Constants.Router.GENERATE_PACKAGE_NAME.packageNameToFilePath()}").deleteRecursively()
    }
}

private fun FileSpec.Builder.generateRubikInit(pickedContexts: MutableMap<String, PickedContext>) = apply {
    val consumedName = mutableMapOf<String, Int>()
    val aggregateNormal = mutableListOf<String>()
    val aggregateReflect = mutableListOf<String>()
    pickedContexts.forEach { (_, context) ->
        if (context.extension.enableProvideRoute){
            when(context.picked.mode.register){
                RouterRegister.NEW_INSTANCE->{
                    val className = Constants.Aggregate.Declare.makeAggregateClassName(context.extension.publishArtifactName)
                    val memberName = MemberName(
                        Constants.Aggregate.Declare.makeAggregatePackageName(context.extension.uri),
                        className
                    )
                    var useName = className
                    if (consumedName.containsKey(className)) {
                        useName = "$className${consumedName[className]}"
                        consumedName[className] = consumedName.getOrDefault(className, 0) + 1
                        addAliasedImport(memberName, useName)
                    }
                    addImport(memberName.packageName, memberName.simpleName)
                    aggregateNormal.add(useName)
                }
                RouterRegister.REFLECT_INSTANCE->{
                    val className = Constants.Aggregate.Declare.makeAggregateClassName(context.extension.publishArtifactName)
                    val memberName =   MemberName(
                        Constants.Aggregate.Declare.makeAggregatePackageName(context.extension.uri),
                        className
                    )
                    aggregateReflect.add(memberName.canonicalName)
                }
                RouterRegister.NONE->{ }
            }
        }
    }

    addFunction(
        FunSpec.builder(
            Constants.Router.INIT_FUNCTION_NAME).receiver(ClassName.bestGuess(Constants.Router.RUBIK_CLASS_NAME)
        ).addNormalRegisterStatement(
            aggregateNormal
        ).addReflectRegisterStatement(
            aggregateReflect
        ).addRGeneratedAnnotation(
            "init"
        ).addAnnotation(
            AnnotationSpec.builder(ClassName.bestGuess(Constants.Router.KEEP_ANNOTATION_CLASS_NAME)).build()
        ).addKdoc(
            Constants.KDoc.init()
        ).build()
    )
}

private fun FunSpec.Builder.addNormalRegisterStatement(names: List<String>) = apply {
    names.forEach { className ->
        addStatement("$className.${Constants.Aggregate.Declare.REGISTER_FUNCTION_NAME}()")
    }
}

private fun FunSpec.Builder.addReflectRegisterStatement(names: List<String>) = apply {
    names.forEach { className ->
        addStatement("${Constants.Router.METHOD_REGISTER_NAME}(\"$className\")")
    }
}
