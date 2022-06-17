package com.mars.component.home.ui;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mars.component.home.R
import com.rubik.router.navigate
import kotlinx.android.synthetic.main.fragment_b1.*
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaContext

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_b1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_navigation_fragment_start_for_result_k_2_k.setOnClickListener {
            navigate {
                uri = DetailContext.Uris.Activity.Page2.KeyStr1.KeyStr2.KEY_STR3(
                    "fff3",
                    "ggg3",
                    "hhh3"
                )
                query {
                    requestCode = 301
                }
            }
        }

        button_context_fragment_start_for_result_k_2_k.setOnClickListener {
            DetailContext.Activity.Page2.KeyStr1.KeyStr2.keyStr3(
                this,
                "ccc3",
                "vvv3",
                "bbb3",
                null,
                302
            )
        }

        button_navigation_fragment_start_for_result_k_2_j.setOnClickListener {
            navigate {
                uri = DetailJavaContext.Uris.Activity.JavaPage2.KeyStr1.KeyStr2.KEY_STR3(
                    "ccc5",
                    "vvv5",
                    88.toString()
                )
                query {
                    requestCode = 303
                }
            }
        }

        button_context_fragment_start_for_result_k_2_j.setOnClickListener {
            DetailJavaContext.Activity.JavaPage2.KeyStr1.KeyStr2.keyStr3(
                this,
                "ccc5",
                "vvv5",
                88,
                null,
                304
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        text_hello_fragment.text =
            "onFragmentResult requestCode:$requestCode resultCode:$resultCode data:${data?.getStringExtra("data")}"
    }
}
