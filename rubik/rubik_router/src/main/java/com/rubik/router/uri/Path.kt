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
package com.rubik.router.uri

import com.rubik.route.Query

/**
 * In order build path by string parameters.
 * @since 1.1
 */
class Path(private val originPath: String) {
    private val parameterRegex = Regex("\\{([^}]*)\\}")

    fun setParameters(values: Array<out String>): String {
        return originPath.setFirstParameters(0, values)
    }

    private fun String.setFirstParameters(index: Int, values: Array<out String>): String {
        return if (contains(parameterRegex))
            replaceFirst((parameterRegex), values[index]).setFirstParameters(index + 1, values)
        else
            this
    }

    fun matching(parametersPath: String): Boolean = if (parametersPath == originPath)
        true
    else
        Regex(originPath.replace(parameterRegex, ".*")).matches(parametersPath)

    fun getParameters(parametersPath: String): List<Query> {
        return if (parametersPath != originPath && matching(parametersPath)) {
            val parameters = mutableListOf<Query>()
            var tmpNamePath = originPath.escapeNaked()
            var tmpValuePath = parametersPath.escapeNaked()
            tmpNamePath.split(parameterRegex).let { parts ->
                parts.forEachIndexed { index, part ->
                    if (index < parts.size - 1) {
                        tmpNamePath = tmpNamePath.removePrefix(part)
                        tmpValuePath = tmpValuePath.removePrefix(part)
                        val name = tmpNamePath.substring(0, tmpNamePath.indexOf(parts[index + 1]))
                        val value = tmpValuePath.substring(0, tmpValuePath.indexOf(parts[index + 1]))
                        tmpNamePath = tmpNamePath.removePrefix(name)
                        tmpValuePath = tmpValuePath.removePrefix(value)
                        parameters.add(Query(name.removeSurrounding("{", "}"), value))
                    }
                }
            }
            parameters
        } else {
            listOf()
        }
    }

    private fun String.escapeNaked(): String = "START${this}END"
}