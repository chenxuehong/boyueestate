package com.huihe.commonservice.data.upload

import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.GET

interface UploadApi {
    @GET("qiniu/token")
    fun getUploadToken(): Observable<BaseResp<String?>>
}