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
}
