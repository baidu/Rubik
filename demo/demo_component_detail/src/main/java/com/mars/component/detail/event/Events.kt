package com.mars.component.detail.event

import android.content.Context
import com.rubik.annotations.context.REvent
import com.rubik.annotations.context.instance.REventInstance
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
}

fun initSdk() {
    println(" CT DBG initSdk begin !!!")
}

private val life = EventsByInstance()

@REventInstance(provideForTag = "events-by-instance")
@REventInstance(provideForTag = "events-by-instance")
fun provideEventsInstance(bool: Boolean) = life