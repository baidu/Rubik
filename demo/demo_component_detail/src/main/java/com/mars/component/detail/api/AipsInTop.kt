package com.mars.component.detail.api

import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RRoute


//顶层方法
@RRoute(path = "doSthTop")
fun doSthTop(
    ints: Array<Int>,
    li: List<Int?>,
    strings: Array<String>,
    ls: List<String>,
    beans: Array<TestDataBean>,
    lb: List<TestDataBean>
): List<TestDataBean> {
    println(" AP DBG DETAIL  doSthTop begin beans:${ints[0]} li:${li[0]} ls:${ls[0]} strings:${strings[0]} beans:${beans[0]} lb:${lb[0]} !!!")
    return lb
}

//顶层高阶函数
@RRoute(path = "doSthHOFTop")
val doSthHOFTop: (Unit) -> Unit = {
    println(" AP DBG DETAIL  doSthHOFTop begin !!!")
}

//顶层属性
@RRoute(path = "property/top")
var propertyTop: String = "bnm"
