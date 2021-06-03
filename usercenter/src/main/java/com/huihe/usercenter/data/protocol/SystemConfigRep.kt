package com.huihe.usercenter.data.protocol

data class SystemConfigRep(
    val list:MutableList<SystemConfigBean>,
    val totalCount:Long
){
    data class SystemConfigBean(
        var configKey:String?,
        var configValue:String?,
        var isPublish:Int,
        var remark:String?
    )
    data class BannerBean(
        var modile_coveImage:String?
    )
}
