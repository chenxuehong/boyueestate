package com.huihe.usercenter.data.api

import com.huihe.usercenter.data.protocol.*
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.provider.data.protocol.District
import io.reactivex.Observable
import retrofit2.http.*
import com.kotlin.provider.data.protocol.IMUserInfo
import com.kotlin.provider.data.protocol.ServerVersionInfo

interface UserApi {

    @POST("auth/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<String>>

    @GET("districts/villages")
    fun getVillages(): Observable<BaseResp<MutableList<District>?>>

    @GET("schools")
    fun getSchoolDistrictList(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Observable<BaseResp<SchoolDistrictRep?>>

    @GET("mobTech")
    fun getMobTechList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<BaseResp<CorporateCultureRep?>>

    @POST("auth/logout")
    fun logout(): Observable<BaseResp<Any>>

    @GET("depts/deptUsers")
    fun getDeptUsers(): Observable<BaseResp<MutableList<DeptUserRep>?>>

    @GET("users/{id}")
    fun getUserInfo(@Path("id") id: String?): Observable<BaseResp<UserInfo?>>

    @GET("users/imPrincipal")
    fun getUserInfoFormIm(): Observable<BaseResp<IMUserInfo?>>

    @GET("share/system/config")
    fun getSystemConfig(): Observable<BaseResp<SystemConfigRep?>>

    @PATCH("users")
    fun setUserInfo(
        @Body userInfoReq: SetUserInfoReq): Observable<BaseResp<SetUserInfoRep?>>

    @PATCH("users/mobTechId")
    fun setPushInfo(
        @Body setPushReq: SetPushReq): Observable<BaseResp<SetPushRep?>>

    @GET("60a4668fb2eb465a9a09370f?api_token=a93d04a00cedb2b2251be28a99e21616")
    fun getServerVersionInfo(): Observable<ServerVersionInfo?>
}
