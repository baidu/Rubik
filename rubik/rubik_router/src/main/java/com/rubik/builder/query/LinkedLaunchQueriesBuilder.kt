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
package com.rubik.builder.query

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.rubik.route.LaunchQueries

class LinkedLaunchQueriesBuilder :BundleQueriesBuilder(), LinkedLaunchQueriesFrameable<Unit> {
    override val queries: LaunchQueries = LaunchQueries()

    override fun launchBy(context: Context) {
        queries.context = context
    }

    override fun launchBy(activity: Activity) {
        queries.activity = activity
        queries.context = activity.applicationContext
    }

    override fun launchBy(fragment: Fragment) {
        queries.fragment = fragment
        queries.context = fragment.context
    }

    override fun launchRequestCode(requestCode: Int) {
        queries.requestCode = requestCode
    }

    override fun launchFlags(flags: Int) {
        queries.flags = flags
    }

}
