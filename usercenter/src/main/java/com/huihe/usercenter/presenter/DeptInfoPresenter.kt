package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.UserInfo
import com.huihe.usercenter.presenter.view.DeptInfoView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class DeptInfoPresenter @Inject constructor(): BasePresenter<DeptInfoView>(){

    @Inject
    lateinit var service: UserService

    fun getUserInfo(id: String?) {
        service.getUserInfo(id)
            .execute(object :BaseSubscriber<UserInfo?>(mView){
                override fun onNext(t: UserInfo?) {
                    super.onNext(t)
                    mView?.onUserInfo(t)
                }
            },lifecycleProvider)
    }
}
