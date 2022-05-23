package com.huihe.customercenter.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerDetailRep(
    var createDate:String?,
    var createUser:String?,
    var createUserName:String?,
    var customerCode:String?,
    var customerLeaver:String?,
    var customerName:String?,
    var customerType:Int?,
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
    var isFirstHouse:String?,
    var isStyle:String?,
    var isSubstitution:String?,
    var isTop:Int?,
    var mobile:String?,
    var remarks:String?,
    var source:String?,
    var status:Int?,
    var successDate:String?,
    var takeLookCount:Int?,
    var topUser:String?,
    var updateDate:String?,
    var updateUser:String?,
    var updateUserName:String?,
    var userType:Int?,
    var viewHouseDate:String?
):Parcelable