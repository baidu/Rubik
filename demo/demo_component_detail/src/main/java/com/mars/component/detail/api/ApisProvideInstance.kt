package com.mars.component.detail.api


import com.rubik.annotations.route.RInstance
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.instance.RRouteInstance

class Task @RInstance(
    provideForPath = "do-sth-provide-instance-by-constructor"
) constructor(val value: String) {
    // 通过assist提供实例
    @RRoute(path = "do-sth-provide-instance-by-func")
    fun doSthProvideObject() {
        println(" AP DBG DETAIL  doSthProvideObject begin value:$value!!!")
    }

    @RRoute(path = "do-sth-provide-instance-by-func2")
    fun doSthProvideObject2() {
        println(" AP DBG DETAIL  doSthProvideObject begin value:$value!!!")
    }

    @RRoute(path = "do-sth-provide-instance-by-parameter-func")
    fun doSthProvideObject2(v1: String, v2: Int?, v3: Int?) {
        println(" AP DBG DETAIL  doSthProvideObjectByParameterFunction begin value:$value v1:$v1 v2:$v2 v3:$v3 :!!!")
    }

    @RRoute(path = "do-sth-provide-instance-by-constructor")
    fun doSthProvideObject3(v1: String, v2: Int?, v3: Int?) {
        println(" AP DBG DETAIL  doSthProvideObjectByConstructor begin value:$value v1:$v1 v2:$v2 v3:$v3 :!!!")
    }
}

@RInstance(provideForPath = "do-sth-provide-instance-by-func")
@RInstance(provideForPath = "do-sth-provide-instance-by-func2")
fun provideTask(): Task {
    println(" AP DBG DETAIL  provideTask begin !!!")
    return Task("TTT-CREATE BY FUNC")
}

@RInstance(provideForPath = "do-sth-provide-instance-by-parameter-func")
fun provideTask2(value: String, v0: Int?): Task {
    println(" AP DBG DETAIL  provideTask2 begin v0:$v0!!!")
    return Task(value)
}
