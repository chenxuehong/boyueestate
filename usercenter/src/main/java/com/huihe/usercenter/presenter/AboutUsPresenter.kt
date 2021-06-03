package com.huihe.usercenter.presenter

import com.huihe.usercenter.data.protocol.SystemConfigRep
import com.huihe.usercenter.presenter.view.AboutUsView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.provider.constant.UserConstant
import javax.inject.Inject

class AboutUsPresenter @Inject constructor() : BasePresenter<AboutUsView>(){

    @Inject
    lateinit var service: UserService
    fun getServerVersionInfo() {
        service.getSystemConfig()
            .execute(object : BaseSubscriber<SystemConfigRep?>(mView){

                override fun onNext(t: SystemConfigRep?) {
                    super.onNext(t)
                    t?.list?.apply {
                        mView?.onServerAppVersion(getServerVersion(this, UserConstant.KEY_ANDROID_VERSION))
                    }
                }
            },lifecycleProvider)
    }

    private fun getServerVersion(list: MutableList<SystemConfigRep.SystemConfigBean>, androidVersion: String): String {

        list.forEach { item->
            if (item.configKey == androidVersion){
                return item.configValue?:""
            }
        }
        return ""
    }
}
