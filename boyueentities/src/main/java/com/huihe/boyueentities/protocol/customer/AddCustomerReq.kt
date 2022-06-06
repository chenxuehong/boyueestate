package com.huihe.boyueentities.protocol.customer

data class AddCustomerReq(
    var customerType:Int?=null,
    var customerName:String?=null,
    var mobile:String?=null,
    var userType:Int?=null,
    var demandBeat:String?=null,
    var demandBudget:String?=null,
    var demandFloor:String?=null,
    var remarks:String?=null
)
