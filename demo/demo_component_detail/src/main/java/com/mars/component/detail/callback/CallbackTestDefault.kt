package com.mars.component.detail.callback


import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RCallback

@RCallback
interface CallbackTestDefault {

    fun callbackDefault1(int1: Int)

    fun callbackDefault2(bean:TestDataBean?)
}
