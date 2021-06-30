package com.kotlin.base.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.data.protocol.Configs
import com.kotlin.base.ext.readJsonStrFromAssets

/*
    SP工具类
 */
object AppPrefsUtils {
    private var sp: SharedPreferences = BaseApplication.context.getSharedPreferences(BaseConstant.TABLE_PREFS, Context.MODE_PRIVATE)
    private var ed: Editor

    init {
        ed = sp.edit()
    }

    /*
        Boolean数据
     */
    fun putBoolean(key: String, value: Boolean) {
        ed.putBoolean(key, value)
        ed.commit()
    }

    /*
        默认 false
     */
    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    /*
        String数据
     */
    fun putString(key: String, value: String) {
        ed.putString(key, value)
        ed.commit()
    }

    /*
        默认 ""
     */
    fun getString(key: String): String {
        return sp?.getString(key, "")
    }

    /*
        Int数据
     */
    fun putInt(key: String, value: Int) {
        ed.putInt(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    /*
        Long数据
     */
    fun putLong(key: String, value: Long) {
        ed.putLong(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getLong(key: String): Long {
        return sp.getLong(key, 0)
    }

    /*
        Set数据
     */
    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key).toMutableSet()
        localSet.addAll(set)
        ed.putStringSet(key, localSet)
        ed.commit()
    }

    /*
        默认空set
     */
    fun getStringSet(key: String): Set<String> {
        val set = setOf<String>()
        return sp?.getStringSet(key, set)
    }

    /*
        删除key数据
     */
    fun remove(key: String) {
        ed.remove(key)
        ed.commit()
    }

    /**
     * @desc 从asset读取配置文件，转成对应数据模型
     */
    fun getConfigList():MutableList<Configs.ConfigBean>{
        val jsonStr: String = readJsonStrFromAssets("config_constant.json", BaseApplication.context)
        var configsBean = Gson().fromJson<Configs>(jsonStr, Configs::class.java)
        return configsBean.configs
    }

    fun setConfigInfo(companyCode:String):Configs.ConfigBean?{
        var configList = getConfigList()
        var configInfo:Configs.ConfigBean?=null
        configList.forEach {item->
            if (item.CompanyCode.equals(companyCode)){
                configInfo = item
            }
        }
        if (configInfo!=null){
            AppPrefsUtils.putString(BaseConstant.KEY_CONFIG_INFO, Gson().toJson(configInfo))
        }
        return configInfo
    }

    fun getConfigInfo():Configs.ConfigBean?{
        var configInfoStr = AppPrefsUtils.getString(BaseConstant.KEY_CONFIG_INFO) ?: ""
        return Gson().fromJson<Configs.ConfigBean>(configInfoStr,Configs.ConfigBean::class.java)
    }
}
