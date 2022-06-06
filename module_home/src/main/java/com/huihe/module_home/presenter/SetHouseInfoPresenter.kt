package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.SetHouseInfoRep
import com.huihe.boyueentities.protocol.home.SetHouseInfoReq
import com.huihe.module_home.presenter.view.SetHouseInfoView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class SetHouseInfoPresenter @Inject constructor() : BasePresenter<SetHouseInfoView>(){
    @Inject
    lateinit var service: HouseService

    fun putHouseInfo(req: SetHouseInfoReq){
        mView?.showLoading()
        service?.putHouseInfo(req)
            .execute(object : BaseSubscriber<SetHouseInfoRep?>(mView) {
                override fun onNext(t: SetHouseInfoRep?) {
                    mView.onSetHouseInfoSuccess(t)
                }
            }, lifecycleProvider)
    }
}