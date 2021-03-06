package com.huihe.commonservice.data.user.api

import com.huihe.boyueentities.protocol.common.District
import com.huihe.boyueentities.protocol.common.IMUserInfo
import com.huihe.boyueentities.protocol.common.ServerVersionInfo
import com.huihe.boyueentities.protocol.user.*
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.*

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

    @GET("look/task/staff/static")
    fun getLookTaskStaffStatic(
        @Query("type") type: Int): Observable<BaseResp<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>>


    @GET("look/task/administrators/static")
    fun getLookTaskAdministratorsStatic(
        @Query("type") type: Int): Observable<BaseResp<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>>

    @GET("look/task/user/levels")
    fun getUserLevels(): Observable<BaseResp<Int?>>

    @GET("look/task/staff")
    fun getLookTaskStaffList(
        @Query("status") status: Int,
        @Query("type") type: Int?,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int): Observable<BaseResp<MineLookTaskRep?>>

    @GET("look/task/administrators")
    fun getLookTaskAdministratorsList(
        @Query("status") status: Int,
        @Query("type") type: Int?,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int): Observable<BaseResp<MineLookTaskRep?>>

    @GET("look/house/{takeLookId}")
    fun getLookTaskDetail(
        @Path("takeLookId") id: String?): Observable<BaseResp<MutableList<LookTaskDetailRep>?>>

    @DELETE("look/house/{id}")
    fun deleteLookHouse(
        @Path("id") id: String?):  Observable<BaseResp<Any>>

    @POST("look/house")
    fun insertMineLookHouse(
        @Body req: MineLookHouseReq): Observable<BaseResp<String>>

    @PUT("look/task")
    fun transferLookTask(
        @Query("id") id: String?,
        @Query("changeUserId") changeUserId: String?
    ): Observable<BaseResp<Any>>

    @DELETE("look/task/{id}")
    fun deleteLookTask(
        @Path("id") id: String?):  Observable<BaseResp<Any>>

    @PUT("look/house/take/follow")
    fun lookTaskFollow(
        @Body req: MineLookHouseFollowReq): Observable<BaseResp<Any>>

    @PUT("look/house/accompany/follow")
    fun lookHouseAccompanyFollow(
        @Body req: LookHouseAccompanyFollowReq): Observable<BaseResp<Any>>

    @PUT("look/task/audit")
    fun putLookTaskAudit(
        @Body req: LookTaskAuditReq?): Observable<BaseResp<Any>>

    @PUT("look/house/review")
    fun lookHouseReview(
        @Body req: LookHouseReviewReq): Observable<BaseResp<Any>>

    @PUT("look/house/accompany/sign")
    fun accompanySign(
        @Body req: SignReq?): Observable<BaseResp<Any>>

    @PUT("look/house/take/sign")
    fun takeSign(
        @Body req: SignReq?): Observable<BaseResp<Any>>
}
