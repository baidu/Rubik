package com.mars.component.detail.value

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rubik.annotations.route.RValue
import kotlinx.android.parcel.Parcelize

@RValue
@Parcelize
data class TestParcelizeBean(
    val d1: Int?,
    val d2: String?
) : Parcelable
//{
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString()
//    )
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(dest: Parcel?, flags: Int) {
//        dest?.writeInt(d1 ?: -1)
//        dest?.writeString(d2)
//    }
//
//    companion object CREATOR : Parcelable.Creator<TestParcelizeBean> {
//        override fun createFromParcel(parcel: Parcel): TestParcelizeBean {
//            return TestParcelizeBean(parcel)
//        }
//
//        override fun newArray(size: Int): Array<TestParcelizeBean?> {
//            return arrayOfNulls(size)
//        }
//    }
//}