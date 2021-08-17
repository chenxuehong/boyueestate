package com.huihe.usercenter.data.respository

import com.huihe.usercenter.data.api.UserApi
import com.huihe.usercenter.data.protocol.*
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.provider.data.api.UploadApi
import com.kotlin.provider.data.protocol.District
import io.reactivex.Observable
import javax.inject.Inject
import com.kotlin.provider.data.protocol.IMUserInfo
import com.kotlin.provider.data.protocol.ServerVersionInfo

class UserRepository @Inject constructor(){
    /*
        登录
     */
    fun login(account: String, password: String, type: String) : Observable<BaseResp<String>> {

        return RetrofitFactory.instance.create(UserApi::class.java).login(LoginReq(account,password,type))
    }

    fun getVillages(latitude: Double?, longitude: Double?): Observable<BaseResp<MutableList<District>?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getVillages()
    }

    fun getSchoolDistrictList(page:Int,limit:Int):  Observable<BaseResp<SchoolDistrictRep?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getSchoolDistrictList(page,limit)
    }

    fun getMobTechList(page: Int, pageSize: Int): Observable<BaseResp<CorporateCultureRep?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getMobTechList(page,pageSize)
    }

    fun logout(): Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .logout()
    }

    fun getDeptUsers(): Observable<BaseResp<MutableList<DeptUserRep>?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getDeptUsers()
    }

    fun getUserInfo(id: String?): Observable<BaseResp<UserInfo?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getUserInfo(id)
    }

    fun getUserInfoFormIm(): Observable<BaseResp<IMUserInfo?>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getUserInfoFormIm()
    }

    fun getSystemConfig(): Observable<BaseResp<SystemConfigRep?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getSystemConfig()
    }

    fun getUploadToken(): Observable<BaseResp<String?>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
            .getUploadToken()
    }

    fun setUserInfo(userInfoReq: SetUserInfoReq):  Observable<BaseResp<SetUserInfoRep?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .setUserInfo(userInfoReq)
    }

    fun setPushInfo(uid: String?, registrationId: String):  Observable<BaseResp<SetPushRep?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .setPushInfo(SetPushReq(uid,registrationId))
    }

    fun getServerVersionInfo(url:String): Observable<ServerVersionInfo?> {
        return RetrofitFactory.instance.getNewRetrofit(url).create(UserApi::class.java)
                .getServerVersionInfo()
    }

    fun getLookTaskStaffStatic(type: Int): Observable<BaseResp<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getLookTaskStaffStatic(type)
    }

    fun getLookTaskAdministratorsStatic(type: Int): Observable<BaseResp<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getLookTaskAdministratorsStatic(type)
    }

    fun getUserLevels(): Observable<BaseResp<Int?>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getUserLevels()
    }

    fun getLookTaskStaffList(status: Int,type: Int?,pageNo: Int,pageSize: Int): Observable<BaseResp<MineLookTaskRep?>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getLookTaskStaffList(status,type,pageNo,pageSize)
    }

    fun getLookTaskAdministratorsList(status: Int,type: Int?,pageNo: Int,pageSize: Int): Observable<BaseResp<MineLookTaskRep?>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getLookTaskAdministratorsList(status,type,pageNo,pageSize)
    }

    fun getLookTaskDetail(takeLookId: String?): Observable<BaseResp<MutableList<LookTaskDetailRep>?>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .getLookTaskDetail(takeLookId)
    }

    fun deleteLookHouse(id: String?):  Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .deleteLookHouse(id)
    }

    fun insertMineLookHouse(req: MineLookHouseReq):  Observable<BaseResp<String>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .insertMineLookHouse(req)
    }

    fun doTransfer(id: String?, changeUserId: String?):  Observable<BaseResp<Any>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .transferLookTask(id,changeUserId)
    }

    fun deleteLookTask(id: String?):  Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .deleteLookTask(id)
    }

    fun lookTaskFollow(req: MineLookHouseFollowReq):  Observable<BaseResp<Any>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .lookTaskFollow(req)
    }

    fun lookHouseAccompanyFollow(req: LookHouseAccompanyFollowReq):  Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .lookHouseAccompanyFollow(req)
    }

    fun putLookTaskAudit(req: LookTaskAuditReq?):  Observable<BaseResp<Any>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .putLookTaskAudit(req)
    }

    fun lookHouseReview(req: LookHouseReviewReq):  Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .lookHouseReview(req)
    }

    fun accompanySign(req: SignReq?):  Observable<BaseResp<Any>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .accompanySign(req)
    }

    fun takeSign(req: SignReq?):  Observable<BaseResp<Any>>  {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .takeSign(req)
    }
}
