package com.mars.component.detail.api

import com.mars.component.detail.value.TestParcelizeBean
import com.mars.component.detail.value.TestSerializableBean
import com.rubik.annotations.route.function.RFunction

class ApiSerialization {
    @RFunction(path = "api/serialization/parcel-bean")
    fun doSthParcelBean(parcelBean: TestParcelizeBean?): TestParcelizeBean {
        println(" AP DBG DETAIL  doSthParcelBean begin parcelBean:${parcelBean?.d2}!!!")
        return TestParcelizeBean(430,"from detail!")
    }

    @RFunction(path = "api/serialization/parcel-array")
    fun doSthParcelArray(parcels: Array<TestParcelizeBean>): Array<TestParcelizeBean> {
        println(" AP DBG DETAIL  doSthParcelArray begin parcels:${parcels.size}!!!")
        println(" AP DBG DETAIL  doSthParcelArray begin parcels 0 :${parcels[0].d2}!!!")
        return arrayOf(
            TestParcelizeBean(430, "from detail 11!"),
            TestParcelizeBean(430, "from detail 22!"),
            TestParcelizeBean(430, "from detail 33!")
        )
    }

    @RFunction(path = "api/serialization/parcel-list")
    fun doSthParcelList(parcels: List<TestParcelizeBean>): List<TestParcelizeBean> {
        println(" AP DBG DETAIL  doSthParcelList begin parcels:${parcels.size}!!!")
        println(" AP DBG DETAIL  doSthParcelList begin parcels 0 :${parcels[0].d2}!!!")
        return listOf(
            TestParcelizeBean(430, "from detail 22!"),
            TestParcelizeBean(430, "from detail 33!"),
            TestParcelizeBean(430, "from detail 44!")
        )
    }


    @RFunction(path = "api/serialization/Serializable-bean")
    fun doSthSerializableBean(serializableBean: TestSerializableBean): TestSerializableBean {
        println(" AP DBG DETAIL  doSthSerializableBean begin serializableBean:${serializableBean.d2}!!!!!!")
        return TestSerializableBean(530,"from detail!")
    }
}