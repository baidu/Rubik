package com.mars.component.home.api

import com.rubik.annotations.route.function.RFunction

class TestOne {
    @RFunction(path = "getName")
    fun getName() = "name"

    @RFunction(path = "getName1")
    fun getName1() = "name1"
}