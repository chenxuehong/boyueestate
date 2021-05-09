package com.huihe.module_home.data.protocol

data class HouseTakeLookRep(
    var list:MutableList<HouseTakeLook>,
    var totalCount:Int
){
    data class HouseTakeLook(
        var createDate:String,
        var customerCode:String,
        var customerId:String,
        var deptId:String,
        var deptName:String,
        var evaluate:String,
        var houseCode:String,
        var houseId:String,
        var id:String,
        var takeLookUser:String,
        var takeLookUserId:String
    )
}
