package com.huihe.customercenter.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerMobileRep(
    var mobile:String?
):Parcelable
