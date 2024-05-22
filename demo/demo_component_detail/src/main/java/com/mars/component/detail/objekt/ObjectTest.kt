package com.mars.component.detail.objekt


import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RObject
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.function.RFunction

@RObject
class ObjectTest(val value: String) {

    @RRoute(path = "do-sth-in-common-instance")
    fun doSthInObject(): TestDataBean {
        println(" AP DBG DETAIL  doSthProvideObject begin this:${this.hashCode()} value:$value!!!")
        return TestDataBean(645, "ahr")
    }

    @RRoute(path = "do-sth-in-common-instance-parameter")
    fun doSthInObject2(v1: String, v2: Int?, v3: Int?) {
        println(" AP DBG DETAIL  doSthProvideObject2 begin this:${this.hashCode()} value:$value " +
                "v1:$v1 v2:$v2 v3:$v3 :!!!")
    }

    @RFunction(path = "object/get/do-sth-in-common-ins")
    fun doSthInObject3(v1: String, v2: Int?, v3: Int?) {
        println(" AP DBG DETAIL  doSthProvideObject3 begin this:${this.hashCode()} value:$value " +
                "v1:$v1 v2:$v2 v3:$v3 :!!!")
    }

}


// get and send

@RRoute(path = "get-a-other-common-instance")
fun getAOtherObjectTest(i: Int) :ObjectTest= ObjectTest("x1x").apply {
    println(" AP DBG DETAIL  getAOtherObjectTestDa ${hashCode()} value:$value")
}

@RRoute(path = "send-back-common-instance")
fun senBackObjectTest(obj: ObjectTest) {
    println(" AP DBG DETAIL  senBackObjectTest ${obj.hashCode()} value:${obj?.value}")
}

@RFunction(path = "get-a-lot-of-common-instance")
fun getALotOfObjectTest(i: Int):List<ObjectTest> = mutableListOf(
    ObjectTest("yy1").apply {
        println(" AP DBG DETAIL  getALotOfObjectTest 1 ${hashCode()} value:$value")
    },
    ObjectTest("yy2").apply {
        println(" AP DBG DETAIL  getALotOfObjectTest 2 ${hashCode()} value:$value")
    },
    ObjectTest("yy3").apply {
        println(" AP DBG DETAIL  getALotOfObjectTest 3 ${hashCode()} value:$value")
    })

@RFunction(path = "send-back-a-lot-of-common-instance")
fun senBackALotOfObjectTest(objs: Map<String, ObjectTest>) {
    objs.forEach { (key, value) ->
        println(" AP DBG DETAIL  senBackALotOfObjectTest ${value.hashCode()} value:${value?.value} key:$key")
    }
}


@RRoute(path = "get-a-other-common-instance-null")
fun getAOtherObjectTestNull(i: Int) :ObjectTest?= ObjectTest("x1x").apply {
    println(" AP DBG DETAIL  getAOtherObjectTestNull ${hashCode()} value:$value")
}

@RRoute(path = "send-back-common-instance-null")
fun senBackObjectTestNull(obj: ObjectTest?) {
    println(" AP DBG DETAIL  senBackObjectTestNull ${obj.hashCode()} value:${obj?.value}")
}

@RFunction(path = "get-a-lot-of-common-instance-null")
fun getALotOfObjectTestNull(i: Int):List<ObjectTest?>? = mutableListOf(
    ObjectTest("yy1").apply {
        println(" AP DBG DETAIL  getALotOfObjectTestNull 0 ${hashCode()} value:$value")
    },
    ObjectTest("yy2").apply {
        println(" AP DBG DETAIL  getALotOfObjectTestNull 1 ${hashCode()} value:$value")
    },
    ObjectTest("yy3").apply {
        println(" AP DBG DETAIL  getALotOfObjectTestNull 2 ${hashCode()} value:$value")
    })

@RFunction(path = "send-back-a-lot-of-common-instance-null")
fun senBackALotOfObjectTesNull(objs: Map<String?, ObjectTest?>?) {
    objs?.forEach { (key, value) ->
        println(" AP DBG DETAIL  senBackALotOfObjectTesNull ${value.hashCode()} value:${value?.value} key:$key")
    }
}