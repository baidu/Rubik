package com.rubik.apt.utility

import com.blueprint.kotlin.lang.element.*
import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.lang.utility.findConstructors
import com.ktnail.x.camelToPascal
import com.ktnail.x.pascalToSnake
import com.ktnail.x.toPascal
import com.rubik.annotations.route.RCallback
import com.rubik.annotations.route.RResult
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException


fun KbpType.isVarargs() = (null != typeArguments.firstOrNull()?.toTypeName()) && isVarargs

fun KbpAbsElement.isResultCallback() = jmElement?.isResultCallback() == true

fun Element?.isResultCallback() =
    null != this?.getAnnotation(RCallback::class.java) || null != this?.getAnnotation(RResult::class.java)

fun KbpElement.defaultPath() = when (this) {
    is KbpRooterElement -> toPascal("create", simpleNames, "Instance")
    is KbpConstructorElement -> toPascal("create",  (jmElement?.enclosingElement as? TypeElement)?.simpleName.toString(), "Instance")
    is KbpFunctionElement -> name
    is KbpHighOrderFunctionElement -> name
    is KbpVariableElement -> name
    else -> null
}?.pascalToSnake(false, "-")

fun typeToStringInAnnotations(action: () -> String?) = try {
    action().toString()
} catch (e: MirroredTypeException) {
    e.typeMirror.toString()
}





