package com.rubik.apt.files.source.value

import com.rubik.apt.codebase.value.ValueFieldCodeBase
import com.squareup.kotlinpoet.*


fun FunSpec.Builder.addConstructorParameter(
    fields: List<ValueFieldCodeBase>,
    contextUri: String
): FunSpec.Builder {
    fields.filter { codebase -> !codebase.isConstant }.forEach { field ->
        addParameter(field.name, field.toTypeName(contextUri))
    }
    return this
}

fun TypeSpec.Builder.addFieldsProperty(
    fields: List<ValueFieldCodeBase>,
    contextUri: String
): TypeSpec.Builder {
    fields.filter { codebase -> !codebase.isConstant }.forEach { field ->
        addProperty(
            PropertySpec.builder(
                field.name, field.toTypeName(contextUri)
            ).addAnnotations(field.annotations.map { codebase ->
                AnnotationSpec.builder(
                    Class.forName(
                        codebase.className
                    ).asClassName()
                ).addAnnotationMembers(codebase.members).build()
            }).initializer(field.name).build()
        )
    }
    return this
}
fun TypeSpec.Builder.addConstantsProperty(
    fields: List<ValueFieldCodeBase>,
    contextUri: String
): TypeSpec.Builder {
    fields.filter { codebase -> codebase.isConstant }.let { constants ->
        if (constants.isNotEmpty()) {
            addType(TypeSpec.companionObjectBuilder().apply {
                constants.forEach { constant ->
                    addProperty(
                        PropertySpec.builder(
                            constant.name, constant.toTypeName(contextUri)
                        ).addModifiers(
                            KModifier.CONST
                        ).initializer(
                            constant.defaultValueCode ?: "null"
                        ).build()
                    ).build()
                }
            }.build())
        }
    }
    return this
}


fun AnnotationSpec.Builder.addAnnotationMembers(members: List<String>): AnnotationSpec.Builder {
    members.forEach {
        addMember(it)
    }
    return this
}

