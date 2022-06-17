package com.rubik.test.router

import com.rubik.context.Aggregatable
import com.rubik.context.AggregateCompanion
import com.rubik.route.Queries
import com.rubik.route.Result
import com.rubik.route.Results

class TestSimpleAggregate : Aggregatable {
    override fun onEvent(msg: String, queries: Queries) {
        print(msg + queries)
    }

    override fun onRoute(path: String, queries: Queries, results: List<Results>) {
        print(path + queries + results)
        results.getOrNull(0)?.setOne(Result(100))
    }

    companion object : AggregateCompanion() {
        override val URI: String = "a://b"

        override val DEPENDENCIES: List<String> = listOf()

        override val EVENT_MSGS: List<String> = listOf()

        override val CREATOR = { TestSimpleAggregate() }
    }
}