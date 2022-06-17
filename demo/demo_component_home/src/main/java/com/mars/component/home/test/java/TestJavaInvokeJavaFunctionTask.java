package com.mars.component.home.test.java;

import android.content.Context;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaContext;
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean;

public class TestJavaInvokeJavaFunctionTask {
    public void invoke(Context context, Function1<String, Unit> onFinish) {
        // 无参无返回值

        String tag = " B JAVA FUNCTION TASK ";

        DetailJavaContext.touch(() -> {
            onFinish.invoke(" XX DBG B touch DJavaContext.URI：touch!!!");
            return null;
        }).miss(() -> {
            onFinish.invoke(" XX DBG B touch DJavaContext.URI：miss!!!");
            return null;
        });

        // 无参无返回值
        DetailJavaContext.doSth();

        // 属性
        DetailJavaContext.propertyProperty(value -> {
            onFinish.invoke(tag+" NA DBG JAVA propertyProperty value:"+value);
            return null;
        });

        // 接口异步返回
        DetailJavaContext.doSthAsyncOpen((v1, v2) -> {
            onFinish.invoke(tag + " NA DBG JAVA doSthAsyncOpen value:" + v1 + "," + v2);
            return null;
        });

        DetailJavaContext.doSthAsyncInterface ((v1, v2) -> {
            onFinish.invoke(tag + " NA DBG JAVA doSthAsyncInterface value:" + v1 + "," + v2);
            return null;
        });

        // Companion方法
        DetailJavaContext.doSthStatic(8, "asd", 22L, value -> {
            onFinish.invoke(tag + " NA DBG JAVA doSthCompanion value:" + value);
            return null;
        });

        // Companion属性
        DetailJavaContext.propertyStatic(value -> {
            onFinish.invoke(tag + " NA DBG JAVA propertyCompanion value:" + value);
            return null;
        });

        // Bean参数返回值
        DetailJavaContext.doSthBeanProvideInstanceByFunc("msg from HOME", new TestJavaBean(8, null), value -> {
            onFinish.invoke(tag + " NA DBG JAVA doSthBean:" + value.getD1());
            return null;
        });
    }
}