package com.huihe.module_home.data.protocol

import java.math.BigDecimal

data class Customer(
    val id:String,
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
    val label:String,
    val changePrice:BigDecimal,
    val districtName:String,
    val hFlag:String,
    val houseCode:String,
    val imagUrl:String,
    val isCirculation:Int,
    val unitPrice:BigDecimal,
    val villageAddress:String,
    val zoneName:String,
    val remarks:String
    )
