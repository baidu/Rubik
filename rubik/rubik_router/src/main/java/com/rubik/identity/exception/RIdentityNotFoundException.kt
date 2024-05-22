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
package com.rubik.identity.exception

/**
 * Thrown when RIdentity was not found .
 *
 * @since 1.9
 */
internal class RIdentityNotFoundException(
    private val uri: String,
    private val type: String
) : Exception() {
    override fun toString() = "RIdentityNotFoundException $type not found with uri:${uri}"
}