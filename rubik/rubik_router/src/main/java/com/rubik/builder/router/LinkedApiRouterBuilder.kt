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
package com.rubik.builder.router

import com.rubik.builder.query.QueriesBuilder
import java.lang.reflect.Type

class LinkedApiRouterBuilder : BasicRouterBuilder(), LinkedApiRouterBuildable {
    override val queriesBuilder = QueriesBuilder()
    override fun createUri(): String = uri
    private var uri: String = ""

    // linked
    override fun uri(uri: String) = apply {
        this.uri = uri
    }

    override fun String.with(value: Any?) = this@LinkedApiRouterBuilder.apply {
        queriesBuilder.apply {
            this@with with value
        }
    }

    override fun result(typeOfR1: Type?, onReceive: (Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.toType(0, typeOfR1)
            )
        }
    }

    override fun result(typeOfR1: Type?, typeOfR2: Type?, onReceive: (Any?, Any?) -> Unit)= apply {
        receiveResults { results ->
            onReceive(
                results.toType(0, typeOfR1),
                results.toType(1, typeOfR2)
            )
        }
    }

    override fun result(typeOfR1: Type?, typeOfR2: Type?, typeOfR3: Type?, onReceive: (Any?, Any?, Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.toType(0, typeOfR1),
                results.toType(1, typeOfR2),
                results.toType(2, typeOfR3)
            )
        }
    }

    override fun result(typeOfR1: Type?, typeOfR2: Type?, typeOfR3: Type?, typeOfR4: Type?, onReceive: (Any?, Any?, Any?, Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.toType(0, typeOfR1),
                results.toType(1, typeOfR2),
                results.toType(2, typeOfR3),
                results.toType(3, typeOfR4)
            )
        }
    }

    override fun result(typeOfR1: Type?, typeOfR2: Type?, typeOfR3: Type?, typeOfR4: Type?, typeOfR5: Type?, onReceive: (Any?, Any?, Any?, Any?, Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.toType(0, typeOfR1),
                results.toType(1, typeOfR2),
                results.toType(2, typeOfR3),
                results.toType(3, typeOfR4),
                results.toType(4, typeOfR5)
            )
        }
    }

    override fun result(typeOfR1: Type?, typeOfR2: Type?, typeOfR3: Type?, typeOfR4: Type?, typeOfR5: Type?, typeOfR6: Type?, onReceive: (Any?, Any?, Any?, Any?, Any?, Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.toType(0,typeOfR1),
                results.toType(1,typeOfR2),
                results.toType(2,typeOfR3),
                results.toType(3,typeOfR4),
                results.toType(4,typeOfR5),
                results.toType(5,typeOfR6)
            )
        }
    }
}