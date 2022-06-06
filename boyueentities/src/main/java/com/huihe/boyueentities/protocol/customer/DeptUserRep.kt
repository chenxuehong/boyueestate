package com.huihe.boyueentities.protocol.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeptUserRep(
    val children:MutableList<DeptUser>?,
    val label:String?,
    val value:String?
):Parcelable{
    @Parcelize
    data class DeptUser(
        val label:String?,
        val uid:String?,
        val value:String?
    ):Parcelable
}