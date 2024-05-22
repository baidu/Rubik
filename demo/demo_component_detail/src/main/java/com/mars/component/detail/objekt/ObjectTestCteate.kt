package com.mars.component.detail.objekt


import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RObject
import com.rubik.annotations.route.RRoute

class ObjectTestCreate
@RObject
constructor(
    val v1: Int, val v2: String
) {

    @RObject
    constructor(
        v1: Int,
        v2: Int,
        v3: Int,
        v4: Int
    ) : this(v1, v1.toString())

    @RRoute(path = "do-sth-create-common-instance")
    fun doSthInCreateObject(): TestDataBean {
        println(" AP DBG DETAIL  doSthInCreateObject begin this:${this.hashCode()} value:$v2!!!")
        return TestDataBean(985, "gds")
    }

}
