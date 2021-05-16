package com.huihe.customercenter.data.protocol

data class SetCustomersReq(
    var id:String?,
    var deptId:String?,
    var status:Int? = 2, // 状态（1-有效；0-失效(成交）；2-删除；3-暂缓）
    var customerName:String?,
    var userType:Int?,
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
    var isStyle:String?,
    var remarks:String?,
    var demandPrice:String?,
    var customerType:Int?,
    var customerLeaver:String?,
    var customerCode:String?
)
