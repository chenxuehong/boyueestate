package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.SettingView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class SettingPresenter @Inject constructor() : BasePresenter<SettingView>(){

    @Inject
    lateinit var service: UserService

    fun logout() {
        service.logout()
            .execute(object :BaseSubscriber<Any>(mView){
                override fun onError(e: Throwable) {
                    super.onError(e)
                    if(e is DataNullException){
                        mView?.onLogout()
                    }
                }
            },lifecycleProvider)
    }

}
