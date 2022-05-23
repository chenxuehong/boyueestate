package com.huihe.customercenter.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerLogRep(
    var list:MutableList<CustomerLog>?,
    var totalCount:Int?
):Parcelable{
    @Parcelize
    data class CustomerLog(
        var afterRevision: String,
        var beforeRevision: String,
        var content: String,
        var createDate: String,
        var createUser: String,
        var createUserName: String,
        var customerCode: String,
        var customerId: String,
        var deptId: String,
        var deptName: String,
        var id: String,
        var operationType: String
    ):Parcelable
}
