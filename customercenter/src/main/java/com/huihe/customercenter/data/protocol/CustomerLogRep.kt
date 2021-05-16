package com.huihe.customercenter.data.protocol

data class CustomerLogRep(
    var list:MutableList<CustomerLog>?,
    var totalCount:Int?
){

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
    )
}
