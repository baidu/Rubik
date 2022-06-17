package com.mars.component.detail.java.value;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.rubik.annotations.route.RValue;

@RValue
public class TestJavaBean {
    public TestJavaBean(int d1, String d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    @SerializedName("ddd111")
    public int d1;
    @SerializedName("ddd222")
    @Nullable
    private String d2;
}
