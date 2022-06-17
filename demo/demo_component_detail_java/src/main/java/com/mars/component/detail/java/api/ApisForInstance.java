package com.mars.component.detail.java.api;

import com.mars.component.detail.java.value.TestJavaBean;
import com.rubik.annotations.route.RRoute;
import com.rubik.annotations.route.instance.RRouteInstance;

public class ApisForInstance extends BaseApis {

    private final String value;

    @RRouteInstance(provideForPath = "do-sth-bean-provide-instance-by-func")
    public ApisForInstance(String value) {
        this.value = value;
    }

    // Bean参数返回值
    @RRoute(path = "do-sth-bean-provide-instance-by-func")
    public TestJavaBean doSthBean(TestJavaBean d1) {
        println(" AP DBG doSthBean value " + value + " begin d1:" + d1.d1 + "!!!");
        return new TestJavaBean(55, "dd");
    }
}