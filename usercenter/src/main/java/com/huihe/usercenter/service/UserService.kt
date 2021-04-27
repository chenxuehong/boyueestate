package com.huihe.usercenter.service

import io.reactivex.Observable

/*
    用户模块 业务接口
 */
interface UserService {
    //用户登录
    fun login(account:String,password:String): Observable<String>
}
