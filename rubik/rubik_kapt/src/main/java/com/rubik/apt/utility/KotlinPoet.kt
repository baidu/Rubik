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
package com.rubik.apt.utility

import com.ktnail.x.Logger
import com.squareup.kotlinpoet.FileSpec

fun String.noSpaces() = this.replace(" ", "·")

fun FileSpec.Builder.process()  = apply {
    try {
        // fix kotlinpoet issue : "import xxx as xxx" auto wrapping
        this::class.java.getDeclaredField("memberImports").let { field ->
            field.isAccessible = true
            (field.get(this) as? Set<*>)?.forEach { import ->
                if (null != import) {
                    val importString = import::class.java.getDeclaredField("importString")
                    importString.isAccessible = true
                    importString.set(import, importString.get(import).toString().noSpaces())
                }
            }
        }
    } catch (e: Exception) {
        Logger.e(e.toString())
    }
}
