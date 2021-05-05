package com.huihe.module_home.data.api

import com.huihe.module_home.data.protocol.*
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.*

interface HouseApi {
    /*
          房源列表
       */
    @POST("house/list")
    fun getHouseList(
        @Body req: GetHouseListReq
    ): Observable<BaseResp<HouseWrapper?>>

    @GET("dicts/dataType")
    fun getHouseList(
        @Query("pageNo") pageNo: Int?,
        @Query("pageSize") pageSize: Int?,
        @Query("dataType") dataType: Int?
    ): Observable<BaseResp<HouseWrapper?>>

    @GET("house/{id}")
    fun getHouseDetailById(
        @Path("id") id:String?
    ): Observable<BaseResp<HouseDetail?>>


    @GET("house/{id}/relationPeople")
    fun getHouseDetailRelationPeople(
        @Path("id") id:String?
    ): Observable<BaseResp<OwnerInfo?>>

    @GET("villages")
    fun getVillages(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?
    ): Observable<BaseResp<AreaBeanWrapper?>>
}
