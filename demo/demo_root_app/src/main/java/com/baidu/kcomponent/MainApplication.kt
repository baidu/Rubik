package com.baidu.kcomponent

import android.app.Application
import android.util.Log
import com.rubik.Rubik
import rubik.generate.shell.init

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Rubik.logger {
            bigLogEnable = false
            d = { log -> Log.d("R DEBUG", log) }
            e = { log, e ->
                Log.e("R ERROR", "$log ")
//                e?.let {
//                    throw it
//                }
            }
        }
        Rubik.init()
        Rubik.Properties.autoParcel = true
    }
}