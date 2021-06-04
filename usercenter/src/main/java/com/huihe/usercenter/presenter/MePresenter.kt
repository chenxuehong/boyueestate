package com.huihe.usercenter.presenter

import com.huihe.usercenter.data.protocol.SetUserInfoRep
import com.huihe.usercenter.data.protocol.SetUserInfoReq
import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.presenter.view.MeView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import com.kotlin.provider.utils.UserPrefsUtils
import javax.inject.Inject

class MePresenter @Inject constructor(): BasePresenter<MeView>(){

    @Inject
    lateinit var service: UserService

    fun getUserInfo() {
        var userInfo = UserPrefsUtils.getUserInfo()
        service.getUserInfo(userInfo?.uid)
            .execute(object :BaseSubscriber<UserInfo?>(mView){

                override fun onNext(t: UserInfo?) {
                    super.onNext(t)
                    mView?.onUserInfo(t)
                }
            },lifecycleProvider)
    }

    fun setUserInfo(userInfoReq: SetUserInfoReq) {
        var userInfo = UserPrefsUtils.getUserInfo()
        userInfoReq.uid = userInfo?.uid
        service.setUserInfo(userInfoReq)
            .execute(object :BaseSubscriber<SetUserInfoRep?>(mView){

                override fun onNext(t: SetUserInfoRep?) {
                    super.onNext(t)
                    mView?.onSetUserInfo()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if(e is DataNullException){
                        mView?.onSetUserInfo()
                    }
                }
            },lifecycleProvider)
    }


    fun getUploadToken() {
        mView?.showLoading("正在上传...")
        service.getUploadToken()
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onGetUploadTokenResult(t)
                }
            }, lifecycleProvider)
    }
}
