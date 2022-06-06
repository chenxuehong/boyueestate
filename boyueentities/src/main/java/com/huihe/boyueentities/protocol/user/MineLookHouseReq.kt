package com.huihe.boyueentities.protocol.user


data class MineLookHouseReq(
    var takeLookId:String?,
    var houseCode:String?=null,
    var accompanyUserId:String?=null,
    var accompanyUser:String?=null,
    var follow:String?=null
)