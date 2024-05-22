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

import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.invoker.OriginalInvokable
import com.rubik.apt.codebase.invoker.InvokeOriginalCodeBase

class EventInstanceCodeBase(
    override val invoker: InvokeOriginalCodeBase,
    val forTag: String
) : OriginalInvokable, RToken {
    override val tokenList: TokenList
        get() = TokenList(forTag, invoker, key = "EIN", warp = false)
}



