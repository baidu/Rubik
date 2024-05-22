package com.mars.component.detail.event

import android.content.Context
import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.context.REvent
import com.rubik.annotations.route.RInstance
import com.rubik.annotations.route.RResult
import com.rubik.context.LifeCycleEvent

data class EventsWithLambda(
    @REvent(msg = LifeCycleEvent.INIT)
    val init: () -> Unit = {
        println(" CT DBG init DETAIL EventsWithLambda begin !!! !!!")
        initSdk()
    },
    @REvent(msg = LifeCycleEvent.DESTROY)
    val destroy: () -> Unit = {
        println(" CT DBG destroy DETAIL EventsWithLambda begin !!!")
    }
)

class Events {
    @REvent(msg = LifeCycleEvent.INIT)
    fun onInit(context: Context, parameter1: String) {
        println(" CT DBG init DETAIL Events begin :${context.hashCode()} parameter1:$parameter1!!!")
        initSdk()
    }

    @REvent(msg = LifeCycleEvent.DESTROY)
    fun onDestroy(context: Context, parameter1: String) {
        println(" CT DBG destroy DETAIL Events begin :${context.hashCode()} parameter1:$parameter1!!!")
    }
}

class EventsByInstance {
    @REvent(msg = "MY_INIT", tag = "events-by-instance")
    fun onInit(arg1: Any, arg2: Any, arg3: Any) {
        println(" CT DBG MY_INIT DETAIL:${hashCode()}  arg1:$arg1 arg2:$arg2 arg3:$arg3 EventsByInstance begin !!!")
        initSdk()
    }

    @REvent(msg = "MY_DESTROY", tag = "events-by-instance")
    fun onDestroy(arg1: Any, arg2: Any, arg3: Any) {
        println(" CT DBG MY_DESTROY DETAIL:${hashCode()}  arg1:$arg1 arg2:$arg2 arg3:$arg3  EventsByInstance begin !!!")
    }

    @REvent(msg = "MY_CALLBACK_RES", tag = "events-by-instance")
    fun onCallbackRes(arg1: Any, @RResult result: (Int) -> Unit) {
        println(" CT DBG MY_CALLBACK DETAIL:${hashCode()}  arg1:$arg1 result:$result EventsByInstance begin !!!")
    }

    @REvent(msg = "MY_CALLBACK_RES_BEAN", tag = "events-by-instance")
    fun onCallbackResBean(arg1: Any, @RResult result: (TestDataBean) -> Unit) {
        println(" CT DBG MY_CALLBACK_RES_BEAN DETAIL:${hashCode()}  arg1:$arg1 result:$result EventsByInstance begin !!!")
    }

    @REvent(msg = "MY_CALLBACK_BEAN", tag = "events-by-instance")
    fun onCallbackBean(arg1: Any, bean: TestDataBean) {
        println(" CT DBG MY_CALLBACK_BEAN DETAIL:${hashCode()}  arg1:$arg1 result:$bean EventsByInstance begin !!!")
    }
}

fun initSdk() {
    println(" CT DBG initSdk begin !!!")
}

private val life = EventsByInstance()

@RInstance(provideForTag = "events-by-instance")
@RInstance(provideForTag = "events-by-instance")
fun provideEventsInstance(bool: Boolean) = life