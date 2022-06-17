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

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.rubik.builder.query.LinkedLaunchQueriesBuilder

class LinkedLaunchRouterBuilder : BasicRouterBuilder(), LinkedLaunchRouterBuildable {
    override val queriesBuilder = LinkedLaunchQueriesBuilder()
    override fun createUri(): String = uri
    private var uri: String = ""

    // linked
    override fun uri(uri: String) = apply {
        this.uri = uri
    }

    override fun launchBy(context: Context) = apply {
        queriesBuilder.queries.context = context
    }

    override fun launchBy(activity: Activity) = apply {
        queriesBuilder.queries.activity = activity
        queriesBuilder.queries.context = activity.applicationContext
    }

    override fun launchBy(fragment: Fragment) = apply {
        queriesBuilder.queries.fragment = fragment
        queriesBuilder.queries.context = fragment.context
    }

    override fun launchRequestCode(requestCode: Int) = apply {
        queriesBuilder.queries.requestCode = requestCode
    }

    override fun launchFlags(flags: Int) = apply {
        queriesBuilder.queries.flags = flags
    }


    override fun String.withSerializable(value: Any?)= this@LinkedLaunchRouterBuilder.apply {
        queriesBuilder.apply {
            this@withSerializable withSerializable value
        }
    }
    override fun String.withParcelable(value: Parcelable?)= this@LinkedLaunchRouterBuilder.apply {
        queriesBuilder.apply {
            this@withParcelable withParcelable value
        }
    }

    override fun String.withParcelable(value: Array<out Parcelable>?)= this@LinkedLaunchRouterBuilder.apply {
        queriesBuilder.apply {
            this@withParcelable withParcelable value
        }
    }

    override fun String.withParcelable(value: ArrayList<out Parcelable>?)= this@LinkedLaunchRouterBuilder.apply {
        queriesBuilder.apply {
            this@withParcelable withParcelable value
        }
    }

    override fun String.with(value: Any?) = this@LinkedLaunchRouterBuilder.apply {
        queriesBuilder.apply {
            this@with with value
        }
    }

    override fun String.withValue(value: Any?) = this@LinkedLaunchRouterBuilder.apply {
        queriesBuilder.apply {
            this@withValue withValue value
        }
    }

}