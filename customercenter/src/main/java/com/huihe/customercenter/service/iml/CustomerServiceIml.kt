package com.huihe.customercenter.service.iml

import com.huihe.customercenter.data.protocol.*
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

    override fun getCustomerDetail(id: String?): Observable<CustomerDetailRep?> {
        return repository.getCustomerDetail(id).convert()
    }

    override fun getCustomerFollowList(
        page: Int,
        limit: Int?,
        customerCode: String?
    ): Observable<CustomerFollowRep?> {
        return repository.getCustomerFollowList(page,limit,customerCode).convert()
    }

    override fun reqCollection(id: String?): Observable<Any?> {
        return repository.reqCollection(id).convert()
    }

    override fun reqDeleteCollection(id: String?): Observable<Any?> {
        return repository.reqDeleteCollection(id).convert()
    }

    override fun addFollowContent(
        customerCode: String?,
        followUpContent: String
    ): Observable<Any?> {
        return repository.addFollowContent(customerCode,followUpContent).convert()
    }

    override fun getCustomerLogList(
        page: Int,
        limit: Int,
        customerCode: String?
    ): Observable<CustomerLogRep?> {
        return repository.getCustomerLogList(page,limit,customerCode).convert()
    }

    override fun getCustomerTelLogList(
        page: Int,
        pageSize: Int,
        customerCode: String?
    ): Observable<CustomerTelLogRep?> {
        return repository.getCustomerTelLogList(page,pageSize,customerCode).convert()
    }

    override fun setCustomerInfo(req: SetCustomersReq): Observable<Any?> {
        return repository.setCustomerInfo(req).convert()
    }

    override fun setCustomerStatus(id: String?, type: Int, value: String): Observable<Any?> {
        return repository.setCustomerStatus(id,type,value).convert()
    }

    override fun addCustomer(req: AddCustomerReq): Observable<Any?> {
        return repository.addCustomer(req).convert()
    }

    override fun getCustomerMobile(id: String?): Observable<CustomerMobileRep?> {
        return repository.getCustomerMobile(id).convert()
    }

    override fun checkCustomer(mobile: String?): Observable<CheckCustomerRep?> {
        return repository.checkCustomer(mobile).convert()
    }

}