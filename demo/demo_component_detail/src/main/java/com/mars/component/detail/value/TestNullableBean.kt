package com.mars.component.detail.value

import com.google.gson.annotations.SerializedName
import com.rubik.annotations.route.RValue

@RValue
data class TestNullableBean(@SerializedName("ddd111") val d1: Int?, @SerializedName("ddd222") val d2: String?)