package com.huihe.customercenter.service

import com.huihe.customercenter.data.protocol.CustomerRep
import com.huihe.customercenter.data.protocol.CustomerReq
import com.huihe.customercenter.data.protocol.DeptUserRep
import com.huihe.customercenter.data.protocol.StatusRep
import io.reactivex.Observable

interface CustomerService {
    fun getCustomerList(customerReq: CustomerReq): Observable<CustomerRep?>
    fun getDeptUserList():Observable<MutableList<DeptUserRep>?>
    fun getCustomerStatusList(dataType: String):Observable<MutableList<StatusRep>?>
}