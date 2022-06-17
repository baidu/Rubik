package com.mars.component.detail.java.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mars.component.detail.java.R;
import com.rubik.annotations.route.RProperty;
import com.rubik.annotations.route.page.RPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@RPage(path = "activity/java_page2/{key_str1}/{key_str2}/{key_str3}")
public class SecondActivity extends AppCompatActivity {

    @Nullable
    @RProperty(name = "key_str1")
    private String str1;

    @Nullable
    @RProperty(name = "key_str2")
    private String str2;

    @Nullable
    @RProperty(name = "key_str3")
    private int str3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d1);
        str1 = getIntent().getStringExtra("key_str1");
        str2 = getIntent().getStringExtra("key_str2");
        String str3_3 = getIntent().getExtras().get("key_str3") + "";

        ((TextView) findViewById(R.id.text_hello)).setText("Hello D2 !!\n" +
                str1 + "\n" +
                str2 + "\n" +
                str3_3 + "\n");

        Intent intent = new Intent();
        intent.putExtra("data", "data from D2");
        setResult(200, intent);

    }
}
