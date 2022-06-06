package com.huihe.boyueentities.protocol.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusRep(
    var dataKey:Int?,
    var dataType:String?,
    var dataValue:String?,
    var description:String?,
    var id:String?,
    var sorts:String?
):ISearchResult,Parcelable