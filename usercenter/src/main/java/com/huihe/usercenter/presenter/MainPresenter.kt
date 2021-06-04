package com.huihe.usercenter.presenter

import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.data.protocol.SetPushRep
import com.huihe.usercenter.presenter.view.MainView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
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
}