package com.huihe.module_home.data.protocol

data class HaveKeyUserReq(
    var houseId:String?=null,
    var haveKeyUser:String?=null,
    var haveKeyTime:String?=null,
    var keyCode:String?=null,
    var keyPassword:String?=null,
    var receipt:String?=null,
    var keyImage:String?=null
)