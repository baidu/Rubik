package com.mars.component.detail.java.api;

import com.rubik.annotations.route.RRoute;

public class Apis extends BaseApis {

    // 无参无返回值
    @RRoute(path = "doSth")
    public void doSth() {
        println(" AP DBG JAVA doSth  begin !!!");
    }

    // 属性
    @RRoute(path = "property/property", version = "1.0")
    public String property = "vbn";



    // static方法
    @RRoute(path = "doSthStatic")
    public static String doSthCompanion(int i, String j, Long k) {
        println(" AP DBG JAVA doSthCompanion begin !!! i:" + i + " j:" + j + " k:" + k);
        return "result = i:" + i + " j:" + j + " k:" + k;
    }

    /**
     * static属性
     */
    @RRoute(path = "property/static")
    public static String propertyCompanion = "bnm3";

}

