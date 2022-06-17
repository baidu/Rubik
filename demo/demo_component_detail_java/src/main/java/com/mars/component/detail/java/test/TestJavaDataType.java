package com.mars.component.detail.java.test;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mars.component.detail.java.value.TestJavaBean;
import com.rubik.annotations.route.RRoute;

import java.util.List;

public class TestJavaDataType {
    private static void println(String msg) {
        System.out.println(msg);
    }

    @RRoute(path = "doTestDataType1", forResult = true)
    @NonNull
    public Context doTestDataType1(@NonNull Context data) {
        println(" AP DBG JAVA doTestDataType1 Context !!!");
        return data;
    }

    @RRoute(path = "doTestDataType1-2", forResult = true)
    @Nullable
    public Context doTestDataType1_2(@Nullable Context data) {
        println(" AP DBG JAVA doTestDataType1 Context !!!");
        return data;
    }

    @RRoute(path = "doTestDataType2", forResult = true)
    @Nullable
    public Integer doTestDataType2(@Nullable Integer data) {
        println(" AP DBG JAVA doTestDataType2 Int !!!");
        return data;
    }

    @RRoute(path = "doTestDataType2-2", forResult = true)
    public int doTestDataType2_2(int data) {
        println(" AP DBG JAVA doTestDataType2 Int !!!");
        return data;
    }

    @RRoute(path = "doTestDataType3", forResult = true)
    @Nullable
    public String doTestDataType3(@Nullable String data) {
        println(" AP DBG JAVA doTestDataType3 String !!!");
        return data;
    }

    @RRoute(path = "doTestDataType4", forResult = true)
    @Nullable
    public TestJavaBean doTestDataType4(@Nullable TestJavaBean data) {
        println(" AP DBG JAVA doTestDataType4 A1Bean!!!");
        return data;
    }

    @RRoute(path = "doTestDataType5", forResult = true)
    @Nullable
    public List<String> doTestDataType5( @Nullable List<String> data){
        println(" AP DBG JAVA doTestDataType5 List<String>!!!");
        return data;
    }

    @RRoute(path = "doTestDataType6", forResult = true)
    @Nullable
    public Long[] doTestDataType6(@Nullable Long[] data) {
        println(" AP DBG JAVA doTestDataType6 Long[] !!!");
        return data;
    }

    @RRoute(path = "doTestDataType7", forResult = true)
    @Nullable
    public long[] doTestDataType7(@Nullable long[] data) {
        println(" AP DBG JAVA doTestDataType7long[] !!!");
        return data;
    }
}
