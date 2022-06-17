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
package com.rubik.builder.result

import java.lang.reflect.Type

interface ResultsBuildable<T> {
    fun result(
        typeOfR1: Type? = null,
        onReceive: (Any?) -> Unit
    ): T

    fun result(
        typeOfR1: Type? = null,
        typeOfR2: Type? = null,
        onReceive: (Any?, Any?) -> Unit
    ): T

    fun result(
        typeOfR1: Type? = null,
        typeOfR2: Type? = null,
        typeOfR3: Type? = null,
        onReceive: (Any?, Any?, Any?) -> Unit
    ): T

    fun result(
        typeOfR1: Type? = null,
        typeOfR2: Type? = null,
        typeOfR3: Type? = null,
        typeOfR4: Type? = null,
        onReceive: (Any?, Any?, Any?, Any?) -> Unit
    ): T

    fun result(
        typeOfR1: Type? = null,
        typeOfR2: Type? = null,
        typeOfR3: Type? = null,
        typeOfR4: Type? = null,
        typeOfR5: Type? = null,
        onReceive: (Any?, Any?, Any?, Any?, Any?) -> Unit
    ): T

    fun result(
        typeOfR1: Type? = null,
        typeOfR2: Type? = null,
        typeOfR3: Type? = null,
        typeOfR4: Type? = null,
        typeOfR5: Type? = null,
        typeOfR6: Type? = null,
        onReceive: (Any?, Any?, Any?, Any?, Any?, Any?) -> Unit
    ): T
}