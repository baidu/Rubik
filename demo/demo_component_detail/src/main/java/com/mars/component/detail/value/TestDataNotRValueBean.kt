package com.mars.component.detail.value

import com.rubik.annotations.route.RValue
import com.google.gson.annotations.SerializedName

data class TestDataNotRValueBean(@SerializedName("data1") val d1: Int?, @SerializedName("data2") val d2: String?)