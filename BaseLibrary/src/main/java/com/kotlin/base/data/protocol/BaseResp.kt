package com.kotlin.base.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
    能用响应对象
    @status:响应状态码
    @msg:响应文字消息
    @data:具体响应业务对象
 */
data class BaseResp<out T>(
    val status:Int,
    val msg:String,
    val data:T
)
