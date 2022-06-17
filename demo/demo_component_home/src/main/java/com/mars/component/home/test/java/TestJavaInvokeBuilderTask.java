package com.mars.component.home.test.java;

import android.content.Context;

import com.rubik.router.Router;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext;
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean;
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestListBean;

public class TestJavaInvokeBuilderTask {
    public void invoke(Context context, Function1<String, Unit> onFinish) {
        String tag = " B JAVA BUILDER TASK ";

        Router.touch(DetailContext.URI, () -> {
            onFinish.invoke(" XX DBG B touch AContext.URI：touch!!!");
            return null;
        }).miss(() -> {
            onFinish.invoke(" XX DBG B touch AContext.URI：miss!!!");
            return null;
        });

        Router.touch("yyyyy", () -> {
            onFinish.invoke(" XX DBG B touch yyyyy：touch!!!");
            return null;
        }).miss(() -> {
            onFinish.invoke(" XX DBG B touch yyyyy：miss!!!");
            return null;
        });

        Object results1 = Router.builder()
                .uri(DetailContext.Uris.DO_STH_HOF)
                .with("a0", 10)
                .with("a1", "889")
                .with("a2", new TestDataBean(2, "567"))
                .build()
                .routeForResult(Integer.TYPE);
        onFinish.invoke(tag + " NA DBG doSthHOF doSthHOFResult1:" + results1);

        Router.builder()
                .uri(DetailContext.Uris.DO_STH_BEAN)
                .with("a1", new TestDataBean(33, "xxx"))
                .result(TestListBean.class, value -> {
                    onFinish.invoke(tag + " NA DBG doSthBean value:" + ((TestListBean) value).getD1());
                    return null;
                })
                .build()
                .route();


    }
}