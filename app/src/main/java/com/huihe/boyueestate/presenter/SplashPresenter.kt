package com.huihe.boyueestate.presenter

import com.huihe.boyueestate.presenter.view.SplashView
import com.huihe.usercenter.data.protocol.SplashBannerRep
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class SplashPresenter @Inject constructor() : BasePresenter<SplashView>(){

    @Inject
    lateinit var service: UserService

    fun getSplashBanner() {
        service.getSplashBanner()
            .execute(object :BaseSubscriber<SplashBannerRep?>(mView){

                override fun onNext(t: SplashBannerRep?) {
                    super.onNext(t)
                }
            },lifecycleProvider)
    }
}
