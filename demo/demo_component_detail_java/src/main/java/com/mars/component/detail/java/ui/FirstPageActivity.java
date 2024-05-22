package com.mars.component.detail.java.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.mars.component.detail.java.R;
import com.mars.component.detail.java.value.TestJavaBean;
import com.rubik.annotations.route.RProperty;
import com.rubik.annotations.route.page.RPage;
import com.rubik.router.Router;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.mars.util_library.TestClassB;

@RPage(path = "activity/java_page1")
public class FirstPageActivity extends AppCompatActivity {

    @Nullable
    @RProperty(name = "key_str4", extra = "serializable")
    private List<String> str4;

    @Nullable
    @RProperty(name = "key_str5", extra = "value")
    private List<TestJavaBean> str5;

    @Nullable
    @RProperty(name = "key_str6", extra = "value")
    private TestJavaBean[] str6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d1);
        str4 = (ArrayList<String>) getIntent().getExtras().get("key_str4");
        str5 = Router.valueProperty(getIntent(), "key_str5", new TypeToken<List<TestJavaBean>>() { } . getType());
        str6 = Router.valueProperty(getIntent(), "key_str6", new TypeToken<TestJavaBean[]>() { } . getType());

        ((TextView) findViewById(R.id.text_hello)).setText("Hello D1 !!\n" + str4.get(0) + "\n" + str5.get(0).d1 + "\n" + str6[0].d1 + "\n");

//        ((TextView) findViewById(R.id.text_modules)).setText("A : "+ new TestClassA().getTestTag() +"\n" + "B : "+ new TestClassB().getTestTag() + "\n" );
    }
}
