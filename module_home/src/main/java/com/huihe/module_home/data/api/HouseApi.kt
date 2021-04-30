package com.huihe.module_home.data.api

import com.huihe.module_home.data.protocol.HouseWrapper
import com.huihe.module_home.data.protocol.GetHouseListReq
import com.huihe.module_home.data.protocol.HouseDetail
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HouseApi{
    /*
          房源列表
       */
    @POST("house/list")
    fun getHouseList(
        @Body req: GetHouseListReq
        ): Observable<BaseResp<HouseWrapper?>>

    @GET("dicts/dataType")
    fun getHouseList(
        @Query("pageNo") pageNo:Int?,
        @Query("pageSize") pageSize:Int?,
        @Query("dataType") dataType:Int?
        ): Observable<BaseResp<HouseWrapper?>>

    @GET("house")
    fun getHouseDetailById(
        @Query("id") id:String?
        ): Observable<BaseResp<HouseDetail?>>
}
