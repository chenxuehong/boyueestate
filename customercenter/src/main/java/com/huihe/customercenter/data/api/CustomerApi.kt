package com.huihe.customercenter.data.api

import com.huihe.customercenter.data.protocol.*
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.*

interface CustomerApi {
    @GET("customers")
    fun getCustomerList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("customerType") customerType: Int,
        @Query("createUserList") createUserList: String?,
        @Query("status") status: Int?,
        @Query("userType") userType: Int?,
        @Query("isOwn") isOwn: Int?,
        @Query("isTakeLook") isTakeLook: Int?,
        @Query("isCollection") isCollection: Int?,
        @Query("defaultOrder") defaultOrder: Int?,
        @Query("createTimeOrder") createTimeOrder: Int?,
        @Query("latestFollowTimeOrder") latestFollowTimeOrder: Int?
    ): Observable<BaseResp<CustomerRep?>>

    @GET("depts/deptUsers")
    fun getDeptUserList(): Observable<BaseResp<MutableList<DeptUserRep>?>>

    @GET("dicts/dataType")
    fun getCustomerStatusList(@Query("dataType") dataType: String): Observable<BaseResp<MutableList<StatusRep>?>>

    @GET("customers/{id}")
    fun getCustomerDetail(
        @Path("id") id: String?
    ): Observable<BaseResp<CustomerDetailRep?>>

    @GET("customers/followUps")
    fun getCustomerFollowList(
        @Query("page") page: Int,
        @Query("limit") limit: Int?,
        @Query("customerCode") customerCode: String?
    ): Observable<BaseResp<CustomerFollowRep?>>

    @POST("customers/{id}/collection")
    fun reqCollection(
        @Path("id") id: String?
    ): Observable<BaseResp<Any?>>

    @DELETE("customers/{id}/collection")
    fun reqDeleteCollection(
        @Path("id") id: String?
    ): Observable<BaseResp<Any?>>

    @POST("customers/followUps")
    fun addFollowContent(
        @Body req: AddFollowReq
    ): Observable<BaseResp<Any?>>

    @GET("customers/log")
    fun getCustomerLogList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("customerCode") customerCode: String?
    ): Observable<BaseResp<CustomerLogRep?>>

    @GET("customers/phone")
    fun getCustomerTelLogList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("customerCode") customerCode: String?
    ): Observable<BaseResp<CustomerTelLogRep?>>

    @PUT("customers")
    fun setCustomerInfo(@Body req: SetCustomersReq): Observable<BaseResp<Any?>>

    @PUT("customers/{id}/part")
    fun setCustomerStatus(
        @Path("id") id: String?,
        @Query("type") type: Int,
        @Query("value") value: String
    ): Observable<BaseResp<Any?>>

    @POST("customers")
    fun addCustomer(@Body req: AddCustomerReq): Observable<BaseResp<Any?>>
}