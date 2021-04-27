package com.huihe.usercenter.data.respository

import com.huihe.usercenter.data.api.UserApi
import com.huihe.usercenter.data.protocol.LoginReq
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class UserRepository @Inject constructor(){
    /*
        登录
     */
    fun login(account: String, password: String) : Observable<BaseResp<String>> {

        return RetrofitFactory.instance.create(UserApi::class.java).login(LoginReq(account,password))
    }
}
