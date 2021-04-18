package com.huihe.module_home.data.protocol

import java.math.BigDecimal

data class Customer(
    val coverImageUrl:String,
    val price: BigDecimal,
    val villageName:String,
    val building:String,
    val hNum:String,
    val floorage:String,
    val hShape:String,
    val floor:String,
    val totalFloor:String,
    val createUserName:String,
    val createTime:String,
    val latestFollowTime:String,
    val label:String
    )
