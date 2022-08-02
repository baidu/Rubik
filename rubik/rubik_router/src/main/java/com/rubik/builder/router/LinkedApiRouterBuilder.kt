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
import com.rubik.route.mapping.caseToTypeOfT
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

    override fun result(onReceive: (Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0)
            )
        }
    }

    override fun result(onReceive: (Any?, Any?) -> Unit) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0),
                results.value(1)
            )
        }
    }

    override fun result(
        onReceive: (Any?, Any?, Any?) -> Unit
    ) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0),
                results.value(1),
                results.value(2)
            )
        }
    }

    override fun result(
        onReceive: (Any?, Any?, Any?, Any?) -> Unit
    ) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0),
                results.value(1),
                results.value(2),
                results.value(3)

            )
        }
    }

    override fun result(
        onReceive: (Any?, Any?, Any?, Any?, Any?) -> Unit
    ) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0),
                results.value(1),
                results.value(2),
                results.value(3),
                results.value(4)
            )
        }
    }

    override fun result(
        onReceive: (Any?, Any?, Any?, Any?, Any?, Any?) -> Unit
    ) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0),
                results.value(1),
                results.value(2),
                results.value(3),
                results.value(4),
                results.value(5)
            )
        }
    }

    override fun result(
        onReceive: (Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit
    ) = apply {
        receiveResults { results ->
            onReceive(
                results.value(0),
                results.value(1),
                results.value(2),
                results.value(3),
                results.value(4),
                results.value(5),
                results.value(6)
            )
        }
    }
}