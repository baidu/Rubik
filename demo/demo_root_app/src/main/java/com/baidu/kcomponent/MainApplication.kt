package com.baidu.kcomponent

import android.app.Application
import android.util.Log
import com.rubik.Rubik
import rubik.generate.root.init

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Rubik.logger {
            bigLogEnable = false
            d = { log -> Log.d("R DEBUG", log) }
            e = { log, _ ->
                Log.e("R ERROR", "$log ")
//            exception?.let {
//                throw it
//            }
            }
        }
        Rubik.init()
    }
}