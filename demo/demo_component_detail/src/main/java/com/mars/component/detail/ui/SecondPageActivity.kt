package com.mars.component.detail.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mars.component.detail.R
import com.rubik.annotations.route.RProperty
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.page.RPage
import com.rubik.router.property
import kotlinx.android.synthetic.main.activity_a1.*

@RPage(path = "activity/page2/{key_str1}/{key_str2}/{key_str3}")
class SecondPageActivity : AppCompatActivity() {

    @RProperty(name = "key_str1")
    private var str1: String? = null
        get() =  property("key_str1")

    @RProperty(name = "key_str2")
    private var str2: String? = null
        get() =  property("key_str2")

    @RProperty(name = "key_str3")
    private var str3: String? = null
        get() =  property("key_str3")

    override fun onCreate(savedInstanceState: Bundle?) {
        println(" CT DBG TEST SecondPageActivity  onCreate  !!!")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a1)

        val msg = "Hello SecondPageActivity !!\n" +
                "$str1\n" +
                "$str2\n" +
                "$str3\n"
        text_hello.text = msg
        setResult(200, Intent().apply { putExtra("data", "data from A2") })

    }
}
