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
package com.rubik.activity

import android.app.Activity
import android.content.Intent
import com.rubik.route.LaunchQueries
import com.rubik.route.Queries
import com.rubik.route.Query
import com.rubik.route.Results
import com.rubik.router.annotations.RInvariant

/**
 * Launcher a Android Activity.
 *
 * @since: 1.0
 */
@RInvariant
class Launcher {
    fun launch(
        clazz: Class<out Activity>,
        queries: Queries,
        pathQueries: List<Query>? = null,
        results: List<Results>? = null
    ) {
        (queries as? LaunchQueries)?.let { launchQueries ->
            launchQueries.context?.let { context ->
                Intent(context, clazz).let { intent ->
                    launchQueries.flags?.let { flag ->
                        intent.addFlags(flag)
                    }
                    intent.putQueriesExtras(launchQueries)
                    pathQueries?.let { pathQueries ->
                        intent.putPathQueriesExtras(pathQueries)
                    }
                    launchQueries.requestCode.let { requestCode ->
                        when {
                            null != launchQueries.fragment -> if (null != requestCode) {
                                launchQueries.fragment?.startActivityForResult(intent, requestCode)
                            } else {
                                launchQueries.fragment?.startActivity(intent)
                            }
                            null != launchQueries.activity -> if (null != requestCode) {
                                launchQueries.activity?.startActivityForResult(intent, requestCode)
                            } else {
                                launchQueries.activity?.startActivity(intent)
                            }
                            else -> {
                                context.startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Intent.putQueriesExtras(queries: Queries) {
    putExtras(queries.toBundle())
}

private fun Intent.putPathQueriesExtras(pathQueries: List<Query>) {
    pathQueries.forEachIndexed { _, query ->
        putExtra(query.name, query.value?.toString())
    }
}
