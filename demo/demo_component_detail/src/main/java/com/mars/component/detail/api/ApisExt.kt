package com.mars.component.detail.api

import com.rubik.annotations.route.RExtend
import com.rubik.annotations.route.RRoute

// 扩展函数
@RRoute(path = "do-sth-ext")
fun doSthExt(s: String, @RExtend i: Int): Int {
    println(" AP DBG DETAIL  doSthExt i:$i !!!")
    return i
}