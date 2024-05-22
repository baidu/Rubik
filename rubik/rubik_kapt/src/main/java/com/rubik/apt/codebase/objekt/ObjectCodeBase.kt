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
package com.rubik.apt.codebase.objekt

import com.blueprint.kotlin.lang.element.KbpRooterElement
import com.blueprint.kotlin.lang.type.KbpType
import com.blueprint.kotlin.lang.utility.toType
import com.rubik.apt.codebase.ClassMirrorable
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.TokenName
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.context.SectionCodeBase

/**
 * The code structure of Router Object.
 *
 * @since 1.10
 */
class ObjectCodeBase(
    override val qualifiedName: String,
    override val simpleName: String,
    override val originalType: KbpType
) : ClassMirrorable, RToken {
    companion object {
        operator fun invoke(
            classElement: KbpRooterElement
        ): ObjectCodeBase? =
            classElement.toType()?.let { type ->
                ObjectCodeBase(
                    classElement.qualifiedName,
                    classElement.simpleNames,
                    type
                )
            }
    }

    val constructors: MutableList<ApiCodeBase> = mutableListOf()

    val apis: MutableMap<String, ApiCodeBase> = mutableMapOf() // key : versionPath

    val sections = SectionCodeBase<ApiCodeBase>()

    fun addConstructor(api: ApiCodeBase) {
        constructors.add(api)
    }

    fun addApi(versionPath: String, api: ApiCodeBase) {
        apis[versionPath] = api
    }

    fun composeSections() {
        apis.forEach { (_, api) ->
            sections.addItem(api, api.sections)
        }
    }

    override val tokenList
        get() = TokenList(
            originalType,
            constructors.map { api -> api.versionPath }.sortedBy { it }.map { path -> TokenName(path) },
            apis.map { (_, api) -> api.versionPath }.sortedBy { it }.map { path -> TokenName(path) },
            key = "OBJ",
            warp = false
        )

}
