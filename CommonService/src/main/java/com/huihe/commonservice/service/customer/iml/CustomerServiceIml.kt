package com.huihe.commonservice.service.customer.iml

import com.huihe.commonservice.service.customer.CustomerService

import com.huihe.boyueentities.protocol.customer.*
import com.huihe.commonservice.data.customer.repository.CustomersRepository
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