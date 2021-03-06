package com.huihe.boyueentities.protocol.home

data class EntrustUserReq(
    var houseId:String?=null,
    var entrustUser:String?=null,
    var estateType:Int?=1,
    var brokerUser:String?=null,
    var district:String?=null,
    var road:String?=null,
    var lane:String?=null,
    var number:String?=null,
    var room:String?=null,
    var carportNum:String?=null,
    var floorage:String?=null,
    var powerUser:String?=null,
    var decorat:String?=null,
    var facility:String?=null,
    var isMortgage:String?=null,
    var mortgageUser:String?=null,
    var mortgageBalance:String?=null,
    var isLease:String?=null,
    var housePrice:String?=null
)