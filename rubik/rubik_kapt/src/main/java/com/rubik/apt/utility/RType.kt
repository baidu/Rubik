package com.rubik.apt.utility

import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.lang.utility.*
import com.rubik.annotations.route.RCallback
import com.rubik.annotations.route.RObject
import com.rubik.annotations.route.RValue
import com.rubik.apt.Constants
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import javax.lang.model.type.TypeMirror


fun TypeMirror?.isRMirror(
    vararg annotations: Class<out Annotation> = arrayOf(RValue::class.java, RObject::class.java, RCallback::class.java)
): Boolean =
    if (null != this)
        annotations.fold(false) { acc, annotation ->
            acc || asTypeElement()?.let { element ->
                null != element.getAnnotation(annotation) || element.findConstructors().fold(false) { acc, constructor -> acc || null != constructor.getAnnotation(annotation) }
            } ?: false
        }
    else false

fun KbpType.containRMirror(vararg annotations: Class<out Annotation>): Boolean = null != getRMirrorType(*annotations)

fun KbpType.toRMirrorName(
    uri: String,
    vararg annotations: Class<out Annotation> = arrayOf(RValue::class.java, RObject::class.java, RCallback::class.java)
): TypeName =
    if (containRMirror(*annotations)) {
        toTypeName {
            name.let { name ->
                if (jmType.isRMirror(*annotations) && name is ClassName)
                    ClassName(toContextPackageName(uri), name.simpleName)
                else name
            }
        }
    } else {
        toTypeName()
    }.let {
        val firstTypeArguments = typeArguments.firstOrNull()?.toTypeName()
        if (null != firstTypeArguments && isVarargs()) firstTypeArguments
        else it
    }

fun KbpType.getRMirrorType(vararg annotations: Class<out Annotation>): KbpType? {
    when {
        jmType.isRMirror(*annotations) -> return this
        typeArguments.isNotEmpty() -> typeArguments.forEach { argument ->
            if (null != argument.getRMirrorType(*annotations)) return argument
        }
        else -> return null
    }
    return null
}

fun KbpType?.isSupportMappingRMirror(
    vararg annotations: Class<out Annotation> = arrayOf(RValue::class.java, RObject::class.java, RCallback::class.java)
) = if (this?.jmType.isRMirror(*annotations)) true
    else {
        this?.jmType?.let { type ->
            type.asTypeElement()?.let {  typeElement ->
                if (null != typeElement.findSuperType("androidx.lifecycle.LiveData") ||
                    null != typeElement.findInterface("java.util.List") ||
                    null != typeElement.findInterface("java.util.Set") ||
                    null != typeElement.findSuperType("kotlin.Array") ||
                    null != typeElement.findInterface("kotlin.collections.List") ||
                    null != typeElement.findInterface("kotlin.collections.Set"))
                    type.typeArguments.firstOrNull()
                else if (null != typeElement.findInterface("java.util.Map") ||
                    null != typeElement.findInterface("kotlin.collections.Map"))
                    type.typeArguments.getOrNull(1)
                else null
            }
        }?.jmType?.isRMirror(*annotations) ?: false
}

fun KbpType.toContextPackageName(uri: String): String =
    when {
//        jmType.isRMirror(RValue::class.java) -> Constants.Contexts.Declare.makeContextPackageName(uri, "value")
        jmType.isRMirror(RObject::class.java) -> Constants.Contexts.Declare.makeContextPackageName(uri, "objekt")
        jmType.isRMirror(RCallback::class.java) -> Constants.Contexts.Declare.makeContextPackageName(uri, "callback")
        else -> Constants.Contexts.Declare.makeContextPackageName(uri)
    }
