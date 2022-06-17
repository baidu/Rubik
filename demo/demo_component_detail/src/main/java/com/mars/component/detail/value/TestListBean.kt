package com.mars.component.detail.value

import com.rubik.annotations.route.RValue
import com.google.gson.annotations.SerializedName

@RValue
data class TestListBean(@SerializedName("data1") val d1: Int, val d2: List<String>?) {
    constructor(b1: Int) : this(b1, listOf())
}