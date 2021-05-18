package com.kotlin.provider.utils

import com.google.gson.Gson
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.data.protocol.IMUserInfo


object UserPrefsUtils {
    /*
       退出登录时，传入null,清空存储
    */
    fun putUserInfo(userInfo: IMUserInfo?) {
        val gson = Gson()
        var userInfoJsonStr = gson.toJson(userInfo)
        AppPrefsUtils.putString(BaseConstant.KEY_SP_USER_INFO, userInfoJsonStr)
    }

    /*
       获取用户信息
    */
    fun getUserInfo(): IMUserInfo? {
        val gson = Gson()
        var userInfoJsonStr = AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_INFO)
        return gson.fromJson<IMUserInfo>(userInfoJsonStr, IMUserInfo::class.java)
    }
}
