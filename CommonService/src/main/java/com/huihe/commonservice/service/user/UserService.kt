package com.huihe.commonservice.service.user

import com.huihe.boyueentities.protocol.common.District
import com.huihe.boyueentities.protocol.common.IMUserInfo
import com.huihe.boyueentities.protocol.common.ServerVersionInfo
import com.huihe.boyueentities.protocol.user.*
import io.reactivex.Observable

/*
    用户模块 业务接口
 */
interface UserService {
    //用户登录
    fun login(account: String, password: String, type: String): Observable<String>

    fun getVillages(
        latitude: Double? = null,
        longitude: Double? = null
    ): Observable<MutableList<District>?>

    fun getSchoolDistrictList(page:Int,limit:Int): Observable<SchoolDistrictRep?>
    fun getMobTechList(page: Int, pageSize: Int): Observable<CorporateCultureRep?>
    fun logout(): Observable<Any>
    fun getDeptUsers():Observable<MutableList<DeptUserRep>?>
    fun getUserInfo(id: String?):Observable<UserInfo?>
    fun getUserInfoFormIm():Observable<IMUserInfo?>
    fun getSystemConfig():Observable<SystemConfigRep?>
    fun getUploadToken(): Observable<String?>
    fun setUserInfo(userInfoReq: SetUserInfoReq):  Observable<SetUserInfoRep?>
    fun setPushInfo(uid: String?, registrationId: String): Observable<SetPushRep?>
    fun getServerVersionInfo(url:String): Observable<ServerVersionInfo?>
    fun getLookTaskStaffStatic(type:Int) : Observable<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>
    fun getLookTaskAdministratorsStatic(type:Int) : Observable<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>
    fun getUserLevels(): Observable<Int?>

    fun getLookTaskStaffList(status: Int,type: Int?,pageNo: Int,pageSize: Int) : Observable<MineLookTaskRep?>
    fun getLookTaskAdministratorsList(status: Int,type: Int?,pageNo: Int,pageSize: Int) : Observable<MineLookTaskRep?>
    fun getLookTaskDetail(takeLookId: String?) : Observable<MutableList<LookTaskDetailRep>?>
    fun deleteLookHouse(id: String?): Observable<Any>
    fun insertMineLookHouse(req: MineLookHouseReq): Observable<String>
    fun doTransfer(id: String?, changeUserId: String?): Observable<Any>
    fun deleteLookTask(id: String?): Observable<Any>
    fun lookTaskFollow(req: MineLookHouseFollowReq): Observable<Any>
    fun lookHouseAccompanyFollow(req: LookHouseAccompanyFollowReq): Observable<Any>
    fun putLookTaskAudit(req: LookTaskAuditReq?): Observable<Any>
    fun lookHouseReview(req: LookHouseReviewReq): Observable<Any>
    fun accompanySign(req: SignReq?): Observable<Any>
    fun takeSign(req: SignReq?): Observable<Any>
}
