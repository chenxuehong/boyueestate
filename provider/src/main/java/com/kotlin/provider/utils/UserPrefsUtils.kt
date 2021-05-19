package com.kotlin.provider.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.data.protocol.District
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

    /*
      保存小区数据
   */
    fun putVillages(villages: MutableList<District>?) {
        val gson = Gson()
        var villageList = gson.toJson(villages)
        AppPrefsUtils.putString(BaseConstant.KEY_SP_VILLAGES, villageList)
    }

    /*
      获取小区数据
   */
    fun gettVillages():MutableList<District>? {
        val gson = Gson()
        var json = AppPrefsUtils.getString(BaseConstant.KEY_SP_VILLAGES) ?: ""
        var typeToken = object : TypeToken<MutableList<District>?>() {}
        var villageList = gson.fromJson<MutableList<District>?>(json, typeToken.type)
        return villageList
    }

}
