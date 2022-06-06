package com.huihe.boyueentities.protocol.customer

data class CustomerSearchReq(
    var customerCode:String?=null,
    var mobile:String?=null,
    var customerName:String?=null,
    var demandBeat:String?=null,
    var remarks:String?=null
)
