package com.huihe.customercenter.data.protocol

data class CustomerDetailBean(
    var type:Int=0,
    var mainInfo:CustomerMainInfo?=null,
    var helperInfo:HelperInfo?=null,
    var basicInfoList:MutableList<BasicInfo>?=null
){
    data class CustomerMainInfo(
        var customerType:Int?,
        var customerName:String?,
        var isTop:Int?,
        var isCornucopia:Int?,
        var takeLookCount:Int?,
        var demandBeat:String?,
        var demandBudget:String?
    )

    data class HelperInfo(
        var mobile :MutableList<String>?,
        var customerCode:String?
    )

    data class ItemHelperInfo(
        var helperInfo :HelperInfo?,
        var title:String?,
        var icon:Int?
    )

    data class BasicInfo(
        var type:Int,
      var customerBasicInfo:CustomerBasicInfo?=null,
      var rewarks:String?=null,
      var createUserInfo:CreateUserInfo?=null
    ){
        data class CustomerBasicInfo(
            var customerName:String?,
            var source:String?,
            var demandFloor:String?,
            var demandHouseType:String?,
            var demandArea:String?,
            var demandBeat:String?,
            var isSubstitution:String?,
            var demandBudget:String?,
            var viewHouseDate:String?,
            var isFirstHouse:String?,
            var successDate:String?,
            var isStyle:String?
        )
        data class CreateUserInfo(
            var createUserName:String?,
            var createDate:String?,
            var updateUserName:String?,
            var updateDate:String?
        )
    }

    data class ItemBasicInfo(
        var title:String?,
        var content:String?,
        var isTitle:Boolean
    )

}
