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

import com.blueprint.kotlin.lang.type.KbpType
import com.rubik.apt.codebase.AnnotationCodeBase
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.invoker.TypeCodeBase

class ValueFieldCodeBase(
    val name: String,
    originalType: KbpType,
    val annotations: List<AnnotationCodeBase>,
    val defaultValueCode: String?,
    val isConstant: Boolean
) : TypeCodeBase(originalType) {
    override val tokenList
        get() = TokenList(
            originalType,
            annotations,
            defaultValueCode,
            isConstant,
            key = "FLD",
            warp = false
        )
}
