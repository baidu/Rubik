package com.mars.component.home.test.kotlin

import android.content.Context
import com.ktnail.x.Logger
import com.mars.util_library.TestLibDataBean
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean


class TestBigData {
    fun invoke(context: Context, onFinish: (String) -> Unit) {
        // 无参无返回值
        val tag = " B FUNCTION TASK "

        val testArraySize = 199999
        val testListSize = 199999

        val startTimeAL = System.currentTimeMillis()
        val resultAL = DetailContext.Api.Bigdata.libArray(Array(testArraySize){
            TestLibDataBean(it, "from home! array:$it")
        })
        val alCost = System.currentTimeMillis() - startTimeAL
        "$tag TestBigData ARRAY LIB size:${resultAL?.size} cost:$alCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }

        val startTimeAP = System.currentTimeMillis()
        val resultAP =DetailContext.Api.Bigdata.parcelArray(Array(testArraySize){
            TestParcelizeBean(it, "from home! array:$it")
        })
        val apCost = System.currentTimeMillis() - startTimeAP
        "$tag TestBigData ARRAY PARCEL size:${resultAP?.size} cost:$apCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }

        val startTimeAJ = System.currentTimeMillis()
        val resultAJ = DetailContext.Api.Bigdata.jsonArray(Array(testArraySize){
            TestDataBean(it, "from home! array:$it")
        })
        val ajCost = System.currentTimeMillis() - startTimeAJ
        "$tag TestBigData ARRAY JSON size:${resultAJ?.size} cost:$ajCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }


        val startTimeLL = System.currentTimeMillis()
        val resultLL = DetailContext.Api.Bigdata.libList(MutableList(testListSize){
            TestLibDataBean(it, "from home! list:$it")
        })
        val llCost = System.currentTimeMillis() - startTimeLL
        "$tag TestBigData List LIB size:${resultLL?.size} cost:$llCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }

        val startTimeLP = System.currentTimeMillis()
        val resultLP = DetailContext.Api.Bigdata.parcelList(List(testListSize){
            TestParcelizeBean(it, "from home! list:$it")
        })
        val lpCost = System.currentTimeMillis() - startTimeLP
        "$tag TestBigData List PARCEL size:${resultLP?.size} cost:$lpCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }

        val startTimeALP = System.currentTimeMillis()
        val resultALP = DetailContext.Api.Bigdata.parcelArraylist(MutableList(testListSize){
            TestParcelizeBean(it, "from home! arrayList:$it")
        })
        val alpCost = System.currentTimeMillis() - startTimeALP
        "$tag TestBigData ArrayList PARCEL size:${resultALP?.size} cost:$alpCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }

        val startTimeLJ = System.currentTimeMillis()
        val resultLJ = DetailContext.Api.Bigdata.jsonList(List(testListSize){
            TestDataBean(it, "from home! list:$it")
        })
        val ljCost = System.currentTimeMillis() - startTimeLJ
        "$tag TestBigData List JSON size:${resultLJ?.size} cost:$ljCost ".let { log->
            Logger.d(log)
            onFinish(log)
        }



    }
}