package com.rubik.apt.files.source.context

import com.ktnail.x.uri.buildVersionUri
import com.rubik.apt.Constants
import com.rubik.apt.codebase.api.RouteCodeBase
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

fun TypeSpec.Builder.addPathProperties(
    uri: String,
    route: RouteCodeBase
) = apply {
    val name = route.contextPropertyName
    val versionUri = buildVersionUri(uri, route.path, route.version)
    if (Constants.Aggregate.isParameterPath(route.path)) {
        addFunction(
            FunSpec.builder(
                name
            ).addParameter(
                Constants.Aggregate.PARAMETER_VALUES_NAME, String::class, KModifier.VARARG
            ).returns(
                String::class
            ).addStatement(
                "return ${Constants.Aggregate.PATH_CLASS_NAME_AS}(\"$versionUri\").${Constants.Aggregate.METHOD_SET_PARAMETERS_NAME}(${Constants.Aggregate.PARAMETER_VALUES_NAME})"
            ).build()
        )
    } else {
        addProperty(
            PropertySpec.builder(
                name, String::class
            ).addModifiers(
                KModifier.CONST
            ).initializer(
                "%S", versionUri
            ).build()
        )
    }

}
