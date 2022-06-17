package com.mars.component.detail.java.api;

import com.mars.component.detail.java.api.callback.Callback;
import com.mars.component.detail.java.api.callback.Callbackable;
import com.rubik.annotations.route.RResult;
import com.rubik.annotations.route.RRoute;

public class ApisAsyncReturn extends BaseApis {
    // 接口异步返回
    @RRoute(path = "do-sth-async-open")
    public void doSthAsyncOpen(@RResult Callback onResult) {
        println(" AP DBG JAVA doSthAsyncOpen begin !!!");
        onResult.onCall("zxc", 33);
    }

    @RRoute(path = "do-sth-async-interface")
    public void doSthAsyncInterface(@RResult Callbackable onResult) {
        println(" AP DBG JAVA doSthAsyncInterface begin !!!");
        onResult.onCall("zxc", 33);
    }
}
