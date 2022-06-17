package com.mars.component.home.ui.java;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mars.component.home.R;
import com.mars.component.home.test.java.TestJavaInvokeBuilderTask;
import com.mars.component.home.test.java.TestJavaInvokeFunctionTask;
import com.mars.component.home.test.java.TestJavaInvokeJavaFunctionTask;
import com.rubik.router.Router;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext;
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestCompanionBean;
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean;
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaContext;
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean;

public class JavaHomeActivity extends AppCompatActivity{

   private TextView textHello;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_java);

      textHello = findViewById(R.id.text_hello);

      findViewById(R.id.button_builder_j_2_k).setOnClickListener(view -> {
         textHello.setText("");
         new TestJavaInvokeBuilderTask().invoke(getApplicationContext(), (result) -> {
            System.out.println(result);
            textHello.setText(textHello.getText() + "\n" + result);
            return null;
         });
      });
      findViewById(R.id.button_context_j_2_k).setOnClickListener(view -> {
         textHello.setText("");
         new TestJavaInvokeFunctionTask().invoke(getApplicationContext(), (result) -> {
            System.out.println(result);
            textHello.setText(textHello.getText() + "\n" + result);
            return null;
         });
      });
      findViewById(R.id.button_context_j_2_j).setOnClickListener(view -> {
         textHello.setText("");
         new TestJavaInvokeJavaFunctionTask().invoke(getApplicationContext(), (result) -> {
            System.out.println(result);
            textHello.setText(textHello.getText() + "\n" + result);
            return null;
         });
      });

      findViewById(R.id.button_builder_start_j_2_j).setOnClickListener(view -> {
         List<String> strs = new ArrayList<>();
         strs.add("i am from b java list");
         List<TestJavaBean> cPaLi = new ArrayList<>();
         cPaLi.add(new TestJavaBean(405, "405405"));

         Router.builder(JavaHomeActivity.this).uri(DetailJavaContext.Uris.Activity.JAVA_PAGE1)
                 .launchFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                 .withSerializable("key_str4", strs)
                 .with("key_str5", cPaLi)
                 .with("key_str6", new TestJavaBean[] {new TestJavaBean(404, "404404")})
                 .build().route();
      });

      findViewById(R.id.button_context_start_j_2_j).setOnClickListener(view -> {
         List<String> strs = new ArrayList<>();
         strs.add("i am from b java list");
         List<TestJavaBean> cPaLi = new ArrayList<>();
         cPaLi.add(new TestJavaBean(405, "405405"));

         DetailJavaContext.Activity.javaPage1(
                 JavaHomeActivity.this,
                 strs,
                 cPaLi,
                 new TestJavaBean[] {new TestJavaBean(404, "404404")}, null, null);
      });

      findViewById(R.id.button_builder_start_j_2_k).setOnClickListener(view -> {
         List<String> strs = new ArrayList<>();
         strs.add("i am from b java list");
         List<Integer> ints = new ArrayList<>();
         ints.add(456);
         List<TestCompanionBean> cPaLi = new ArrayList<>();
         cPaLi.add(new TestCompanionBean(405, "405405"));

         Router.builder(JavaHomeActivity.this).uri(DetailContext.Uris.Activity.PAGE1)
                 .launchFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                 .with("key_3_int", 55)
                 .with("key_b_str", "i am from b java")
                 .withSerializable("key_4_strs", strs)
                 .with("key_a_ints", ints)
                 .with("key_5_bean", new TestDataBean(205, "205"))
                 .with("key_1_pa", new TestCompanionBean(403, "403403"))
                 .with("key_2_pa_ar", new TestCompanionBean[] {new TestCompanionBean(404, "404404")})
                 .with("key_c_pa_li", cPaLi)
                 .build().route();
      });

      findViewById(R.id.button_context_start_j_2_k).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            List<String> strs = new ArrayList<>();
            strs.add("i am from b java list");
            List<Integer> ints = new ArrayList<>();
            ints.add(456);
            List<TestCompanionBean> cPaLi = new ArrayList<>();
            cPaLi.add(new TestCompanionBean(405, "405405"));

            DetailContext.Activity.page1(JavaHomeActivity.this,
                            new TestCompanionBean(7303, "303303"),
                            new TestCompanionBean[] {new TestCompanionBean(404, "404404")},
                            733,
                            strs,
                            new TestDataBean(205, "205"), ints, cPaLi,
                            "i am from b java",
                            null,
                            null);
         }
      });


   }
}
