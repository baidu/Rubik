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
        @RResult hofx1res: ((String, TestDataBean) -> Unit)?,
        hof2xx: ((String, TestDataBean) -> Unit)?,
//        @RResult hofx3res: ((String, Int, TestDataBean) -> TestDataBean)?,
        hof4xx: ((String, Int, TestDataBean) -> TestDataBean?)?
    ) {
        println(" AP DBG DETAIL  doSthAsyncHOFNullable begin !!!")
        hofx1res?.invoke("dfg", TestDataBean(101, "xxx"))
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

    @RRoute(path = "do-sth-async-3-interface")
    fun doSthAsync2Interface(
        @RResult onResult: Callbackable?,
        @RResult onResult2: Callbackable,
        @RResult onResult3: BeanCallbackable
//        @RResult onResult4: BeanReturnCallbackable,
//        func5: BeanReturnCallbackable
    ) {
        println(" AP DBG DETAIL  doSthAsyncInterface begin !!!")
        onResult?.onCall("zxc", 33)
        onResult2.onCall("zxc2", 332)
        onResult3.onCall(TestDataBean(0, "abd"))
    }

    @RRoute(path = "do-sth-async-interface-multi-func")
    fun doSthAsyncInterfaceMultiFunc(@RResult onResult: MultiCallback) {
        println(" AP DBG DETAIL  doSthAsyncInterface begin !!!")
        onResult.start("start1", 55)
        onResult.data(TestDataBean(1, "dfg"))
        onResult.stop("stop2", 66)
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

interface MultiCallback {
    @RResult
    fun start(v1: String?, v2: Int)

    @RResult
    fun data(v: TestDataBean?)

    @RResult
    fun stop(v1: String?, v2: Int)
}

interface BeanReturnCallbackable {
    fun onCall(v: TestDataBean?):TestDataBean
}