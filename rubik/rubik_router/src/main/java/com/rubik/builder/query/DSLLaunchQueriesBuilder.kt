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

class DSLLaunchQueriesBuilder(context: Context?) :BundleQueriesBuilder(), DSLLaunchQueriesFrameable {
    override val queries: LaunchQueries = LaunchQueries()

    init {
        queries.context = context
    }

    constructor(activity: Activity) : this(activity.applicationContext) {
        queries.activity = activity
    }

    constructor(fragment: Fragment) : this(fragment.context) {
        queries.fragment = fragment
    }

    override var flags: Int? = null
        set(value) {
            queries.flags = value
            field = value
        }

    override var requestCode: Int? = null
        set(value) {
            queries.requestCode = value
            field = value
        }


}
