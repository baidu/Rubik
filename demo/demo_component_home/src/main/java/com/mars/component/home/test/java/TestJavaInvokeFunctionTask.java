package com.mars.component.home.test.java;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext;
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean;

public class TestJavaInvokeFunctionTask {
    public void invoke(Context context, Function1<String, Unit> onFinish) {
        // 无参无返回值

        String tag = " B JAVA FUNCTION TASK ";

        DetailContext.touch(() -> {
            onFinish.invoke(" XX DBG B touch AContext.URI：touch!!!");
            return null;
        }).miss(() -> {
            onFinish.invoke(" XX DBG B touch AContext.URI：miss!!!");
            return null;
        });

        DetailContext.doSth();

        // 高阶函数
        @Nullable
        Integer doSthHOFResult = DetailContext.doSthHof(3, "123", new TestDataBean(1, "456"));

        onFinish.invoke(tag+" NA DBG doSthHOF doSthHOFResult:"+doSthHOFResult);

        // 属性
        DetailContext.propertyProperty(value -> {
            onFinish.invoke(tag+" NA DBG propertyProperty value:"+value);
            return null;
        });

        // 高阶函数异步返回
        DetailContext.doSthAsync2HOF((v1, v2) -> {
            onFinish.invoke(tag + " NA DBG doSthAsyncHOF 1 value:" + v1 + "," + v2);
            return null;
        },(v1, v2) -> {
            onFinish.invoke(tag + " NA DBG doSthAsyncHOF 2 value:" + v1 + "," + v2);
            return null;
        });

        // 接口异步返回
        DetailContext.doSthAsyncOpen("textUri", (v1, v2) -> {
            onFinish.invoke(tag + " NA DBG doSthAsyncOpen value:" + v1 + "," + v2);
            return null;
        });

        DetailContext.doSthAsyncInterface ((v1, v2) -> {
            onFinish.invoke(tag + " NA DBG doSthAsyncInterface value:" + v1 + "," + v2);
            return null;
        });

        // 返回View
        DetailContext.viewGet(context, view -> {
            onFinish.invoke(tag + " NA DBG getView value:" + view);
            return null;
        });

        // Companion方法
        DetailContext.doSthCompanion(8, "asd", 22L, value -> {
            onFinish.invoke(tag + " NA DBG doSthCompanion value:" + value);
            return null;
        });

        // Companion属性
        DetailContext.propertyCompanion(value -> {
            onFinish.invoke(tag + " NA DBG propertyCompanion value:" + value);
            return null;
        });

        // Companion高阶函数
        DetailContext.doSthHOFCompanion(6, value -> {
            onFinish.invoke(tag + " NA DBG doSthHOFCompanion value:" + value);
            return null;
        });

        // Bean参数返回值
        DetailContext.doSthBean(new TestDataBean(8, null), value -> {
            onFinish.invoke(tag + " NA DBG doSthBean:" + value.getD1());
            return null;
        });

        // 指定返回类型
        Fragment result = DetailContext.Fragment.page1();

        onFinish.invoke(tag + " NA DBG makeApiFragment value:" + result);
    }
}