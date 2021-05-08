package com.huihe.module_home.data.protocol

data class SetHouseInfoReq(
    val id:String?=null,
    val hFlag:Int?=null,
    val circulation:Int?=null,
    val ownerTel:String?=null,
    val ownerName:String?=null,
    val imageUser: Int?=null,
    val imagUrl: String?=null,
    val houseCode: String?=null,
    val referUrl: String?=null
)