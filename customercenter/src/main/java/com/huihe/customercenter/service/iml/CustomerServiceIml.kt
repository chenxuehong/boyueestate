package com.huihe.customercenter.service.iml

import com.huihe.customercenter.data.protocol.CustomerRep
import com.huihe.customercenter.data.protocol.CustomerReq
import com.huihe.customercenter.data.protocol.DeptUserRep
import com.huihe.customercenter.data.protocol.StatusRep
import com.huihe.customercenter.data.repository.CustomersRepository
import com.huihe.customercenter.service.CustomerService
import com.kotlin.base.ext.convert
import io.reactivex.Observable
import javax.inject.Inject

class CustomerServiceIml @Inject constructor(): CustomerService {
    @Inject
    lateinit var repository: CustomersRepository

    override fun getCustomerList(customerReq: CustomerReq): Observable<CustomerRep?> {
        return repository.getCustomerList(
            customerReq
        ).convert()
    }

    override fun getDeptUserList(): Observable<MutableList<DeptUserRep>?> {
        return repository.getDeptUserList().convert()
    }

    override fun getCustomerStatusList(dataType: String): Observable<MutableList<StatusRep>?> {
        return repository.getCustomerStatusList(dataType).convert()
    }

}