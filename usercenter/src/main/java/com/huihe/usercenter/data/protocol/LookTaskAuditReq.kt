package com.huihe.usercenter.data.protocol

data class LookTaskAuditReq(
    var customerCode:String?=null,
    var followUpContent:String?=null,
    var isQualified:Int?=null,
    var takeLookTaskId:String?=null
)
