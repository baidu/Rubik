package com.mars.component.detail.api

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RRoute
import com.rubik.route.mapping.query
import com.rubik.router.bundleQueries
import com.rubik.router.property

class ApisWithResultReceiver {
    // 传递ResultReceiver
    @RRoute(path = "doSthResultReceiver")
    fun doSthResultReceiver(result: ResultReceiver): ResultReceiver {
        result.send(100, bundleQueries {
            "fromA" with " this is A "
            "BEAN_fromA" with TestDataBean(555,"A5r5r5r")
        })
        return object : ResultReceiver(Handler()) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                super.onReceiveResult(resultCode, resultData)
                println(
                    " AP DBG DETAIL  doSthResultReceiver begin resultCode:$resultCode  " +
                            "resultData1:${resultData?.property<String>("fromB"
                    )} resultData2:${resultData?.property<TestDataBean>(
                        "BEAN_fromB"
                    )?.d2} !!!"
                )
            }
        }
    }
}