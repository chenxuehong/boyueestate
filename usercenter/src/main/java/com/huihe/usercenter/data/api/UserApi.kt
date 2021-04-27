package com.huihe.usercenter.data.api

import com.huihe.usercenter.data.protocol.LoginReq
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi{

    @POST("auth/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<String>>
}
