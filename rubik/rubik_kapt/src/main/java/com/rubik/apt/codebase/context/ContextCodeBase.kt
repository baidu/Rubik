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
package com.rubik.apt.codebase.context

import com.ktnail.x.Logger
import com.ktnail.x.find
import com.ktnail.x.toPascal
import com.ktnail.x.uri.buildVersionPath
import com.rubik.apt.Constants
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.ApiInstanceCodeBase
import com.rubik.apt.codebase.event.EventCodeBase
import com.rubik.apt.codebase.event.EventInstanceCodeBase
import com.rubik.apt.codebase.value.ValueCodeBase

/**
 * The code structure of Router Context.
 *
 * @since 1.1
 */
class ContextCodeBase {
    var name: String = ""
    var dependencies: List<String> = listOf()
    val events: MutableMap<String, MutableList<EventCodeBase>> = mutableMapOf() // key : msg
    val eventAssistants: MutableMap<String, EventInstanceCodeBase> = mutableMapOf() // key : tag
    val values: MutableList<ValueCodeBase> = mutableListOf()
    val sections = SectionCodeBase()
    val apis: MutableMap<String, ApiCodeBase> = mutableMapOf() // key : versionPath
    val apiAssistants: MutableMap<String, ApiInstanceCodeBase> = mutableMapOf() // key : versionPath
    val activities: MutableMap<String, ActivityCodeBase> = mutableMapOf()
    var version: String = "undefine"

    var generatedEnable: Boolean = false

    fun getAggregateName() = Constants.Aggregate.Declare.makeAggregateClassName(name)

    fun getContextName(): String = toPascal(name, Constants.Contexts.CONTEXT_BASE_CLASS_NAME)

    fun merge(other: ContextCodeBase) {
        events.putAll(other.events)
        values.addAll(other.values)
        apis.putAll(other.apis)
        apiAssistants.putAll(other.apiAssistants)
        activities.putAll(other.activities)
    }

    fun addActivity(activity: ActivityCodeBase) {
        activities[buildVersionPath(activity.path, activity.version)] = activity
    }

    fun addApi(api: ApiCodeBase) {
        apis[buildVersionPath(api.path, api.version)] = api
    }

    fun compose() {
        composeSections()
        composeEventAssistants()
        composeApiAssistants()
    }

    private fun composeSections() {
        activities.forEach { (_, activity) ->
            if (!activity.navigationOnly) {
                sections.addItem(activity, activity.sections)
            }
        }
        apis.forEach { (_, api) ->
            if (!api.navigationOnly) {
                sections.addItem(api, api.sections)
            }
        }
    }

    private fun composeEventAssistants() {
        eventAssistants.forEach { (tag, assistant) ->
            events.forEach { (_, msgEvents) ->
                msgEvents.filter { life -> life.tag == tag }.forEach { life ->
                    life.invoker.assistant = assistant.invoker
                }
            }
        }
    }

    private fun composeApiAssistants() {
        apiAssistants.forEach { (versionPath, assistant) ->
            assistant.invoker.queries.forEach { query ->
                query.addNameAssistPrefix()
            }
            if (assistant.version.isNotBlank())
                apis[versionPath]?.let { api ->
                    composeApiAssistant(api, assistant)
                }
            else
                apis.find { api -> api.path == assistant.forPath }.forEach { (_, api) ->
                    composeApiAssistant(api, assistant)
                }
        }

    }

    private fun composeApiAssistant(api: ApiCodeBase, apiAssistant: ApiInstanceCodeBase) {
        api.invoker.assistant = apiAssistant.invoker
    }

}