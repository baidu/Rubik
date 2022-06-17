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
package com.rubik.apt.codebase.api

import com.blueprint.kotlin.lang.type.KbpType
import javax.lang.model.type.TypeKind

/**
 * The code structure of Router Result.
 *
 * @since 1.1
 */
class ResultCodeBase(
    val name: String,
    type: KbpType
) : TypeCodeBase(type) {
    companion object {
        operator fun invoke(
            kbpType: KbpType
        ): ResultCodeBase? =
            if (kbpType.jmType?.kind != TypeKind.VOID)
                ResultCodeBase("", kbpType)
            else
                null
    }
}
