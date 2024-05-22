package com.mars.component.detail.api

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.mars.component.detail.value.TestDataBean
import com.mars.component.detail.value.TestListBean
import com.mars.component.detail.value.mapping.TestDataMappingBean
import com.mars.component.detail.value.mapping.TestNestDataMappingBean
import com.rubik.annotations.route.RRoute

typealias AliasHof =  (String?) -> String?
typealias AigcChatHybridActionCreator = (
    activity: Activity,
    owner: ViewModelStoreOwner,
    lifecycleOwner: LifecycleOwner,
    hybridActionBuilder: LifecycleOwner?,
    baseView: LifecycleOwner?
) -> LifecycleOwner?

class Apis {
    // 无参无返回值
    @RRoute(path = "do-sth")
    fun doSth() {
        println(" AP DBG DETAIL  doSth  begin !!!")
    }

    // 高阶函数
    @RRoute(path = "do-sth-hof", forResult = true, version = "2.0")
    val doSthHOF: (Int, String, TestDataBean) -> Int = { a, b, c ->
        println(" AP DBG DETAIL  doSthHOF begin b:$b c:$c!!!")
        a + 102
    }

    // 属性
    @RRoute(path = "property/property", version = "1.0")
    val property: String = "vbn"


    // 返回View
    @RRoute(path = "view/get")
    fun getView(context: Context): View? {
        println(" AP DBG DETAIL  getView begin !!!")
        return View(context)
    }

    // uri传递参数
    @RRoute(path = "sth/{id}/a-{name}?code1={code1}&code2={code2}")
    fun getSth(id: String, name: String, code1: String, code2: String): String {
        println(" AP DBG DETAIL  getSth begin id:$id name:$name code1:$code1 code2:$code2 !!!")
        return "$id => $name => $code1 => $code2"
    }

    // navigationOnly
    @RRoute(path = "sth-navigation-only/{uri}", navigationOnly = true)
    fun getSthNavigationOnly(uri: String): String {
        println(" AP DBG DETAIL  getSthNavigationOnly begin uri:$uri  !!!")
        return "$uri  !"
    }

    // 变长参数
    @RRoute(path = "doSthVararg")
    fun doSthVararg(no: Int, vararg varargString: String) {
        println("  AP DBG DETAIL  doSthVararg begin strings:${varargString.getOrNull(0)}!!!")
    }


    @RRoute(path = "doSthVarargHof")
    fun doSthVarargHof(no: Int, vararg varargString: AigcChatHybridActionCreator) {
        println("  AP DBG DETAIL  doSthVarargHof begin strings:${varargString.getOrNull(0)}!!!")
    }

    // Bean参数返回值
    @RRoute(path = "doSthBean")
    fun doSthBean(a1: TestDataBean): TestListBean {
        println(" AP DBG DETAIL  doSthBean begin a1:${a1.d1}!!!")
        return TestListBean(55)
    }

//    // BeanList参数返回值
//    @RRoute(context = "com.mars.rubik-test.detail", path = "doSthBean")
//    fun doSthBeanList(a1: List<TestDataNotRValueBean>?): List<TestDataNotRValueBean>? {
//        println(" AP DBG DETAIL  doSthBean begin a1:${a1?.getOrNull(0)}!!!")
//        return listOf(TestDataNotRValueBean(66, ""))
//    }


    // uri冲突
//    @RRoute(path = "doSthUriCrash")
//    fun doSthUriCrash1(name: String, code: String) {
//    }
//
//    @RRoute(path = "doSthUriCrash")
//    fun doSthUriCrash2(name: String, code: String, version: String) {
//    }

    @RRoute(path = "do-sth-uri-crash")
    fun doSthUriCrash3(name: String, code: String) {
    }

    @RRoute(path = "do-sth-uri-crash")
    fun doSthUriCrash4(name: String, code: String, version: String) {
    }

//    @RRoute(path = "do-sth-uri-crash")
//    fun doSthUriCrash5(name: String, code: String, version: String) {
//    }

    // Bean mapping参数返回值
    @RRoute(path = "doSthMappingBean")
    fun doSthMappingBean(a1: TestDataMappingBean): TestDataMappingBean {
        println(" AP DBG DETAIL  doSthMappingBean  a1:${a1.d1}!!!")
        return TestDataMappingBean(655, "sdfa")
    }

    // Bean mapping参数返回值
    @RRoute(path = "doSthTestNestDataMappingBean")
    fun doSthTestNestDataMappingBean(bean: TestNestDataMappingBean): TestNestDataMappingBean {
        println(" AP DBG DETAIL  doSthTestNestDataMappingBean ${bean.d1?.d2} ${bean.d2?.d2} ${bean.d3?.d2} ${bean.d4} ${bean.d5}!!!")
        return TestNestDataMappingBean(
            TestDataBean(23, "11sdsd"),
            TestDataMappingBean(55, "11sdsd"),
            TestNestDataMappingBean(
                TestDataBean(4423, "22sdsd"),
                TestDataMappingBean(4455, "22sdsd"),
                null,
                listOf(TestDataBean(4423, "22sdsd")),
                listOf(TestDataMappingBean(4455, "22sdsd"))
            ),
            listOf(TestDataBean(23, "11sdsd")),
            listOf(TestDataMappingBean(55, "11sdsd"))
        )
    }

}
