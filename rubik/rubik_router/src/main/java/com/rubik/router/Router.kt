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
package com.rubik.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ktnail.x.uri.Uri
import com.rubik.Rubik
import com.rubik.builder.query.BundleQueriesBuilder
import com.rubik.builder.query.BundleQueriesFrameable
import com.rubik.builder.query.DSLLaunchQueriesBuilder
import com.rubik.builder.router.*
import com.rubik.context.Aggregatable
import com.rubik.context.doEvent
import com.rubik.logger.Logger
import com.rubik.route.Queries
import com.rubik.route.ResultGroups
import com.rubik.route.Results
import com.rubik.route.mapping.caseToTypeOfT
import com.rubik.route.mapping.query
import com.rubik.route.mapping.typeOfT
import com.rubik.route.mapping.valueProperty
import com.rubik.router.annotations.RInvariant
import com.rubik.router.exception.RubikAggregateNotFoundException
import java.lang.reflect.Type

class Router internal constructor(
    private val uri: Uri,
    private val queries: Queries,
    private val resultGroups: ResultGroups,
    private val checkRouterVersion: Int? = null
) {

    private val path
        get() = uri.versionPath

    fun route() {
        safeRoute {
            checkRouterVersion()
            findAggregate(uri.basicUri).onRoute(path, queries, resultGroups)
        }
    }

    /**
     * route and get the result.
     */
    fun routeForResult(type: Type? = null): Any? {
        return safeRouteAny { routeSync().value(0).caseToTypeOfT() }
    }

    @RInvariant
    fun routeSync(): Results {
        val results = Results(null)
        safeRoute {
            checkRouterVersion()
            resultGroups.load(results)
            findAggregate(uri.basicUri).onRoute(path, queries, resultGroups)
        }
        return results
    }

    private fun findAggregate(uri: String): Aggregatable {
        return Rubik.createAggregate(uri) ?: throw RubikAggregateNotFoundException(uri)
    }

    private fun checkRouterVersion() {
        if (null != checkRouterVersion) Rubik.checkRouterVersionLogic(checkRouterVersion)
    }

    private fun safeRoute(body: () -> Unit) {
        return try {
            body()
        } catch (e: Exception) {
            Logger.e(" RUBIK navigation on uri: $uri with exception : $e", e)
        }
    }

    companion object {
        // event
        /**
         * start to sending a event message.
         */
        @JvmStatic
        fun doEvent(msg: String, vararg args: Any) {
            Rubik.createAggregatesByMsg(msg).forEachIndexed { _, aggregate -> aggregate.doEvent(msg, *args) }
        }

        /**
         * start to sending a event message.
         */
        fun doEvent(msg: String, context: Context, vararg arg: Any) {
            doEvent(msg, *(listOf<Any>(context) + arg.toList()).toTypedArray())
        }

        // touch
        /**
         * touch other context by uri.
         *
         * @param  action invoke if can touch the context.
         */
        @JvmStatic
        fun touch(uri: String, action: () -> Unit) = TouchHolder(uri).touch(action)

        // builder
        /**
         * get a builder to build a route.
         */
        @JvmStatic
        fun builder(): LinkedApiRouterBuildable = LinkedApiRouterBuilder()

        /**
         * get a builder to build a route for starting a Activity.
         */
        @JvmStatic
        fun builder(context: Context): LinkedLaunchRouterBuilder =
            LinkedLaunchRouterBuilder().launchBy(context)

        /**
         * get a builder to build a route for starting a Activity by given Activity.
         */
        @JvmStatic
        fun builder(activity: Activity): LinkedLaunchRouterBuilder =
            LinkedLaunchRouterBuilder().launchBy(activity)

        /**
         * get a builder to build a route for starting a Activity by given Fragment.
         */
        @JvmStatic
        fun builder(fragment: Fragment): LinkedLaunchRouterBuilder =
            LinkedLaunchRouterBuilder().launchBy(fragment)

        // property
        /**
         * get a property value in Intent by name.
         */
        @JvmStatic
        fun <T> valueProperty(intent: Intent, name: String, type: Type): T? =
            intent.valueProperty(name, type)

        /**
         * get a property value in Bundle by name.
         */
        @JvmStatic
        fun <T> valueProperty(bundle: Bundle, name: String, type: Type): T? =
            bundle.valueProperty(name, type)
    }
}

// event
/**
 * start to sending a event message.
 */
fun Context.doEventWithContext(msg: String, vararg args: Any) = Router.doEvent(msg, this, *args)

/**
 * start to sending a event message.
 */
fun doEvent(msg: String, vararg args: Any) = Router.doEvent(msg, *args)

// touch
/**
 * touch other context by uri.
 *
 * @param  action invoke if can touch the context.
 */
fun touch(uri: String, action: () -> Unit) = Router.touch(uri, action)

// navigate
/**
 * navigate to a router path , using DSL code style.
 */
fun navigate(body: DSLRouterBuildable.() -> Unit): Unit =
    DSLApiRouterBuilder().apply(body).build().route()

/**
 * navigate to a router path , using DSL code style.
 */
fun Context.navigate(body: DSLLaunchRouterBuildable.() -> Unit): Unit =
    DSLLaunchRouterBuilder(DSLLaunchQueriesBuilder(this)).apply(body).build().route()

/**
 * navigate to a router path , using DSL code style , by given Activity.
 */
fun Activity.navigate(body: DSLLaunchRouterBuildable.() -> Unit): Unit =
    DSLLaunchRouterBuilder(DSLLaunchQueriesBuilder(this)).apply(body).build().route()

/**
 * navigate to a router path , using DSL code style , by given Fragment.
 */
fun Fragment.navigate(body: DSLLaunchRouterBuildable.() -> Unit): Unit =
    DSLLaunchRouterBuilder(DSLLaunchQueriesBuilder(this)).apply(body).build().route()

/**
 * navigate to a router path , using DSL code style , And receive results synchronously.
 */
@RInvariant
inline fun <reified T> navigateForResult(noinline body: DSLRouterBuildable.() -> Unit): T? {
    return safeRoute<T> {
        DSLApiRouterBuilder().apply(body).build().routeSync().value(0).caseToTypeOfT()
    }
}

// bundle/property
/**
 * get a property value in Intent by name.
 */
@RInvariant
inline fun <reified T> Bundle.property(name: String): T? = query(name, typeOfT<T>()).caseToTypeOfT()

@RInvariant
inline fun <reified T> Activity.property(name: String): T? =
    intent?.query(name, typeOfT<T>()).caseToTypeOfT()

@RInvariant
inline fun <reified T> Fragment.property(name: String): T? = activity?.property(name)

/**
 * add queries to bundle.
 */
fun bundleQueries(block: BundleQueriesFrameable<Unit>.() -> Unit) =
    BundleQueriesBuilder().apply(block).queries.toBundle()

@RInvariant
fun <T> safeRoute(body: () -> T): T? {
    return try {
        return body()
    } catch (e: Exception) {
        Logger.e(" RUBIK navigation with exception : $e", e)
        null
    }
}

fun safeRouteAny(body: () -> Any): Any? {
    return try {
        return body()
    } catch (e: Exception) {
        Logger.e(" RUBIK navigation with exception : $e", e)
        null
    }
}