package com.rubik.apt.utility

import com.blueprint.kotlin.lang.element.KbpVariableElement
import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.lang.utility.asTypeElement
import com.blueprint.kotlin.lang.utility.hasSuperTypeOrInterface
import com.blueprint.kotlin.lang.utility.isCharSequence
import com.blueprint.kotlin.lang.utility.isPrimitive
import com.rubik.annotations.route.RResult
import com.rubik.annotations.route.RValue
import com.rubik.apt.Constants
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import java.io.Serializable
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror


fun KbpType.containRValue(): Boolean =
    this.jmType?.isRValue() == true || null != typeArguments.find { child -> child.containRValue() }

fun KbpType.isVarargs() = (null != typeArguments.firstOrNull()?.toTypeName()) && isVarargs

fun KbpType.toRValueTypeName(uri: String? = null): TypeName =
    if (containRValue() && null != uri) {
        toTypeName {
            if (jmType?.isRValue() == true) {
                (name as? ClassName)?.let { className ->
                    ClassName(Constants.Contexts.makeContextPackageName(uri), className.simpleName)
                } ?: name
            } else {
                name
            }
        }
    } else {
        toTypeName()
    }.let {
        val firstTypeArguments = typeArguments.firstOrNull()?.toTypeName()
        if (null != firstTypeArguments && isVarargs()) {
            firstTypeArguments
        } else {
            it
        }
    }

fun TypeMirror.isRValue(): Boolean =
    null != asTypeElement()?.getAnnotation(RValue::class.java)

fun KbpVariableElement.isResultInvoker() = null != jmElement?.getAnnotation(RResult::class.java)

fun KbpType.isSerializable(): Boolean =
    !isPrimitive() && !isCharSequence() && hasSuperTypeOrInterface(Serializable::class.java.name)

fun KbpType.isSerializableArrayOrCollection(): Boolean =
    (jmType?.kind == TypeKind.ARRAY || hasSuperTypeOrInterface("java.util.Collection") ) && null != typeArguments.find { type -> type.hasSuperTypeOrInterface(Serializable::class.java.name) }

fun KbpType.isParcelable(): Boolean =
    name.toString() != "android.os.Bundle" && hasSuperTypeOrInterface("android.os.Parcelable")

fun KbpType.isParcelableArrayOrCollection(): Boolean =
    (jmType?.kind == TypeKind.ARRAY || hasSuperTypeOrInterface("java.util.Collection")) && null != typeArguments.find { type -> type. hasSuperTypeOrInterface("android.os.Parcelable") }

fun typeToStringInAnnotations(action: () -> String?) = try {
    action().toString()
} catch (e: MirroredTypeException) {
    e.typeMirror.toString()
}





