package com.huihe.customercenter.data.repository

import com.huihe.customercenter.data.api.CustomerApi
import com.huihe.customercenter.data.protocol.*
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class CustomersRepository @Inject constructor() {

    fun getCustomerList(customerReq: CustomerReq): Observable<BaseResp<CustomerRep?>>  {
        return RetrofitFactory.instance.create(CustomerApi::class.java).getCustomerList(
            customerReq.page,
            customerReq.pageSize,
            customerReq.customerType,
            customerReq.createUserList,
            customerReq.status,
            customerReq.userType,
            customerReq.isOwn,
            customerReq.isTakeLook,
            customerReq.isCollection,
            customerReq.defaultOrder,
            customerReq.createTimeOrder,
            customerReq.latestFollowTimeOrder)
    }

    fun getDeptUserList():  Observable<BaseResp<MutableList<DeptUserRep>?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).getDeptUserList()
    }

    fun getCustomerStatusList(dataType: String):  Observable<BaseResp<MutableList<StatusRep>?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).getCustomerStatusList(dataType)
    }

    fun getCustomerDetail(id: String?):  Observable<BaseResp<CustomerDetailRep?>>{
        return RetrofitFactory.instance.create(CustomerApi::class.java).getCustomerDetail(id)
    }

    fun getCustomerFollowList(page: Int, limit: Int?, customerCode: String?):  Observable<BaseResp<CustomerFollowRep?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).getCustomerFollowList(page,limit,customerCode)
    }

    fun reqCollection(id: String?):  Observable<BaseResp<Any?>>  {
        return RetrofitFactory.instance.create(CustomerApi::class.java).reqCollection(id)
    }

    fun reqDeleteCollection(id: String?):  Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).reqDeleteCollection(id)
    }

    fun addFollowContent(customerCode: String?, followUpContent: String): Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).addFollowContent(AddFollowReq(customerCode,followUpContent))
    }

    fun getCustomerLogList(page: Int, limit: Int, customerCode: String?): Observable<BaseResp<CustomerLogRep?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).getCustomerLogList(page,limit,customerCode)
    }

    fun getCustomerTelLogList(page: Int, pageSize: Int, customerCode: String?): Observable<BaseResp<CustomerTelLogRep?>>  {
        return RetrofitFactory.instance.create(CustomerApi::class.java).getCustomerTelLogList(page,pageSize,customerCode)
    }

    fun setCustomerInfo(req: SetCustomersReq): Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).setCustomerInfo(req)
    }

    fun setCustomerStatus(id: String?, type: Int, value: String):  Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).setCustomerStatus(id,type,value)
    }

    fun addCustomer(req: AddCustomerReq):  Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(CustomerApi::class.java).addCustomer(req)
    }

}
