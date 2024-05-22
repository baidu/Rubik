package com.rubik.apt.utility

import BY_VERSION
import com.ktnail.x.camelToPascal
import com.ktnail.x.pathToCamel
import com.rubik.annotations.source.RGenerated
import com.rubik.annotations.source.RGeneratedRouter
import com.rubik.apt.Constants
import com.rubik.apt.codebase.AnnotationCodeBase
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.rubik.apt.files.source.mirror.value.addAnnotationMembers
import com.squareup.kotlinpoet.*


fun FunSpec.Builder.inControlFlowStatementIf(
    checkIf: Boolean,
    nameIf: String,
    action: () -> Unit
) {
    if (checkIf){
        beginControlFlow("if (null != $nameIf)".noSpaces())
        action()
        endControlFlow()
    } else {
        action()
    }
}

fun FunSpec.Builder.addWhenBlockStatements(
    whenWhat: String?,
    elseCode: String?,
    action: FunSpec.Builder.() -> Unit
) = apply {
    if (whenWhat.isNullOrBlank()) beginControlFlow("when {".noSpaces())
    else beginControlFlow("when (${whenWhat}){".noSpaces())
    action.invoke(this)
    if (elseCode.isNullOrBlank()) addStatement("else -> { }".noSpaces())
    else addStatement("else -> { $elseCode }".noSpaces())
    endControlFlow()
}

fun TypeSpec.Builder.addRGeneratedAnnotation(kind: String, version: String) = apply {
    addAnnotation(
        AnnotationSpec.builder(RGenerated::class.java)
            .addMember("kind = %S", kind)
            .addMember("by = %S", "rubik-kapt:${BY_VERSION}")
            .addMember("version = %S", version)
            .build()
    )
}

fun FunSpec.Builder.addRGeneratedRouterAnnotation(
    uri: String,
    kind: String,
    className: String,
    methodName: String
) = apply {
    addAnnotation(
        AnnotationSpec.builder(
            RGeneratedRouter::class.java
        ).addMember(
            "uri = %S", uri
        ).addMember(
            "kind = %S", kind
        ).addMember(
            "clazz = %S", className
        ).addMember(
            "method = %S",methodName
        ).build()
    )
}

private fun <T : RouteCodeBase> TypeSpec.Builder.addThisLevelSectionTypes(
    sections: SectionCodeBase<T>,
    action: (TypeSpec.Builder, T) -> Unit
): TypeSpec.Builder = apply {
    sections.items.forEach { codeBase ->
        action(this, codeBase)
    }
}

private  fun <T : RouteCodeBase> TypeSpec.Builder.addNextLevelSectionTypes(
    sections: SectionCodeBase<T>,
    static:Boolean = false,
    action: (TypeSpec.Builder, T) -> Unit
): TypeSpec.Builder = apply {
    sections.nextLevel.forEach { (typeName, codeBase) ->
        val spaceName =  typeName.pathToCamel().camelToPascal()
        if (static) {
            addType(
                TypeSpec.objectBuilder(
                    spaceName
                ).addSectionTypes(
                    codeBase, static, null, action
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                        .build()
                ).build()
            )
        } else {
            val spaceInnerClassName = "NameSpace${spaceName}"
            addType(
                TypeSpec.classBuilder(
                    spaceInnerClassName
                ).addModifiers(
                    KModifier.INNER
                ).addSectionTypes(
                    codeBase, static, null, action
                ).addAnnotation(
                    AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME))
                        .build()
                ).build()
            )
            addProperty(
                PropertySpec.builder(
                    spaceName,
                    ClassName("", spaceInnerClassName)
                ).getter(
                    FunSpec.getterBuilder().addStatement("return ${spaceInnerClassName}()").build()
                ).build())
        }
    }
}

fun <T : RouteCodeBase> TypeSpec.Builder.addSectionTypes(
    sections: SectionCodeBase<T>,
    static:Boolean = true,
    addNextLevelTo: TypeSpec.Builder? = null,
    action: (TypeSpec.Builder, T) -> Unit
): TypeSpec.Builder = apply {
    addThisLevelSectionTypes(sections, action)
    (addNextLevelTo ?: this).addNextLevelSectionTypes(sections, static, action)
}

fun TypeSpec.Builder.addAnnotations(annotations: List<AnnotationCodeBase>) = apply {
    addAnnotations(annotations.map { codebase ->
        AnnotationSpec.builder(
            Class.forName(
                codebase.className
            ).asClassName()
        ).addAnnotationMembers(codebase.members).build()
    })
}

fun TypeSpec.Builder.addInterfaces(interfaces: List<ClassName>) = apply {
    interfaces.forEach { name ->
        addSuperinterface(name)
    }
}

fun <T> Collection<T>.toArrayCode(transform: ((T) -> CharSequence)? = null) = joinToString(",\n" , transform = transform).let { code->
    if(code.isNotBlank()) "\n$code\n" else code
}

fun bestGuessNameOrNull(className: String) = try {
    ClassName.bestGuess(className)
} catch (e: Exception) {
    null
}

fun <T> List<T>.toParametersCode(
    transform: ((T) -> CharSequence)? = null
): String {
    return if (isEmpty()) ""
    else
        """
            |
            |${joinToString(separator = ",\n") { 
            """
            |  ${transform?.invoke(it) ?: it.toString() }
            """.trimMargin()
            }}
            |
        """.trimMargin()
}
