package com.mars.component.home.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mars.component.home.R
import com.mars.component.home.test.kotlin.TestKotlinInvokeDSLTask
import com.mars.component.home.test.kotlin.TestKotlinInvokeFunctionTask
import com.mars.component.home.test.kotlin.TestKotlinInvokeJavaFunctionTask
import com.rubik.router.navigate
import com.rubik.router.result
import kotlinx.android.synthetic.main.activity_kotlin.*
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestCompanionBean
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestListBean
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaContext
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment(), "").commit()

        button_navigation_k_2_k.setOnClickListener {
            text_hello.text = ""
            TestKotlinInvokeDSLTask().invoke(applicationContext) { result ->
                println(result)
                text_hello.text = "${text_hello.text}\n$result"
            }
        }

        button_context_k_2_k.setOnClickListener {
            text_hello.text = ""
            TestKotlinInvokeFunctionTask().invoke(applicationContext) { result ->
                println(result)
                text_hello.text = "${text_hello.text}\n$result"
            }
        }

        button_context_k_2_j.setOnClickListener{
            text_hello.text = ""
            TestKotlinInvokeJavaFunctionTask().invoke(applicationContext) { result ->
                println(result)
                text_hello.text = "${text_hello.text}\n$result"
            }
        }


        button_navigation_livedata_k_2_k.setOnClickListener {
            text_hello.text = ""

            navigate {
                uri = DetailContext.Uris.LIVE_DATA_GET
                result<LiveData<String>> { data ->
                    data.observe(this@HomeActivity, Observer {
                        text_hello.text = "${text_hello.text}\nobs:$it"
                    })
                }
            }

            navigate {
                uri = DetailContext.Uris.LIVE_DATA_BEAN_GET
                result<LiveData<TestDataBean>> { data ->
                    data.observe(this@HomeActivity, Observer {
                        text_hello.text = "${text_hello.text}\nobs:${it.d2}"
                        text_hello.text = "${text_hello.text}\nget:${data.value?.d2}"
                    })
                }
            }

            navigate {
                uri = DetailContext.Uris.LIVE_DATA_BEAN_LIST_GET
                result<LiveData<List<TestListBean>>> { data ->
                    data.observe(this@HomeActivity, Observer {
                        text_hello.text = "${text_hello.text}\nobs:${it.toLog()}"
                        text_hello.text = "${text_hello.text}\nget:${data.value?.toLog()}"
                    })
                }
            }
        }

        button_context_livedata_k_2_k.setOnClickListener {
            text_hello.text = ""

            DetailContext.liveDataGet { data ->
                data.observe(this@HomeActivity, Observer {
                    text_hello.text = "${text_hello.text}\nobs:$it"
                })
            }

            DetailContext.liveDataBeanGet { data ->
                data.observe(this@HomeActivity, Observer {
                    text_hello.text = "${text_hello.text}\nobs:${it.d1}"
                    text_hello.text = "${text_hello.text}\nget:${data.value?.d2}"
                })
            }

            DetailContext.liveDataBeanListGet { data ->
                data.observe(this@HomeActivity, Observer {
                    text_hello.text = "${text_hello.text}\nobs:${it.toLog()}"
                    text_hello.text = "${text_hello.text}\nget:${data.value?.toLog()}"
                })
            }

//            MutableLiveData<A1Bean>().apply {
//                this.observe(this@B1Activity, Observer {
//                    text_hello.text = "${text_hello.text}\nobs3:${it.b1}"
//                    text_hello.text = "${text_hello.text}\nobs3:${it.b2}"
//                })
//                DetailContext.sthWithLiveData(this)
//            }
        }

        button_navigation_start_k_2_k.setOnClickListener {
            navigate {
                uri = DetailContext.Uris.Activity.PAGE1
                query {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    "key_3_int" with 33.toInt()
                    "key_b_str" with "i am from b"
                    "key_4_strs" withSerializable  ArrayList<String>().apply { add(("i am from b list")) }
                    "key_a_ints" with ArrayList<Int>().apply { add(123) }
                    "key_5_bean" with TestDataBean(203, "203")

                    "key_1_pa" with TestCompanionBean(303, "303303")
                    "key_2_pa_ar" with arrayOf(TestCompanionBean(304, "304304"))
                    "key_c_pa_li" with ArrayList<TestCompanionBean>().apply { add(TestCompanionBean(305, "305305")) }

//                    "key_pa_p"  withParcelable TmpA3Bean(1303, "303303")
//                    "key_pa_ar_p" withParcelable  arrayOf(TmpA3Bean(1304, "304304"))
//                    "key_pa_li_p" withParcelable  ArrayList<TmpA3Bean>().apply { add(TmpA3Bean(1305, "305305")) }
//
//                    "key_ser" withSerializable TmpA4Bean(2303, "23032303")
                }
            }
        }

        button_context_start_k_2_k.setOnClickListener {
            DetailContext.Activity.page1(
                this,
                TestCompanionBean(5303, "303303"),
                arrayOf(TestCompanionBean(5304, "304304")),
                533,
                ArrayList<String>().apply { add(("i am from b DetailContext list")) },
                TestDataBean(5203, "203"),
                ArrayList<Int>().apply { add(5123) },
                ArrayList<TestCompanionBean>().apply { add(TestCompanionBean(5305, "305305")) },
                "i am from b DetailContext"
            )
        }

        button_navigation_start_for_result_k_2_k.setOnClickListener {
            navigate {
                uri = DetailContext.Uris.Activity.Page2.KeyStr1.KeyStr2.KEY_STR3("fff", "ggg", "hhh")
                query {
                    requestCode = 101
                }
            }
        }

        button_context_start_for_result_k_2_k.setOnClickListener {
            DetailContext.Activity.Page2.KeyStr1.KeyStr2.keyStr3(
                this,
                "ccc",
                "vvv",
                "bbb",
                null,
                102
            )
        }

        button_navigation_start_k_2_j.setOnClickListener {
            navigate {
                uri = DetailJavaContext.Uris.Activity.JAVA_PAGE1
                query {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    "key_str4" withSerializable  ArrayList<String>().apply { add(("i am from b list")) }
                    "key_str5" with  ArrayList<TestJavaBean>().apply { add(TestJavaBean(505, "505505")) }
                    "key_str6" with arrayOf(TestJavaBean(504, "504504"))
                }
            }
        }

        button_context_start_k_2_j.setOnClickListener {
            DetailJavaContext.Activity.javaPage1(
                this,
                ArrayList<String>().apply { add(("i am from b list")) },
                ArrayList<TestJavaBean>().apply { add(TestJavaBean(505, "505505")) },
                arrayOf(TestJavaBean(504, "504504"))
            )
        }

        button_navigation_start_for_result_k_2_j.setOnClickListener {
            navigate {
                uri = DetailJavaContext.Uris.Activity.JavaPage2.KeyStr1.KeyStr2.KEY_STR3("rrr", "ttt", "90")
                query {
                    requestCode = 103
                }
            }
        }

        button_context_start_for_result_k_2_j.setOnClickListener {
            DetailJavaContext.Activity.JavaPage2.KeyStr1.KeyStr2.keyStr3(
                this,
                "ccc",
                "vvv",
                90,
                null,
                104
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        text_hello.text =
            "onActivityResult requestCode:$requestCode resultCode:$resultCode data:${data?.getStringExtra("data")}"
    }

    private fun List<TestListBean>.toLog() =
        fold("") { str, bean ->
            "$str/${bean.d2?.fold("") { beanStr, value ->
                "$beanStr|$value"
            }}"
        }

    override fun onDestroy() {
        super.onDestroy()
    }

}
