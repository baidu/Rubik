package com.mars.component.home.test.kotlin

import android.content.Context
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaContext
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean

class TestKotlinInvokeJavaFunctionTask {
    fun invoke(context: Context, onFinish: (String) -> Unit) {

        val tag = " B FUNCTION TASK "

        DetailJavaContext.touch {
            onFinish(" XX DBG B touch DetailJavaContext.URI：touch!!!")
        }.miss {
            onFinish(" XX DBG B touch DetailJavaContext.URI：miss!!!")
        }

        // 无参无返回值
        DetailJavaContext.doSth()

        // 属性
        DetailJavaContext.propertyProperty { value -> onFinish("$tag NA DBG JAVA propertyProperty value:$value") }

        // 接口异步返回
        DetailJavaContext.doSthAsyncOpen {  v1, v2 ->
            onFinish("$tag NA DBG JAVA doSthAsyncOpen value:$v1,$v2")
        }

        DetailJavaContext.doSthAsyncInterface {  v1, v2 ->
            onFinish("$tag NA DBG JAVA doSthAsyncInterface value:$v1,$v2")
        }

        // Companion方法
        DetailJavaContext.doSthStatic(8,"asd",22L){value ->
            onFinish("$tag NA DBG JAVA doSthCompanion value:$value")
        }

        // Companion属性
        DetailJavaContext.propertyStatic { value -> onFinish("$tag NA DBG JAVA propertyCompanion value:$value") }

        // Bean参数返回值
        DetailJavaContext.doSthBeanProvideInstanceByFunc("msg from HOME", TestJavaBean(8, null)) { value ->
            onFinish("$tag NA DBG JAVA doSthBean value:${value.d1}")
        }

    }
}