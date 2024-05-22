package com.mars.component.detail.value.mapping

import com.google.gson.annotations.SerializedName
import com.rubik.annotations.route.RValue


data class TestDataMappingBean @RValue constructor(
    @SerializedName("data1") val d1: Int?,
    @SerializedName("data2")
    val d2: String?
)