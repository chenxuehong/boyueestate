package com.huihe.customercenter.data.api

import com.huihe.customercenter.data.protocol.CustomerRep
import com.huihe.customercenter.data.protocol.DeptUserRep
import com.huihe.customercenter.data.protocol.StatusRep
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

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
}