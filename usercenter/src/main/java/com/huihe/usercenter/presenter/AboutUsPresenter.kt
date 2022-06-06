package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.AboutUsView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.huihe.boyueentities.protocol.common.ServerVersionInfo
import javax.inject.Inject

class AboutUsPresenter @Inject constructor() : BasePresenter<AboutUsView>(){

    @Inject
    lateinit var service: UserService
    fun getServerVersionInfo() {
        service.getServerVersionInfo(BaseConstant.VERSION_INFO_URL)
            .execute(object : BaseSubscriber<ServerVersionInfo?>(mView){

                override fun onNext(t: ServerVersionInfo?) {
                    super.onNext(t)
                    mView?.onServerAppVersion(t)
                }
            },lifecycleProvider)
    }
}
