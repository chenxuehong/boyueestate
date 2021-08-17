package com.huihe.usercenter.data.protocol

data class SignReq(
    var id:String?=null,
    var imgUrl:String?=null,
    var positionLatitude:Double?=null,
    var positionLongitude:Double??=null,
    var signInStatus:Int?=null
)
