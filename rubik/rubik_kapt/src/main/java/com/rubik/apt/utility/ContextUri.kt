package com.rubik.apt.utility

import com.rubik.annotations.context.REvent
import com.rubik.annotations.context.assist.REventAssist
import com.rubik.annotations.context.instance.REventInstance
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.RValue
import com.rubik.annotations.route.assist.RRouteAssist
import com.rubik.annotations.route.function.RFunction
import com.rubik.annotations.route.instance.RRouteInstance
import com.rubik.annotations.route.page.RPage

private fun contextUri(context:String,defaultScheme: String?) =
    if (context.contains("://") || context.isBlank()|| defaultScheme.isNullOrBlank() ) context else "$defaultScheme://$context"

fun RRoute.contextUri(defaultScheme: String?) = contextUri(uri.ifBlank { context }, defaultScheme)
fun RFunction.contextUri(defaultScheme: String?) = contextUri(uri, defaultScheme)
fun RPage.contextUri(defaultScheme: String?) = contextUri(uri, defaultScheme)
fun RRouteInstance.contextUri(defaultScheme: String?) = contextUri(uri, defaultScheme)
fun RRouteAssist.contextUri(defaultScheme: String?) = contextUri(uri.ifBlank { context }, defaultScheme)
fun RValue.contextUri(defaultScheme: String?) = contextUri(uri.ifBlank { context }, defaultScheme)
fun REvent.contextUri(defaultScheme: String?) = contextUri(uri.ifBlank { context }, defaultScheme)
fun REventAssist.contextUri(defaultScheme: String?) = contextUri(uri.ifBlank { context }, defaultScheme)
fun REventInstance.contextUri(defaultScheme: String?) = contextUri(uri, defaultScheme)
