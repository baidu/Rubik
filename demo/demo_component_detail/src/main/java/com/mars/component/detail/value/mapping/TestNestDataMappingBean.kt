package com.mars.component.detail.value.mapping

import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RValue


data class TestNestDataMappingBean @RValue constructor(
    val d1: TestDataBean?,
    val d2: TestDataMappingBean?,
    val d3: TestNestDataMappingBean?,
    val d4: List<TestDataBean>?,
    val d5: List<TestDataMappingBean>?
)