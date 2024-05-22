/**
 * Copyright (C) Baidu Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rubik.apt.codebase.value

import com.blueprint.kotlin.lang.element.KbpAnnotationElement
import com.blueprint.kotlin.lang.element.KbpConstructorElement
import com.blueprint.kotlin.lang.element.KbpRooterElement
import com.blueprint.kotlin.lang.element.KbpVariableElement
import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.lang.utility.findInterface
import com.blueprint.kotlin.lang.utility.toType
import com.rubik.apt.Constants
import com.rubik.apt.codebase.AnnotationCodeBase
import com.rubik.apt.codebase.ClassMirrorable
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * The code structure of Router Value.
 *
 * @since 1.1
 */
class ValueCodeBase(
    override val qualifiedName: String,
    override val simpleName: String,
    override val originalType: KbpType,
    val interfaces: List<ClassName>,
    val annotations: List<AnnotationCodeBase>,
    val fields: List<ValueFieldCodeBase>,
    val createByConstructor: Boolean
) : ClassMirrorable, RToken {
    companion object {
        operator fun invoke(
            classElement: KbpRooterElement,
            constructorElement: KbpConstructorElement? = null,
            ignoreAnnotations: List<String>
        ): ValueCodeBase? = classElement.toType()?.let { type ->
            ValueCodeBase(
                classElement.qualifiedName,
                classElement.simpleNames,
                type,
                classElement.toInterfaceNames(),
                classElement.annotations.toAnnotationCodeBase(),
                constructorElement?.toFields(ignoreAnnotations) ?: classElement.toFields(ignoreAnnotations),
                createByConstructor = null != constructorElement
            )
        }

        private fun KbpConstructorElement.toFields(ignoreAnnotations: List<String>) = parameters.map { parameter ->
            parameter.toValueFieldCodeBase(ignoreAnnotations)
        }

        private fun KbpRooterElement.toFields(ignoreAnnotations: List<String>) = properties.filter { (_, parameter) ->
            parameter.type.name.toString() != Constants.Contexts.PARCELABLE_CREATOR_CLASS_NAME && parameter.type.name.toString() != qualifiedName + Constants.Contexts.COMPANION_CLASS_NAME
        }.map { (_, parameter) ->
            parameter.toValueFieldCodeBase(ignoreAnnotations)
        }

        private fun KbpVariableElement.toValueFieldCodeBase(ignoreAnnotations: List<String>) =
            ValueFieldCodeBase(
            name,
            type,
                createFiledAnnotationCodeBase(annotations, ignoreAnnotations),
            defaultValueWithSuffix,
            isConstant
        )

        private fun KbpRooterElement.toInterfaceNames(): List<ClassName> {
            return mutableListOf<ClassName>().apply {
                jmElement?.findInterface("android.os.Parcelable")?.let { element ->
                    add(element.asClassName())
                }
            }
        }

        private fun List<KbpAnnotationElement>.toAnnotationCodeBase(): List<AnnotationCodeBase> {
            return filter { element ->
                element.className == "kotlinx.android.parcel.Parcelize"
            }.map { element ->
                AnnotationCodeBase(
                    element.className,
                    element.members.toList().map { pair ->
                        "${pair.first} = ${pair.second}"
                    }
                )
            }
        }

        private fun createFiledAnnotationCodeBase(
            elements: List<KbpAnnotationElement>,
            ignoreAnnotations: List<String>
        ): List<AnnotationCodeBase> {
            return elements.filter { element ->
                element.className != Nullable::class.qualifiedName.toString()
                        && element.className != NotNull::class.qualifiedName.toString()
                        && !ignoreAnnotations.contains(element.className)
            }.map { element ->
                AnnotationCodeBase(
                    element.className,
                    element.members.toList().map { pair ->
                        "${pair.first} = ${pair.second}"
                    }
                )
            }
        }
    }

    override val tokenList
        get() = TokenList(
            originalType,
            interfaces,
            annotations,
            fields,
            key = "VLE",
            warp = false
        )
}
