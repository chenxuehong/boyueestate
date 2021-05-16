package com.huihe.usercenter.service

import com.huihe.usercenter.data.protocol.*
import io.reactivex.Observable

/*
    用户模块 业务接口
 */
interface UserService {
    //用户登录
    fun login(account:String,password:String): Observable<String>

    fun getVillages(
        latitude: Double? = null,
        longitude: Double? = null
    ): Observable<AreaBeanRep?>

    fun getSchoolDistrictList(page:Int,limit:Int): Observable<SchoolDistrictRep?>
    fun getMobTechList(page: Int, pageSize: Int): Observable<CorporateCultureRep?>
    fun logout(): Observable<Any>
    fun getDeptUsers():Observable<MutableList<DeptUserRep>?>
    fun getUserInfo(id: String?):Observable<UserInfo?>
    fun getUserInfoFormIm():Observable<UserInfo?>
}
