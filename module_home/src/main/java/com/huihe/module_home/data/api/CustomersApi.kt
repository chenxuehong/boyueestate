package com.huihe.module_home.data.api

import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.data.protocol.CustomerWrapper
import com.huihe.module_home.data.protocol.GetHouseListReq
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomersApi{
    /*
          房源列表
       */
    @POST("house/list")
    fun getMoreCustomersList(
        @Body req: GetHouseListReq
        ): Observable<BaseResp<CustomerWrapper?>>
}
