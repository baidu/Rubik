package com.rubik.apt.utility

import BY_VERSION
import com.ktnail.x.camelToPascal
import com.ktnail.x.pathToCamel
import com.rubik.annotations.source.RGenerated
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.api.RouteCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

fun invokeElementCode(
    type: InvokeElementType,
    staticClass: Boolean,
    className: String,
    staticElement: Boolean,
    elementName: String,
    instanceCode: String?,
    queriesCode: String?
): String {
    val instance = when {
        staticClass -> className
        staticElement -> {
            if (className.endsWith("Kt"))
                className.removeSuffix("Kt").let { name ->
                    name.removeRange(name.lastIndexOf("."), name.length)
                }
            else className
        }
        else -> instanceCode ?: "$className()"
    }
    val element = when (type) {
        InvokeElementType.METHOD, InvokeElementType.HIGHER_ORDER_FUNC, InvokeElementType.CONSTRUCTOR -> {
            "$elementName(${queriesCode ?: ""})"
        }
        InvokeElementType.PROPERTY -> {
            elementName
        }
    }
    return if (instance.isEmpty()) element else "$instance.$element"
}


fun FunSpec.Builder.inControlFlowStatementIf(
    check: Boolean,
    beginControlFlow: String,
    action: () -> Unit
) {
    if (check){
        beginControlFlow(beginControlFlow)
        action()
        endControlFlow()
    } else {
        action()
    }
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

fun TypeSpec.Builder.addThisLevelSectionTypes(
    sections: SectionCodeBase,
    action: (TypeSpec.Builder, RouteCodeBase) -> Unit
): TypeSpec.Builder = apply {
    sections.items.forEach { codeBase ->
        action(this, codeBase)
    }
}

fun TypeSpec.Builder.addNextLevelSectionTypes(
    sections: SectionCodeBase,
    action: (TypeSpec.Builder, RouteCodeBase) -> Unit
): TypeSpec.Builder = apply {
    sections.nextLevel.forEach { (typeName, codeBase) ->
        addType(
            TypeSpec.objectBuilder(
                typeName.pathToCamel().camelToPascal()
            ).addSectionTypes(
                codeBase, action
            ).addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess(Constants.Contexts.KEEP_ANNOTATION_CLASS_NAME)).build()
            ).build()
        )
    }
}

fun TypeSpec.Builder.addSectionTypes(
    sections: SectionCodeBase,
    action: (TypeSpec.Builder, RouteCodeBase) -> Unit
): TypeSpec.Builder = apply {
    addThisLevelSectionTypes(sections, action)
    addNextLevelSectionTypes(sections, action)
}