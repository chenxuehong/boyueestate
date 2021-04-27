package com.huihe.usercenter.utils

import com.google.gson.Gson
import com.huihe.usercenter.data.protocol.UserInfo
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils


object UserPrefsUtils {
    /*
       退出登录时，传入null,清空存储
    */
    fun putUserInfo(userInfo: UserInfo?) {
        val gson = Gson()
        var userInfoJsonStr = gson.toJson(userInfo)
        AppPrefsUtils.putString(BaseConstant.KEY_SP_USER_INFO, userInfoJsonStr)
    }

    /*
       获取用户信息
    */
    fun getUserInfo(): UserInfo? {
        val gson = Gson()
        var userInfoJsonStr = AppPrefsUtils.getString(BaseConstant.KEY_SP_USER_INFO)
        return gson.fromJson<UserInfo>(userInfoJsonStr, UserInfo::class.java)
    }
}
