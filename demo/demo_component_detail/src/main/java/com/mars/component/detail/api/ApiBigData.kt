package com.mars.component.detail.api

import com.mars.component.detail.value.TestDataBean
import com.mars.component.detail.value.TestParcelizeBean
import com.mars.util_library.TestClassA
import com.mars.util_library.TestLibDataBean
import com.rubik.annotations.route.function.RFunction

class ApiBigData {

    private val testArraySize = 200001
    private val testListSize = 200001

    @RFunction(path = "api/bigdata/json-array")
    fun getBigJsonArray(array: Array<TestDataBean>): Array<TestDataBean> {
        println(" AP DBG DETAIL  getBigJsonArray begin array:${array.size}!!!")
        return Array(testArraySize) {
            TestDataBean(it, "from detail! array:$it")
        }
    }

    @RFunction(path = "api/bigdata/parcel-array")
    fun getBigParcelArray(array: Array<TestParcelizeBean>): Array<TestParcelizeBean> {
        println("  AP DBG DETAIL  getBigJsonArray begin array:${array.size}!!!")
        return  Array(testArraySize) {
            TestParcelizeBean(it, "from detail! array:$it")
        }
    }

    @RFunction(path = "api/bigdata/lib-array")
    fun getBigLibArray(array: Array<TestLibDataBean>): Array<TestLibDataBean> {
        println(" AP DBG DETAIL  getBigJsonArray begin array:${array.size}!!!")
        return Array(testArraySize) {
            TestLibDataBean(it, "from detail! array:$it")
        }
    }

    @RFunction(path = "api/bigdata/json-list")
    fun getBigJsonList(list: List<TestDataBean>): List<TestDataBean> {
        println(" AP DBG DETAIL  getBigJsonList begin list:${list.size}!!!")
        return MutableList(testListSize){
            TestDataBean(it, "from detail! list:$it")
        }
    }

    @RFunction(path = "api/bigdata/parcel-list")
    fun getBigParcelList(list: List<TestParcelizeBean>): List<TestParcelizeBean> {
        println(" AP DBG DETAIL  getBigJsonList begin list:${list.size}!!!")
        return MutableList(testListSize){
            TestParcelizeBean(it, "from detail! list:$it")
        }
    }

    @RFunction(path = "api/bigdata/lib-list")
    fun getBigLibList(list: List<TestLibDataBean>): List<TestLibDataBean> {
        println(" AP DBG DETAIL  getBigJsonList begin list:${list.size}!!!")
        return List(testListSize){
            TestLibDataBean(it, "from detail! list:$it")
        }
    }

    @RFunction(path = "api/bigdata/parcel-arraylist")
    fun getBigParcelArrayList(list: List<TestParcelizeBean>): MutableList<TestParcelizeBean> {
        println(" AP DBG DETAIL  getBigJsonList begin arraylist:${list.size}!!!")
        return MutableList(testListSize){
            TestParcelizeBean(it, "from detail! arraylist:$it")
        }
    }

}