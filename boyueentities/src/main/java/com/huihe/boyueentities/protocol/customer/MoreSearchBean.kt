package com.huihe.boyueentities.protocol.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoreSearchBean(
    var content:String,
    var isTitle:Boolean,
    var dataType:String?,
    var dataKey:Int?,
    var description:String
):Parcelable