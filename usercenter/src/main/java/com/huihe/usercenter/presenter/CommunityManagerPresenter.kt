package com.huihe.usercenter.presenter

import com.huihe.usercenter.data.protocol.District
import com.huihe.usercenter.presenter.view.CommunityManagerView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CommunityManagerPresenter @Inject constructor(): BasePresenter<CommunityManagerView>(){

    @Inject
    lateinit var service: UserService

    fun getVillages(
        latitude: Double?= null,
        longitude: Double?= null
    ) {
        if (!checkNetWork()) {
            return
        }
        mView?.showLoading()
        service?.getVillages(
            latitude,
            longitude
        )
            .execute(object : BaseSubscriber<MutableList<District>?>(mView) {
                override fun onNext(t: MutableList<District>?) {
                    mView.onGetAreaBeanListResult(t)
                }
            }, lifecycleProvider)

    }
}
