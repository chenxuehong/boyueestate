package com.huihe.module_home.data.protocol

data class AddTakeLookRecordReq(
    val houseCodeList: MutableList<String>?,
    val evaluate: String?,
    val customerCode: String?
)
