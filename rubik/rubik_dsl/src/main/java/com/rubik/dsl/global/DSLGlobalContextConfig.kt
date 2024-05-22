package com.rubik.dsl.global

import com.rubik.dsl.context.source.DSLMavenSource

open class DSLGlobalContextConfig : DSLMavenSource() {

    var publishVersion: String? = null

    fun publish(version: String) {
        publishVersion = version
    }
}