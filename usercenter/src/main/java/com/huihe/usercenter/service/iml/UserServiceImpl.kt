package com.huihe.usercenter.service.iml

import com.huihe.usercenter.data.protocol.*
import com.kotlin.provider.data.protocol.IMUserInfo
import com.huihe.usercenter.data.respository.UserRepository
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.convert
import com.kotlin.provider.data.protocol.District
import com.kotlin.provider.data.protocol.ServerVersionInfo
import io.reactivex.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun login(account: String, password: String, type: String): Observable<String> {

        return repository.login(account,password,type).convert()
    }

    override fun getVillages(latitude: Double?, longitude: Double?): Observable<MutableList<District>?> {
        return repository.getVillages(latitude, longitude).convert()
    }

    override fun getSchoolDistrictList(page:Int,limit:Int): Observable<SchoolDistrictRep?> {
        return repository.getSchoolDistrictList(page,limit).convert()
    }

    override fun getMobTechList(page: Int, pageSize: Int): Observable<CorporateCultureRep?> {
        return repository.getMobTechList(page,pageSize).convert()
    }

    override fun logout(): Observable<Any> {
        return repository.logout().convert()
    }

    override fun getDeptUsers(): Observable<MutableList<DeptUserRep>?> {
        return repository.getDeptUsers().convert()
    }

    override fun getUserInfo(id: String?): Observable<UserInfo?> {
        return repository.getUserInfo(id).convert()
    }

    override fun getUserInfoFormIm(): Observable<IMUserInfo?> {
        return repository.getUserInfoFormIm().convert()
    }

    override fun getSystemConfig(): Observable<SystemConfigRep?> {
        return repository.getSystemConfig().convert()
    }

    override fun getUploadToken(): Observable<String?> {
        return repository.getUploadToken().convert()
    }

    override fun setUserInfo(userInfoReq: SetUserInfoReq): Observable<SetUserInfoRep?> {
        return repository.setUserInfo(userInfoReq).convert()
    }

    override fun setPushInfo(uid: String?, registrationId: String): Observable<SetPushRep?> {
        return repository.setPushInfo(uid,registrationId).convert()
    }

    override fun getServerVersionInfo(url:String): Observable<ServerVersionInfo?> {
        return repository.getServerVersionInfo(url)
    }

    override fun getLookTaskStaffStatic(type:Int) :Observable<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>{
        return repository.getLookTaskStaffStatic(type).convert()
    }

    override fun getLookTaskAdministratorsStatic(type: Int): Observable<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?> {
        return repository.getLookTaskAdministratorsStatic(type).convert()
    }

    override fun getUserLevels(): Observable<Int?> {
        return repository.getUserLevels().convert()
    }

    override fun getLookTaskStaffList(status: Int,type: Int?,pageNo: Int,pageSize: Int): Observable<MineLookTaskRep?> {
        return repository.getLookTaskStaffList(status,type,pageNo,pageSize).convert()
    }

    override fun getLookTaskAdministratorsList(status: Int,type: Int?,pageNo: Int,pageSize: Int): Observable<MineLookTaskRep?> {
        return repository.getLookTaskAdministratorsList(status,type,pageNo,pageSize).convert()
    }

    override fun getLookTaskDetail(takeLookId: String?): Observable<MutableList<LookTaskDetailRep>?> {
        return repository.getLookTaskDetail(takeLookId).convert()
    }

    override fun deleteLookHouse(id: String?): Observable<Any> {
        return repository.deleteLookHouse(id).convert()
    }

    override fun insertMineLookHouse(req: MineLookHouseReq): Observable<String> {
        return repository.insertMineLookHouse(req).convert()
    }

    override fun doTransfer(id: String?, changeUserId: String?): Observable<Any> {
        return repository.doTransfer(id,changeUserId).convert()
    }

    override fun deleteLookTask(id: String?): Observable<Any> {
        return repository.deleteLookTask(id).convert()
    }

    override fun lookTaskFollow(req: MineLookHouseFollowReq): Observable<Any> {
        return repository.lookTaskFollow(req).convert()
    }

    override fun lookHouseAccompanyFollow(req: LookHouseAccompanyFollowReq): Observable<Any> {
        return repository.lookHouseAccompanyFollow(req).convert()
    }

    override fun putLookTaskAudit(req: LookTaskAuditReq?): Observable<Any> {
        return repository.putLookTaskAudit(req).convert()
    }

    override fun lookHouseReview(req: LookHouseReviewReq): Observable<Any> {
        return repository.lookHouseReview(req).convert()
    }

    override fun accompanySign(req: SignReq?): Observable<Any> {
        return repository.accompanySign(req).convert()
    }

    override fun takeSign(req: SignReq?): Observable<Any> {
        return repository.takeSign(req).convert()
    }
}
