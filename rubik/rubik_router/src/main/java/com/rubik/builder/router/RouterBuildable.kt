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

import com.rubik.builder.query.DSLLaunchQueriesFrameable
import com.rubik.builder.query.LinkedLaunchQueriesFrameable
import com.rubik.builder.query.QueriesFrameable
import com.rubik.builder.result.ResultsBuildable
import com.rubik.route.Results
import com.rubik.router.Router
import com.rubik.router.annotations.RInvariant

interface RouterBuildable {
    fun checkRouterVersion(version: Int): RouterBuildable
    @RInvariant
    fun receiveResults(receive: (Results) -> Unit): RouterBuildable
    fun build(): Router
}

interface DSLRouterBuildable : RouterBuildable {
    var uri: String
    fun query(block: QueriesFrameable<Unit>.() -> Unit)
}

interface DSLLaunchRouterBuildable : RouterBuildable {
    var uri: String
    fun query(block: DSLLaunchQueriesFrameable.() -> Unit)
}

interface LinkedApiRouterBuildable : RouterBuildable, QueriesFrameable<LinkedApiRouterBuildable>, ResultsBuildable<LinkedApiRouterBuildable> {
    fun uri(uri: String): LinkedApiRouterBuildable
}

interface LinkedLaunchRouterBuildable : LinkedLaunchQueriesFrameable<LinkedLaunchRouterBuildable> {
    fun uri(uri: String): LinkedLaunchRouterBuildable
}
