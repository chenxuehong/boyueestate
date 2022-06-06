package com.huihe.boyueentities.protocol.home

data class AddHouseInfoReq(
    var transactionType: Int?=null,
    var villageId: String?=null,
    var floorage: String?=null,
    var price: String?=null,
    var rent: String?=null,
    var building: String?=null,
    var hNum: String?=null,
    var ownerName: String?=null,
    var floor: String?=null,
    var totalFloor: String?=null,
    var hShape: String?=null,
    var ownerTel: String?=null

)
