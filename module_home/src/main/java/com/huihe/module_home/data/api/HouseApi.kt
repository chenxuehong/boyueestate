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
        @Path("id") id: String?
    ): Observable<BaseResp<HouseDetail?>>


    @GET("house/{id}/relationPeople")
    fun getHouseDetailRelationPeople(
        @Path("id") id: String?
    ): Observable<BaseResp<OwnerInfo?>>

    @GET("villages")
    fun getVillages(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?
    ): Observable<BaseResp<AreaBeanRep?>>

    @POST("house/{id}/collection")
    fun reqCollection(
        @Path("id") id: String?
    ): Observable<BaseResp<Any?>>

    @DELETE("house/{id}/collection")
    fun reqDeleteCollection(
        @Path("id") id: String?
    ): Observable<BaseResp<Any?>>

    @PATCH("house")
    fun setHouseInfo(@Body req: SetHouseInfoReq): Observable<BaseResp<SetHouseInfoRep?>>

    @PUT("house")
    fun putHouseInfo(@Body req: SetHouseInfoReq): Observable<BaseResp<SetHouseInfoRep?>>

    @POST("house/houseImage")
    fun postHouseImage(@Body req: SetHouseInfoReq): Observable<BaseResp<String?>>

    @POST("house/referImage")
    fun postReferImage(@Body req: SetHouseInfoReq): Observable<BaseResp<String?>>

    @GET("house/{id}/profile")
    fun getCustomerProfile(@Path("id") id: String?): Observable<BaseResp<CustomerProfileInfo?>>

    @GET("house/follow/list")
    fun getHouseFollowList(
        @Query("page")   page: Int?,
        @Query("pageSize")  pageSize: Int?,
        @Query("houseCode")   houseCode: String?
    ): Observable<BaseResp<FollowRep?>>

    @POST("house/follow")
    fun addFollowContent(
       @Body req :FollowReq
    ): Observable<BaseResp<FollowRep.FollowBean?>>

    @GET("take/look")
    fun getTakeLookRecord(
        @Query("page")    page: Int?,
        @Query("limit")    limit: Int?,
        @Query("code")   code: String?
    ): Observable<BaseResp<HouseTakeLookRep?>>

    @POST("take/look")
    fun addHouseTakeLookRecord(
        @Body req: AddTakeLookRecordReq): Observable<BaseResp<HouseTakeLookRep.HouseTakeLook?>>

    @GET("house/log/list")
    fun getHouseLog(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("houseCode") houseCode: String?): Observable<BaseResp<HouseLogRep?>>

    @GET("house/phone/log/list")
    fun getHousePhoneLog(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("houseCode") houseCode: String?
    ): Observable<BaseResp<HouseLogRep?>>
}
