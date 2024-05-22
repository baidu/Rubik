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
package com.rubik.picker.exception

/**
 * Thrown when Context in projectMode but no project path set.
 *
 * @since 1.8
 */
internal class RubikProjectNotFoundException(private val uri:String, private val path:String) : RuntimeException() {
    override fun toString() =
        "RubikProjectPathNotFoundException uri[${uri}] in projectMode but not found project use path[$path] ! "
}