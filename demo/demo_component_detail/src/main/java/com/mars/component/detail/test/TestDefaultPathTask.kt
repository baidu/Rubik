package com.mars.component.detail.test

import android.app.Activity
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.function.RFunction
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class TestDefaultPathTask {

    @RFunction
    fun testName(){

    }

    @RFunction
    fun TEST_NAME_UP(){

    }

    @RFunction
    val testNameString = ""

    @RRoute(forResult = false,resultType = Any::class)
    constructor()
}