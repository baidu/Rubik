package com.rubik.test.router

import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory

class TestSimpleAggregateFactory : AggregateFactory {
    override fun create(uri: String): Aggregatable? {
        return TestSimpleAggregate()
    }

    override fun createByMsg(msg: String): List<Aggregatable> {
        return listOf(TestSimpleAggregate())
    }

    override fun touch(uri: String): Boolean {
        return true
    }

    override fun touchByMsg(msg: String): Boolean {
        return true
    }

}