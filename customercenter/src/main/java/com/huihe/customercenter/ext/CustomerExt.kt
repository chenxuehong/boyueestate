package com.huihe.customercenter.ext

import android.content.Context
import android.widget.Button
import com.huihe.customercenter.R

fun Button.enableList(
    mContext: Context,
    checkList: MutableList<*>
) {
    var btnMoreReset = this
    if (checkList?.size > 0) {
        btnMoreReset.isEnabled = true
        btnMoreReset.setTextColor(mContext.resources.getColor(R.color.color_333333))
    } else {
        btnMoreReset.setTextColor(mContext.resources.getColor(R.color.color_999999))
        btnMoreReset.isEnabled = false
    }
}









