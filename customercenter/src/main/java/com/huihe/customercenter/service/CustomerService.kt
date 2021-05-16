package com.huihe.customercenter.service

import com.huihe.customercenter.data.protocol.*
import io.reactivex.Observable

interface CustomerService {
    fun getCustomerList(customerReq: CustomerReq): Observable<CustomerRep?>
    fun getDeptUserList(): Observable<MutableList<DeptUserRep>?>
    fun getCustomerStatusList(dataType: String): Observable<MutableList<StatusRep>?>
    fun getCustomerDetail(id: String?): Observable<CustomerDetailRep?>
    fun getCustomerFollowList(
        page: Int,
        limit: Int?,
        customerCode: String?
    ): Observable<CustomerFollowRep?>

    fun reqCollection(id: String?): Observable<Any?>
    fun reqDeleteCollection(id: String?): Observable<Any?>
    fun addFollowContent(customerCode: String?, followUpContent: String): Observable<Any?>
    fun getCustomerLogList(page: Int, limit: Int, customerCode: String?): Observable<CustomerLogRep?>
    fun getCustomerTelLogList(page: Int, pageSize: Int, customerCode: String?): Observable<CustomerTelLogRep?>
    fun setCustomerInfo(req: SetCustomersReq): Observable<Any?>
    fun setCustomerStatus(id: String?, type: Int, value: String): Observable<Any?>
    fun addCustomer(req: AddCustomerReq): Observable<Any?>
}