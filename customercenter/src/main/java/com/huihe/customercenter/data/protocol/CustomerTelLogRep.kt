package com.huihe.customercenter.data.protocol

data class CustomerTelLogRep(
   var list: MutableList<CustomerTelLog>?,
   var totalCount:Int?
){
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
    )
}
