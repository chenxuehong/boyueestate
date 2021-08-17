package com.huihe.module_home.data.protocol

data class AddTakeLookRecordReq(
    var customerCode: String?,
    var houseCode: String?=null,
    var accompanyUser: String?=null,
    var accompanyUserId: String?=null,
    var customerId: String?=null,
    var customerName: String?=null,
    var follow: String?=null,
    var lookTime: String?=null,
    var takeLookUser: String?=null,
    var takeLookUserId: String?=null
)
