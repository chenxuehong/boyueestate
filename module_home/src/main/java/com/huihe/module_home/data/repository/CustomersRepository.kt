package com.huihe.module_home.data.repository

import com.huihe.module_home.data.api.CustomersApi
import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.data.protocol.CustomerWrapper
import com.huihe.module_home.data.protocol.GetHouseListReq
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class CustomersRepository @Inject constructor() {

    fun getMoreCustomersList(
        pageNo: Int?,
        pageSize: Int?,
        myHouse: Int?,
        hasKey: Int?,
        hasSole: Int?,
        myMaintain: Int?,
        isCirculation: Int?,
        entrustHouse: Int?,
        myCollect: Int?,
        floorageRanges: Map<String, String>?,
        roomNumRanges: Map<String, String>?
    ): Observable<BaseResp<CustomerWrapper?>> {
        return RetrofitFactory.instance.create(CustomersApi::class.java)
            .getMoreCustomersList(
                GetHouseListReq(
                    pageNo,pageSize,
                    myHouse, hasKey,hasSole,
                    myMaintain,isCirculation,entrustHouse,
                    myCollect,floorageRanges,roomNumRanges
                )
            )
    }
}
