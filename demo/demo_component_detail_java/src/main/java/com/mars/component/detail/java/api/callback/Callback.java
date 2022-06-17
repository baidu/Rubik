package com.mars.component.detail.java.api.callback;

import androidx.annotation.Nullable;

import com.rubik.annotations.route.RResult;

public class Callback {
    public void onCallNone(String v1, int v2) {
        // nothing
    }

    @RResult
    public void onCall(@Nullable String v1, int v2) {
        // nothing
    }
}
