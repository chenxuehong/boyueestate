package com.kotlin.base.common

/*
    基础常量
 */
class BaseConstant{
    companion object {
        const val project_name = "boyueEstate"
        //七牛服务地址
//        const val IMAGE_SERVER_ADDRESS = "http://osea2fxp7.bkt.clouddn.com/"
        // 测试服务器地址
        const val SERVER_ADDRESS_DEBUG = "http://101.133.160.181/api/v1/"
        // 正式服务器地址
        const val SERVER_ADDRESS = "http://101.133.160.181/api/v1/"
//        const val SERVER_ADDRESS = "http://106.14.197.40/api/v1/"
        // 分享
        const val HouseDetail_BASE_URL = "http://billion.housevip.cn/#/house/"
        const val VERSION_INFO_URL = "http://api.bq04.com/apps/latest/"
        // ip
        const val ip = "2"

        //SP表名
        const val TABLE_PREFS = "estate"
        //Token Key
        const val KEY_SP_TOKEN = "H-User-Token"
        const val KEY_SP_USER_INFO = "userInfo"
        const val KEY_SP_REGISTRATIONID = "RegistrationId"
        const val KEY_ISSELECT = "isSelect"
        const val KEY_STATUS = "status"
        const val KEY_STATUS_DEFAULT = 0
        const val KEY_STATUS_MAP = 1
        const val KEY_ID = "id"

        // 小区列表 key
        const val KEY_SP_VILLAGES = "Villages"
    }
}
