package com.huihe.module_home.data.api

import com.huihe.module_home.data.protocol.Customer
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomersApi{
    /*
          房源列表
       */
    @GET("house/list")
    fun getMoreCustomersList(
        @Query("myHouse")  myHouse: Int,
        @Query("hasKey")  hasKey: Int,
        @Query("hasSole")  hasSole: Int,
        @Query("myMaintain")  myMaintain: Int,
        @Query("isCirculation")  isCirculation: Int,
        @Query("entrustHouse")  entrustHouse: Int,
        @Query("myCollect")  myCollect: Int,
        @Query("floorageRanges")  floorageRanges: Map<String, String>,
        @Query("roomNumRanges")  roomNumRanges: Map<String, String>
        ): Observable<BaseResp<MutableList<Customer>?>>
}
