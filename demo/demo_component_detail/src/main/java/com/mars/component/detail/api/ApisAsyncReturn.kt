package com.mars.component.detail.api

import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RResult
import com.rubik.annotations.route.RRoute

class ApisAsyncReturn {

    // 高阶函数异步返回
    @RRoute(path = "doSthAsyncHOF")
    fun doSthAsyncHOF(@RResult hof: (String, TestDataBean) -> Unit) {
        println(" AP DBG DETAIL  doSthAsyncHOF begin !!!")
        hof("dfg", TestDataBean(101, "xxx"))
    }

    // 高阶函数异步返回
    @RRoute(path = "doSthAsyncHOFNullable")
    fun doSthAsyncHOFNullable(
        @RResult hof: ((String, TestDataBean) -> Unit)?,
        hof2p: ((String, TestDataBean) -> Unit)?
    ) {
        println(" AP DBG DETAIL  doSthAsyncHOFNullable begin !!!")
        hof?.invoke("dfg", TestDataBean(101, "xxx"))
    }

    @RRoute(path = "doSthAsync2HOF")
    fun doSthAsyncHOF(
        @RResult hof: (String, Int) -> Unit,
        @RResult hof2: (String, Int) -> Unit
    ) {
        println(" AP DBG DETAIL  doSthAsyncHOF begin !!!")
        hof("dfg", 22)
        hof2("dfg2", 222)
    }

    // 接口异步返回
    @RRoute(path = "doSthAsyncOpen")
    fun doSthAsyncOpen(uri: String, @RResult results: Callback?) {
        println(" AP DBG DETAIL  doSthAsyncOpen begin uri:$uri !!!")
        results?.onCall("zxc", 33)
    }

    @RRoute(path = "do-sth-async-interface")
    fun doSthAsyncInterface(@RResult onResult: Callbackable) {
        println(" AP DBG DETAIL  doSthAsyncInterface begin !!!")
        onResult.onCall("zxc", 33)
    }

    @RRoute(path = "do-sth-async-2-interface")
    fun doSthAsync2Interface(@RResult onResult: Callbackable, @RResult onResult2: Callbackable, @RResult onResult3: BeanCallbackable) {
        println(" AP DBG DETAIL  doSthAsyncInterface begin !!!")
        onResult.onCall("zxc", 33)
        onResult2.onCall("zxc2", 332)
        onResult3.onCall(TestDataBean(0, "abd"))
    }
}

open class Callback {
    open fun onCallNone(v1: String?, v2: Int) {
        // nothing
    }

    @RResult
    open fun onCall(v1: String?, v2: Int) {
        // nothing
    }
}

interface Callbackable {
    fun onCall(v1: String?, v2: Int)
}

interface BeanCallbackable {
    fun onCall(v: TestDataBean?)
}
