package com.huihe.usercenter.presenter

import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.presenter.view.MeView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class MePresenter @Inject constructor(): BasePresenter<MeView>(){

    @Inject
    lateinit var service: UserService

    fun getUserInfo() {
        service.getUserInfoFormIm()
            .execute(object :BaseSubscriber<UserInfo?>(mView){
                override fun onNext(t: UserInfo?) {
                    super.onNext(t)
                    mView?.onUserInfo(t)
                }
            },lifecycleProvider)
    }
}
