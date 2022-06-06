package com.huihe.boyueentities.protocol.user

data class SignReq(
    var id:String?=null,
    var imgUrl:String?=null,
    var positionLatitude:Double?=null,
    var positionLongitude:Double??=null,
    var signInStatus:Int?=null
)
