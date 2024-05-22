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
package com.rubik.publish.exception

import java.lang.RuntimeException

/**
 * Thrown when turn task state error.
 *
 * @since 1.9
 */
internal class RubikTaskStateError(
    private val from: String,
    private val to: String
) : RuntimeException() {
    override fun toString() =
        "RubikTaskStateError Cannot switch from state:${from} to state:${to}! "
}