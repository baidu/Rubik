package com.mars.component.detail.callback


import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RCallback
import com.rubik.annotations.route.RRoute

@RCallback
interface CallbackTestInterface {
    @RCallback
    fun callbackInt(i:Int)

    @RCallback
    fun callbackBean(bean:TestDataBean)

    @RCallback
    fun callbackBeanReBean(bean:TestDataBean?)
}

@RRoute(path = "do-sth-callback-object")
fun doSthCallbackObject(callback: CallbackTestInterface) {
    println(" AP DBG DETAIL  doSthCallbackObject begin this:${callback.hashCode()} !!!")
    callback.callbackInt(11)
    callback.callbackBean(TestDataBean(23, "sdsd"))
    callback.callbackBeanReBean(TestDataBean(24, "dfdf"))
}
