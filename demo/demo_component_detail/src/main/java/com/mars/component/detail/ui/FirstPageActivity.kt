package com.mars.component.detail.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mars.component.detail.R
import com.mars.component.detail.value.TestCompanionBean
import com.mars.component.detail.value.TestDataBean
import com.rubik.annotations.route.RProperty
import com.rubik.annotations.route.page.RPage
import com.rubik.router.property
import kotlinx.android.synthetic.main.activity_a1.*

@RPage(path = "activity/page1")
@RPage(path = "activity/page1_from_home")
@RPage(path = "activity/page1_from_detail")
class FirstPageActivity : AppCompatActivity() {

    @RProperty(name = "key_int_for_home", forPath = ["activity/page1_from_home"])
    private var intForA1b: Int? = null
        get() = property("key_int_for_home")

    @RProperty(name = "key_int_for_detail", forPath =  ["activity/page1_from_detail"])
    private var intForA1c: Int? = null
        get() = property("key_int_for_detail")

    @RProperty(name = "key_int_for_all", forPath = ["activity/page1_from_home", "activity/page1_from_detail"])
    private var intForAll: Int? = null
        get() = property("key_int_for_all")

    @get:RProperty(name = "key_3_int")
    private val inta: Int
        get() = property("key_3_int") ?: -1

    @get:RProperty(name = "uri")
    private val str: String?
        get() = property("uri")

    @get:RProperty(name = "key_4_strs", extra = "serializable")
    private val strs: List<String>?
        get() = property("key_4_strs")

    @get:RProperty(name = "key_a_ints")
    private val ints: List<Int>?
        get() = property("key_a_ints")

    private var aints: ArrayList<Int>? = null
        get() = property("key_a_ints")

    @RProperty(name = "key_5_bean")
    private var bean: TestDataBean? = null
        get() = property("key_5_bean")

    @RProperty(name = "key_1_pa")
    private var pa: TestCompanionBean? = null
        get() = property("key_1_pa")

    @RProperty(name = "key_2_pa_ar")
    private var paAr: Array<TestCompanionBean>? = null
        get() = property("key_2_pa_ar")

    @RProperty(name = "key_c_pa_li")
    private var paLi: List<TestCompanionBean>? = null
        get() = property("key_c_pa_li")

//    @RProperty(name = "key_pa_p")
//    private var paP: A3Bean? = null
//        get() = parcelableProperties("key_pa_p")
//
//    @RProperty(name = "key_pa_ar_p")
//    private var paArP: Array<A3Bean>? = null
//        get() = parcelableArrayProperties("key_pa_ar_p")
//
//    @RProperty(name = "key_pa_li_p")
//    private var paLiP: List<A3Bean>? = null
//        get() = parcelableListProperties("key_pa_li_p")
//
//    @RProperty(name = "key_ser")
//    private var ser: A4Bean? = null
//        get() = serializablProperties("key_ser")


    override fun onCreate(savedInstanceState: Bundle?) {
        println(" CT DBG TEST FirstPageActivity onCreate   !!!")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a1)
        val msg = "Hello FirstPageActivity !!\n" +
                    "$inta\n" +
                    "$str\n" +
                    "$strs\n" +
                    "$ints\n" +
                    "$aints\n" +
                    "${bean?.d1}\n" +
                    "${pa?.d1}\n" +
                    "${paAr?.get(0)?.d1}\n" +
                    "${paLi?.get(0)?.d1}\n"
//                    "$ser\n"
//                    "${paP?.b1}\n" +
//                    "${paArP?.get(0)?.b1}\n" +
//                    "${paLiP?.get(0)?.b1}\n"
        text_hello.text = msg


    }
}

//@RRoute(path = "notActivity2")
//class NotActivity(val b :TestClassB){
//}
