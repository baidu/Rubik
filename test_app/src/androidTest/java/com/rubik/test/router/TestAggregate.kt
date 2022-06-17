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
package com.rubik.test.router

import androidx.annotation.Keep
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateCompanion
import com.rubik.context.LifeCycleEvent
import com.rubik.route.LaunchQueries
import com.rubik.route.Queries
import com.rubik.route.Result
import com.rubik.route.Results
import com.rubik.router.uri.Path
import com.rubik.test.log


@Keep
class TestAggregate : Aggregatable {
    override fun onEvent(msg: String, queries: Queries) {
        when(msg){
            LifeCycleEvent.INIT ->  {
                log(" EVENT LifeCycleEvent_INIT ")
            }
            LifeCycleEvent.DESTROY ->  {
                log(" EVENT LifeCycleEvent_DESTROY ")
            }
            "context_init" ->  {
                log(" EVENT context_init : ${queries[0].value}")
            }
            "context_destroy"->  {
                log(" EVENT context_destroy : ${queries[0].value}")
            }
            else -> {
                log(" EVENT OTHER")
            }
        }
    }

    override fun onRoute(
        path: String,
        queries: Queries,
        results: List<Results>
    ) {
        when {
            "int/add1" == path -> {
                val p = queries.toType<Int>(0, null)
                results.getOrNull(0)?.setOne(Result(p + 1))
            }
            "int/add2" == path ->  {
                val p = queries.toType<Int>(0, null)
                results.getOrNull(0)?.setOne(Result(p + 2))
            }
            "activity/launch" == path ->  {
                log(" launch context ${ (queries as? LaunchQueries)?.context}")
            }
            "result/get3" == path ->  {
                results.getOrNull(0)?.setAll(Result("r1"), Result("r2"), Result("r3"))
            }
            "result/get4" == path ->  {
                results.getOrNull(0)?.setAll(Result("r1"), Result("r2"), Result("r3"), Result("r4"))
            }
            "result/get5" == path ->  {
                results.getOrNull(0)?.setAll(Result("r1"), Result("r2"), Result("r3"), Result("r4"), Result("r5"))
            }
            Path("path-queries/{0}/{1}/{2}").matching(path) -> {
                Path("path-queries/{0}/{1}/{2}").getParameters(path).let {queries->
                    results.getOrNull(0)?.setAll(
                        Result(queries[0].value.toString()),
                        Result(queries[1].value.toString()),
                        Result(queries[2].value.toString())
                    )
                }
            }
            else -> { throw com.rubik.route.exception.BadPathOrVersionException(path)}
        }
    }

    @Keep
    companion object : AggregateCompanion() {
        override val URI: String = "test://agg.test"

        override val DEPENDENCIES: List<String> = listOf()

        override val EVENT_MSGS: List<String> = listOf("LifeCycleEvent_Init","LifeCycleEvent_Destroy")

        override val CREATOR: Function0<Aggregatable> = { TestAggregate() }
    }
}
