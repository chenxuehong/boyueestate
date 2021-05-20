package com.huihe.usercenter.service.iml

import com.huihe.usercenter.data.protocol.*
import com.kotlin.provider.data.protocol.IMUserInfo
import com.huihe.usercenter.data.respository.UserRepository
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.convert
import com.kotlin.provider.data.protocol.District
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
}
