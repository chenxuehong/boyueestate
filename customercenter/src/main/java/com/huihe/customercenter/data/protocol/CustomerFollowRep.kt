package com.huihe.customercenter.data.protocol

data class CustomerFollowRep(
    var list: MutableList<CustomerFollow>?,
    var totalCount: Int
) {
    data class CustomerFollow(
        var createDate: String?,
        var customerCode: String?,
        var customerId: String?,
        var customerName: String?,
        var deptId: String?,
        var deptName: String?,
        var followUpContent: String?,
        var followUpId: String?,
        var followUpName: String?,
        var id: String?
    )
}
