package com.mars.component.detail.api

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mars.component.detail.value.TestDataBean
import com.mars.component.detail.value.TestListBean
import com.rubik.annotations.route.RRoute

class ApisWithLiveData {
    // 传递LiveData
    @RRoute(path = "live-data/get")
    fun getSthLiveData(): LiveData<String> {
        return MutableLiveData<String>().apply {
            this.postValue("i am from A")
            Handler().postDelayed({
                this.value = "i am also from A"
            }, 1000L)
        }
    }

    @RRoute(path = "live-data-bean/get")
    fun getSthLiveDataBean(): LiveData<TestDataBean> {
        return MutableLiveData<TestDataBean>().apply {
            this.value = TestDataBean(122334, "A1q2w3e")
        }
    }

    @RRoute(path = "live-data-bean-list/get")
    fun getSthLiveDataBeanList(): LiveData<List<TestListBean?>> {
        return MutableLiveData<List<TestListBean?>>().apply {
            this.value = listOf(
                TestListBean(45566778, listOf("A2QQQ", "A2WWW", "A2EEE")),
                TestListBean(67788990, listOf("A2AAA", "A2SSS", "A2DDD"))
            )
        }
    }
}