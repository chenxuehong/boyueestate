package com.huihe.boyueentities.protocol.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerTelLogRep(
   var list: MutableList<CustomerTelLog>?,
   var totalCount:Int?
):Parcelable{
    @Parcelize
    data class CustomerTelLog(
        var afterRevision:String?,
        var beforeRevision:String?,
        var createDate:String?,
        var createUser:String?,
        var createUserName:String?,
        var customerCode:String?,
        var customerId:String?,
        var deptId:String?,
        var deptName:String?,
        var id:String?,
        var operationType:String?
    ):Parcelable
}
