package com.huihe.customercenter.data.protocol

data class CustomerRep(
    val list:MutableList<Customer>,
    val totalCount:Int
){
    data class Customer(
        val createDate:String?,
        val createUser:String?,
        val createUserName:String?,
        val customerCode:String?,
        val customerName:String?,
        val customerType:String?,
        val demandArea:String?,
        val demandBeat:String?,
        val demandBudget:String?,
        val demandFloor:String?,
        val demandHouseType:String?,
        val demandPrice:String?,
        val deptId:String?,
        val developer:String?,
        val developerDate:String?,
        val developerName:String?,
        val followUpDate:String?,
        val id:String?,
        val isCollection:String?,
        val isCornucopia:String?,
        val isFirstHouse:String?,
        val isStyle:String?,
        val isSubstitution:String?,
        val isTop:String?,
        val mobile:String?,
        val remarks:String?,
        val source:String?,
        val status:String?,
        val successDate:String?,
        val takeLookCount:Int?=0,
        val topUser:String?,
        val updateUserName:String?,
        val userType:String?,
        val viewHouseDate:String?
    )
}