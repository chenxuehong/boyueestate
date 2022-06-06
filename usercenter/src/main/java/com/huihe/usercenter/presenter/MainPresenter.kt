package com.huihe.usercenter.presenter

import com.eightbitlab.rxbus.Bus
import com.huihe.boyueentities.protocol.user.SetPushRep
import com.huihe.usercenter.presenter.view.MainView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.huihe.boyueentities.protocol.common.ServerVersionInfo
import com.kotlin.provider.event.PushIMEvent
import javax.inject.Inject

class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    @Inject
    lateinit var userService: UserService

    fun setPushInfo(uid: String?,  registrationId: String) {
        Bus.send(PushIMEvent())
        userService.setPushInfo(uid,registrationId)
            .execute(object : BaseSubscriber<SetPushRep?>(mView){
                override fun onNext(t: SetPushRep?) {
                    super.onNext(t)
                    mView?.onPushSuccess(t)
                }
            },lifecycleProvider)
    }

    fun getServerVersionInfo() {
        userService.getServerVersionInfo(BaseConstant.VERSION_INFO_URL)
            .execute(object : BaseSubscriber<ServerVersionInfo?>(mView){

                override fun onNext(t: ServerVersionInfo?) {
                    super.onNext(t)
                    mView?.onServerAppVersion(t)
                }
            },lifecycleProvider)
    }
}