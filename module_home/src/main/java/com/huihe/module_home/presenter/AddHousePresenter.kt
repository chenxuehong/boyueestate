package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.AddHouseInfoReq
import com.huihe.module_home.data.protocol.District
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.presenter.view.AddHouseView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class AddHousePresenter @Inject constructor(): BasePresenter<AddHouseView>(){

    @Inject
    lateinit var service: HouseService

    fun addHouseInfo(req:AddHouseInfoReq){
        service.addHouseInfo(req)
            .execute(object :BaseSubscriber<SetHouseInfoRep?>(mView){
                override fun onNext(t: SetHouseInfoRep?) {
                    super.onNext(t)
                    mView?.onAddHouseInfoSuccess(t)
                }
            },lifecycleProvider)
    }

    fun getVillages(){
        mView?.showLoading()
        service?.getVillages()
            .execute(object : BaseSubscriber<MutableList<District>?>(mView) {
                override fun onNext(t: MutableList<District>?) {
                    mView.onGetAreaBeanListResult(t)
                }
            }, lifecycleProvider)
    }
}
