package com.huihe.customercenter.data.repository

import com.huihe.customercenter.data.api.CustomerApi
import com.huihe.customercenter.data.protocol.CustomerRep
import com.huihe.customercenter.data.protocol.CustomerReq
import com.huihe.customercenter.data.protocol.DeptUserRep
import com.huihe.customercenter.data.protocol.StatusRep
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

}
