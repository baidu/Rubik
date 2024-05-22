package com.rubik.apt.files.source.aggregate

import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase
import com.rubik.apt.codebase.invoker.TypeCodeBase
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.toParametersCode

fun invokeOriginalCode(
    invoker: InvokeOriginalCodeBase,
    nameBox: FileNameBox,
    castAllQuery: Boolean,
    processExt: Boolean
): String = invoker.invokeCode(
    nameBox,
    invoker.instanceInvoker?.let { instanceInvoker ->
        invokeOriginalCode(instanceInvoker, nameBox, castAllQuery, processExt)
    }?:invoker.objectParameter?.let { query->
        query.toOriginalCode(Constants.Object.OBJECT_INSTANCE_PARAMETER_NAME, nameBox)
    },
    parametersCode(invoker, nameBox, castAllQuery, processExt)
).let { code ->
    """
        |${
        if (null != invoker.result) {
            invoker.result.toContextCode(code, nameBox)
        } else {
            code
        }
    }
    """.trimMargin().noSpaces()
}

private fun InvokeOriginalCodeBase.invokeCode(
    nameBox: FileNameBox,
    instanceCode: String?,
    parametersCode: String?
): String {
    val instance = when {
        null != instanceCode -> instanceCode
        clazz.static -> nameBox.closeSimpleName(clazz.name)
        static -> ""
        else -> "${nameBox.closeSimpleName(clazz.name)}()"
    }
    val method = when {
        static -> "${if (clazz.name.endsWith("Kt")) clazz.name.substringBeforeLast(".") else clazz.name}.$name"
        type == InvokeElementType.CONSTRUCTOR -> nameBox.closeSimpleName(clazz.name)
        else -> ".$name"
    }
    val parameters = if (parametersCode.isNullOrBlank()) "" else "$parametersCode"
    return when (type) {
        InvokeElementType.CONSTRUCTOR -> {
            "$method(${parameters})"
        }
        InvokeElementType.METHOD, InvokeElementType.HIGHER_ORDER_FUNC -> {
            "$instance$method(${parameters})"
        }
        InvokeElementType.PROPERTY -> {
            "$instance$method"
        }
    }
}

private fun parametersCode(
    invoker: InvokeOriginalCodeBase,
    nameBox: FileNameBox,
    castAllQuery: Boolean,
    processExt: Boolean
) = invoker.queries.toParametersCode { query ->
        val needTransform = null != query.callback && query.callback.transformRMirror
        var code = when {
            query.isExtendThis && processExt -> "this"
            needTransform -> Constants.Apis.toCallbackTransformerName(query.legalName)
            else -> query.callName
        }
        if (!needTransform) {
            code = query.toOriginalCode(code, nameBox, castIfNot = castAllQuery)
        } else {
            if (castAllQuery) code = TypeCodeBase.castToTypeCode(code)
        }
        code
    }