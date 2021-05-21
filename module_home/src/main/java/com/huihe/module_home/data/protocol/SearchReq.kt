package com.huihe.module_home.data.protocol

data class SearchReq(
    var villageName:String?=null,
    var ownerTel:String?=null,
    var houseCode:String?=null,
    var building:String?=null,
    var hNum:String?=null,
    var schoolIds:MutableList<String>?=null,
    var createUsers:MutableList<String>?=null,
    var imageUsers:MutableList<String>?=null,
    var maintainUsers:MutableList<String>?=null,
    var bargainPriceUsers:MutableList<String>?=null,
    var blockUsers:MutableList<String>?=null,
    var haveKeyUsers:MutableList<String>?=null,
    var soleUsers:MutableList<String>?=null,
    var entrustUsers:MutableList<String>?=null
)
