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

import com.ktnail.x.uri.Uri
import com.ktnail.x.uri.toUri
import com.rubik.builder.query.QueriesBuildable
import com.rubik.route.ResultGroups
import com.rubik.route.Results
import com.rubik.route.exception.BadUriException
import com.rubik.router.Router

abstract class BasicRouterBuilder : RouterBuildable {
    protected abstract val queriesBuilder: QueriesBuildable
    protected abstract fun createUri(): String

    private val resultGroups: ResultGroups = ResultGroups()
    private var checkRouterVersion: Int? = null

    private fun buildUri(): Uri {
        val uri = createUri()
        return uri.toUri() ?: throw BadUriException(uri)
    }

    override fun checkRouterVersion(version: Int) = apply {
        checkRouterVersion = version
    }

    override fun receiveResults(receive: (Results) -> Unit) = apply {
        resultGroups.load(Results(receive))
    }

    override fun build() = Router(buildUri(), queriesBuilder.buildQueries(), resultGroups, checkRouterVersion)

}