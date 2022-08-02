package com.baidu.kcomponent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mars.component.home.test.kotlin.TestBigData
import com.mars.component.home.test.kotlin.invokeDataType
import com.mars.component.home.ui.HomeActivity
import com.mars.component.home.ui.java.JavaHomeActivity
import com.rubik.context.LifeCycleEvent
import com.rubik.router.doEvent
import com.rubik.router.doEventWithContext
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        button_start_home.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        button_start_java_home.setOnClickListener {
            startActivity(Intent(this, JavaHomeActivity::class.java))
        }

        button_test_life.setOnClickListener {
            applicationContext.doEventWithContext(LifeCycleEvent.INIT, "initapp", 11, 22)
            doEvent(LifeCycleEvent.DESTROY, applicationContext, "desapp", 33, 44)

            doEvent("MY_INIT", true, "initmyapp", "a", "b")
            doEvent("MY_DESTROY", false, "desapp", "c", "d")
        }

        button_test_data_type.setOnClickListener {
             invokeDataType(applicationContext)
        }

        button_test_big_data.setOnClickListener {
            text_info.text = ""
            TestBigData().invoke(applicationContext) { info ->
                text_info.text = text_info.text.toString() + "\n" + info
            }
        }
    }
}
