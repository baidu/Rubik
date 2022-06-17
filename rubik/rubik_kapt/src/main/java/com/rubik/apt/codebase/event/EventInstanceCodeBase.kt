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
package com.rubik.apt.codebase.event

import com.blueprint.kotlin.lang.element.KbpElement
import com.blueprint.kotlin.pool.ElementPool
import com.rubik.apt.codebase.InvokeElementCodeBase

class EventInstanceCodeBase(
    val forTag: String,
    val invoker: InvokeElementCodeBase
) {
    companion object {
        operator fun invoke(
            elementPool: ElementPool,
            element: KbpElement,
            forTag: String
        ): EventInstanceCodeBase? {
            return InvokeElementCodeBase(elementPool, element)?.let { invoker ->
                EventInstanceCodeBase(
                    forTag,
                    invoker
                )
            }
        }
    }
}



