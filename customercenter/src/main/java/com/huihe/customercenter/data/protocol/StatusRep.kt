package com.huihe.customercenter.data.protocol

import android.os.Parcelable
import com.huihe.customercenter.ui.widget.ISearchResult
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