package com.huihe.ebai.presenter

import com.huihe.ebai.presenter.view.SplashView
import com.huihe.usercenter.data.protocol.SystemConfigRep
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.provider.constant.UserConstant
import javax.inject.Inject

class SplashPresenter @Inject constructor() : BasePresenter<SplashView>(){

    @Inject
    lateinit var service: UserService

    fun getSplashBanner() {
        service.getSystemConfig()
            .execute(object :BaseSubscriber<SystemConfigRep?>(mView){

                override fun onNext(t: SystemConfigRep?) {
                    super.onNext(t)
                    t?.list?.apply {
                        mView?.onBanner(getBanner(this,UserConstant.KEY_MODILE_COVEIMAGE))
                    }
                }
            },lifecycleProvider)
    }

    private fun getBanner(
        list: MutableList<SystemConfigRep.SystemConfigBean>,
        keyModileCoveimage: String
    ) :String {
        list.forEach {item->
            if (keyModileCoveimage == item.configKey){
                return item.configValue?:""
            }
        }
        return ""
    }
}
