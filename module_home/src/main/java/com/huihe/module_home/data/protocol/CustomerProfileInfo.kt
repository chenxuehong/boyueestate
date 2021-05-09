package com.huihe.module_home.data.protocol

data class CustomerProfileInfo(
    var houseInfo:HouseInfo
){
    data class HouseInfo(
        var floor:String?,
        var floorage:String?,
        var hFlag:String?,
        var hShape:String?,
        var houseCode:String?,
        var id:String?,
        var maintainUser:String?,
        var maintainUserName:String?,
        var price:String?,
        var rent:String?,
        var totalFloor:String?,
        var transactionType:String?,
        var villageName:String?
    )
}
