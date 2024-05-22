package com.mars.component.detail.api

import android.content.Context
import androidx.lifecycle.LiveData
import com.rubik.annotations.route.RResult
import com.rubik.annotations.route.RRoute

class ApisBadCase {

    @RRoute(path = "getFilesMeta")
    fun getFilesMeta(context: Context?, path: String?, @RResult callback: GetFileMetaCallback) {
    }

    @RRoute(path = "enterprise/business/allocateTicket")
    fun allocateTicket(context: Context, shareId: Long, count: Int, packetType: Int): LiveData<List<String>>? {
        return null
    }

//    @RRoute(path = "get/TestClassB")
//    fun allocateTicket( count: Int): LiveData<TestClassB>? {
//        return null
//    }

//    @RFunction(path = "shareObject/createGroupLinkShareController")
//    fun createGroupLinkShareController(
//        activity: Activity?,
//        shareOption: String?,
//        image: ByteArray?,
//        handler: Handler?,
//        type: String?
//    ): GetFileMetaCallback? {
//        return null
//    }
//
//    @RFunction(path = "shareObject/createGroupLinkShareController")
//    fun createGroupLinkShareController(
//        activity: Activity?,
//        shareOption: String?,
//        handler: Handler?,
//        type: String?,
//        @RResult shareHelper: GetFileMetaCallback?
//    ): GetFileMetaCallback? {
//        return null
//    }
}

interface GetFileMetaCallback {
    /**
     * 文件恢复结果通知
     */
    @RResult
    fun onResult(fsid: Long, path: String?)
}