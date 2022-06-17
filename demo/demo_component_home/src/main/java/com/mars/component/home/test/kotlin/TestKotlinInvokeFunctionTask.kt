package com.mars.component.home.test.kotlin

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import com.rubik.route.mapping.query
import com.rubik.router.bundleQueries
import com.rubik.router.property
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext.Companion.doSthExt
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestSerializableBean

class TestKotlinInvokeFunctionTask {
    fun invoke(context: Context, onFinish: (String) -> Unit) {
        // 无参无返回值

        val tag = " B FUNCTION TASK "

        DetailContext.touch {
            onFinish(" XX DBG B touch DetailContext：touch!!!")
        }.miss {
            onFinish(" XX DBG B touch DetailContext：miss!!!")
        }

        DetailContext.doSth()

        // 高阶函数
        val doSthHOFResult = DetailContext.doSthHof(3, "123", TestDataBean(1, "456"))

        onFinish("$tag NA DBG doSthHOF doSthHOFResult:$doSthHOFResult")

        // 属性
        DetailContext.propertyProperty { value -> onFinish("$tag NA DBG propertyProperty value:$value") }

        // 高阶函数异步返回
        DetailContext.doSthAsync2HOF({ v1, v2 ->
            onFinish("$tag NA DBG doSthAsyncHOF 1 value:$v1,$v2")
        }, { v1, v2 ->
            onFinish("$tag NA DBG doSthAsyncHOF 2 value:$v1,$v2")
        })

        // 接口异步返回
        DetailContext.doSthAsyncOpen("textUri") {  v1, v2 ->
            onFinish("$tag NA DBG doSthAsyncOpen value:$v1,$v2")
        }

        DetailContext.doSthAsyncInterface {  v1, v2 ->
            onFinish("$tag NA DBG doSthAsyncInterface value:$v1,$v2")
        }

        // 返回View
        DetailContext.viewGet(context) { view ->
            onFinish("$tag NA DBG getView value:$view")
        }

        // Companion方法
        DetailContext.doSthCompanion(8,"asd",22L){value ->
            onFinish("$tag NA DBG doSthCompanion value:$value")
        }

        // Companion属性
        DetailContext.propertyCompanion { value ->  onFinish("$tag NA DBG propertyCompanion value:$value")}

        // Companion高阶函数
        DetailContext.doSthHOFCompanion(6){ value ->
            onFinish("$tag NA DBG doSthHOFCompanion value:$value")
        }

        // 顶层方法
        DetailContext.doSthTop(
            arrayOf(1),
            listOf(2, 3),
            arrayOf("asd"),
            listOf("ghj", "zxc"),
            arrayOf(TestDataBean(100, "101")),
            listOf(TestDataBean(101, "102"),TestDataBean(103, "104"))
        ) { list ->
            list.forEach {
                onFinish("$tag NA DBG doSthTop d1:${it.d1} d1:${it.d2}")
            }
        }

        // 顶层高阶函数
        DetailContext.doSthHOFTop(Unit) {

        }

        // 顶层属性
        DetailContext.propertyTop { value ->
            onFinish("$tag NA DBG propertyTop value:$value")
        }

        // 通过assist获取实例
        DetailContext.doSthProvideInstanceByFunc()

        DetailContext.doSthProvideInstanceByParameterFunc("2 value_ST 2", null, v1 = "2 value_ST- 2", v2 = 2888, v3 = 2999)

        DetailContext.doSthProvideInstanceByConstructor("2 value_ST 3", v1 = "2 value_ST- 3", v2 = 3888, v3 = 3999)

        // Bean参数返回值
        DetailContext.doSthBean(TestDataBean(8, null)) { value ->
            onFinish("$tag NA DBG doSthBean value:${value.d1}")
        }

        // 调用构造函数
        DetailContext.testBeanCreate(55, "ggghhh") { value ->
            onFinish("$tag NA DBG makeApiBean value:(${value.d1},${value.d2})")
        }

        // 指定返回类型
        val result = DetailContext.Fragment.page1()
        onFinish("$tag NA DBG makeApiFragment value:$result")

        // uri传递参数
        DetailContext.sthIdAName("vbn", "ghj", "yui", "987") { value ->
            onFinish("$tag NA DBG getSth value:$value")
        }

        //扩展函数
        100.doSthExt("tmp"){
            onFinish("$tag NA DBG doSthExt value:$it")
        }

        // 传递ResultReceiver
        DetailContext.doSthResultReceiver(
            object : ResultReceiver(Handler()) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    super.onReceiveResult(resultCode, resultData)
                    onFinish("$tag NA DBG doSthResultReceiver resultCode:$resultCode resultData1:${resultData?.property<String>(
                        "fromA"
                    )} resultData2:${resultData?.property<TestDataBean>(
                        "BEAN_fromA"
                    )?.d2}")
                }
            }
        ) { value ->
            value.send(1010, bundleQueries {
                "fromB" with " this is B "
                "BEAN_fromB" with TestDataBean(333,"B3w3w3w")
            })
            onFinish("$tag NA DBG doSthResultReceiver value:$value")
        }

        // 变长参数
        DetailContext.doSthVararg(1, "qwe111", "asd2")

       val parcelizeBean = DetailContext.Api.Serialization.parcelBean(TestParcelizeBean(1, "from home!"))
        onFinish("$tag NA DBG Serialization value:${parcelizeBean?.d2}")



    //        // 原始value
//        DetailContext.doKeepOriginalValue(A4Bean(135,"135X")){
//            onFinish("$tag NA DBG doKeepOriginalValue d1:${it.d1} d2:${it.d2}")
//        }
    }
}