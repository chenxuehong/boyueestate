package com.huihe.module_home.data.protocol

data class CustomerProfileInfo(
    var houseInfo:MutableList<HouseInfo>?,
    var rentCustomerCount:String?,
    var saleCustomerCount:String?,
    var saleCustomer:MutableList<CustomerInfo>?,
    var rentCustomer:MutableList<CustomerInfo>?
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
        var villageName:String?,
        var coverImageUrl:String?,
        var latestFollowTime:String?
    )

    data class CustomerInfo(
        var createDate:String?,
        var createUser:String?,
        var createUserName:String?,
        var customerCode:String?,
        var customerName:String?,
        var customerType:String?,
        var demandArea:String?,
        var demandBeat:String?,
        var demandBudget:String?,
        var demandFloor:String?,
        var demandHouseType:String?,
        var demandPrice:String?,
        var deptId:String?,
        var developer:String?,
        var developerDate:String?,
        var developerName:String?,
        var followUpDate:String?,
        var id:String?,
        var isCollection:Int?,
        var isCornucopia:Int?,
        var isFirstHouse:Int?,
        var isStyle:Int?,
        var isSubstitution:Int?,
        var isTop:Int?,
        var mobile:String?,
        var remarks:String?,
        var source:String?,
        var status:Int?,
        var successDate:String?,
        var updateUser:String?,
        var updateUserName:String?,
        var userType:String?,
        var viewHouseDate:String?
    )
}
