package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.EntrustUserRep
import com.huihe.module_home.data.protocol.EntrustUserReq
import com.huihe.module_home.presenter.view.EntrustUserView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class EntrustUserPresenter @Inject constructor(): BasePresenter<EntrustUserView>() {

    @Inject
    lateinit var service: HouseService

    fun putHouseEntrust(req: EntrustUserReq) {

        mView.showLoading()
        service.putHouseEntrust(req)
            .execute(object :BaseSubscriber<EntrustUserRep?>(mView){

                override fun onNext(t: EntrustUserRep?) {
                    super.onNext(t)
                    mView?.onPutEntrustUserSuccess(t)
                }
            },lifecycleProvider)
    }

    fun getHouseEntrust(houseId: String) {
        mView.showLoading()
        service.getHouseEntrust(houseId)
            .execute(object :BaseSubscriber<EntrustUserRep?>(mView){

                override fun onNext(t: EntrustUserRep?) {
                    super.onNext(t)
                    mView?.onEntrustUserSuccess(t)
                }
            },lifecycleProvider)
    }
}