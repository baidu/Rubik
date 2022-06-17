package com.mars.component.home.event

import com.rubik.annotations.context.REvent
import com.rubik.context.LifeCycleEvent

class Events {
    @REvent(msg = LifeCycleEvent.INIT)
    fun myInit(){
        println(" CT DBG init HOME begin  !!!")
    }

    @REvent(msg = LifeCycleEvent.DESTROY)
    fun myDestory(){
        println(" CT DBG destroy HOME begin !!!")
    }

}
