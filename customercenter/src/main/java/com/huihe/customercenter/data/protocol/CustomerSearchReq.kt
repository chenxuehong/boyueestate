package com.huihe.customercenter.data.protocol

data class CustomerSearchReq(
    var customerCode:String?=null,
    var mobile:String?=null,
    var customerName:String?=null,
    var demandBeat:String?=null,
    var remarks:String?=null
)
