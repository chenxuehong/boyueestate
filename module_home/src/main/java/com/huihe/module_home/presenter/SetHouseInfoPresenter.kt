package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.AreaBeanRep
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.data.protocol.SetHouseInfoReq
import com.huihe.module_home.presenter.view.SetHouseInfoView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class SetHouseInfoPresenter @Inject constructor() : BasePresenter<SetHouseInfoView>(){
    @Inject
    lateinit var service: HouseService

    fun getVillages(){
        mView?.showLoading()
        service?.getVillages()
            .execute(object : BaseSubscriber<AreaBeanRep?>(mView) {
                override fun onNext(t: AreaBeanRep?) {
                    mView.onGetAreaBeanListResult(t?.list)
                }
            }, lifecycleProvider)
    }

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