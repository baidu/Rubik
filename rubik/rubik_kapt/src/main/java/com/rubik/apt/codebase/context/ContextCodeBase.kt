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

import com.ktnail.x.find
import com.ktnail.x.toPascal
import com.rubik.apt.Constants
import com.rubik.apt.InvokeElementType
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.RouteCodeBase
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.ApiInstanceCodeBase
import com.rubik.apt.codebase.createToken
import com.rubik.apt.codebase.event.EventCodeBase
import com.rubik.apt.codebase.event.EventInstanceCodeBase
import com.rubik.apt.codebase.callback.ObjectCallbackCodeBase
import com.rubik.apt.codebase.objekt.ObjectCodeBase
import com.rubik.apt.codebase.value.ValueCodeBase
import com.rubik.apt.utility.toArrayCode

/**
 * The code structure of Router Context.
 *
 * @since 1.1
 */
class ContextCodeBase : RToken {
    var name: String = ""
    val apis: MutableMap<String, ApiCodeBase> = mutableMapOf() // key : path
    val events: MutableMap<String, MutableList<EventCodeBase>> = mutableMapOf() // key : msg
    val values: MutableList<ValueCodeBase> = mutableListOf()
    val valuesCreateByConstructor
        get() = values.filter { value -> value.createByConstructor }
    val activities: MutableMap<String, ActivityCodeBase> = mutableMapOf()
    var version: String = "undefine"

    val sections = SectionCodeBase<RouteCodeBase>()
    private val apiInstances: MutableList<ApiInstanceCodeBase> = mutableListOf()
    private val eventInstances: MutableMap<String, EventInstanceCodeBase> = mutableMapOf() // key : tag
    val objects: MutableMap<String, ObjectCodeBase> = mutableMapOf() // key : classname
    val callbacks: MutableList<ObjectCallbackCodeBase> = mutableListOf()

    var generatedContextLibsEnable: Boolean = false
    var generatedAggregateEnable: Boolean = false

    fun merge(other: ContextCodeBase) {
        events.putAll(other.events)
        eventInstances.putAll(other.eventInstances)
        values.addAll(other.values)
        apis.putAll(other.apis)
        apiInstances.addAll(other.apiInstances)
        activities.putAll(other.activities)
        objects.putAll(other.objects)
        callbacks.addAll(other.callbacks)
    }

    fun addActivity(activity: ActivityCodeBase) {
        activities[activity.versionPath] = activity
    }

    fun addApi(api: ApiCodeBase, pathCrash: (ApiCodeBase) -> ApiCodeBase) {
        val addedApi = apis[api.versionPath]
        if (null != addedApi)
            addApi(pathCrash(addedApi), pathCrash)
        else
            apis[api.versionPath] = api
    }

    fun addApiInstance(codeBase: ApiInstanceCodeBase) {
        apiInstances.add(codeBase)
    }

    fun addEventInstance(codeBase: EventInstanceCodeBase){
        eventInstances[codeBase.forTag] = codeBase
    }

    fun addObject(objekt: ObjectCodeBase) {
        objects[objekt.qualifiedName] = objekt
    }

     fun addCallback(callBack: ObjectCallbackCodeBase){
         callbacks.add(callBack)
     }

    private fun composeSections() {
        activities.forEach { (_, activity) ->
            sections.addItem(activity, activity.sections) { route -> !route.navigationOnly }
        }
        apis.forEach { (_, api) ->
            sections.addItem(api, api.sections) { route -> !route.navigationOnly }
        }
    }

    private fun composeEventInstances() {
        eventInstances.forEach { (tag, instance) ->
            instance.invoker.queries.forEach { query ->
                query.addNameApiInstancePrefix()
            }
            events.forEach { (_, msgEvents) ->
                msgEvents.filter { life -> life.tag == tag }.forEach { life ->
                    life.invoker.instance = instance
                }
            }
        }
    }

    private fun composeApiInstances() {
        apiInstances.forEach { instance ->
            instance.invoker.queries.forEach { query ->
                query.addNameApiInstancePrefix()
            }
            apis.find { api -> instance.isSamePath(api) }.forEach { (_, api) ->
                api.invoker.instance = instance
            }
        }
    }

    private fun composeObject() {
        objects.forEach { (className, objekt) ->
            apis.forEach { (versionPath, api) ->
                if (api.invoker.clazz.name == className) {
                    if (api.invoker.type == InvokeElementType.CONSTRUCTOR) {
                        objekt.addConstructor(api)
                    } else {
                        objekt.addApi(versionPath, api)
                        api.invoker.instance = objekt
                    }
                }
            }
            objekt.composeSections()
        }
    }

    fun compose() {
        composeObject()
        composeSections()
        composeEventInstances()
        composeApiInstances()
    }

    // make code
    val aggregateName
        get() = Constants.Aggregate.Declare.makeAggregateClassName(name)
    val contextName
        get() = toPascal(name, Constants.Contexts.CONTEXT_BASE_CLASS_NAME)
    val routeActionsName
        get() = toPascal(name, Constants.RouteActions.CONTEXT_ROUTE_ACTIONS_BASE_CLASS_NAME)
    val routeContextName
        get() = toPascal(name, Constants.ContextRouters.ROUTE_CONTEXT_BASE_CLASS_NAME)
    val contextIdName
        get() = toPascal(name, Constants.Identity.Declare.CONTEXT_ID_BASE_CLASS_NAME)
    val aggregateIdName
        get() = toPascal(name, Constants.Identity.Declare.AGGREGATE_ID_BASE_CLASS_NAME)
    val eventsArray: String
        get() = events.keys.toArrayCode { event -> "\"$event\"" }

    private var _token: String? = null

    val token: String
        get() = _token ?: createToken().apply { _token = this }

    override val tokenList
        get() = TokenList(
            *apis.map { entry -> entry.value }.sortedBy { api -> api.path }.toTypedArray(),
            *activities.map { entry -> entry.value }.sortedBy { act -> act.path }.toTypedArray(),
            *events.flatMap { entry -> entry.value }.sortedBy { evt -> evt.msg }.toTypedArray(),
            *values.sortedBy { it.qualifiedName }.toTypedArray(),
            *objects.map { entry -> entry.value }.sortedBy { obj -> obj.qualifiedName }.toTypedArray(),
            *callbacks.sortedBy { it.qualifiedName }.toTypedArray(),
            key = null,
            warp = true
        )
}