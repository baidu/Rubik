package com.mars.component.detail.value

import com.google.gson.annotations.SerializedName
import com.rubik.annotations.route.RValue
import java.io.Serializable

@RValue
data class TestSerializableBean(
    @SerializedName("data1") val d1: Int?,
    @SerializedName("data2") val d2: String?
):Serializable