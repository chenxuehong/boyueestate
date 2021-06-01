package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.AddHouseInfoReq
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.presenter.view.AddHouseView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import com.kotlin.provider.data.protocol.District
import javax.inject.Inject

class AddHousePresenter @Inject constructor(): BasePresenter<AddHouseView>(){

    @Inject
    lateinit var service: HouseService

    fun addHouseInfo(req:AddHouseInfoReq){
        service.preCheckHouse(req)
            .execute(object :BaseSubscriber<String?>(mView){
                override fun onNext(t: String?) {
                    super.onNext(t)
                    service.addHouseInfo(req)
                        .execute(object :BaseSubscriber<SetHouseInfoRep?>(mView){
                            override fun onNext(t: SetHouseInfoRep?) {
                                super.onNext(t)
                                mView?.onAddHouseInfoSuccess(t)
                            }
                        },lifecycleProvider)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        service.addHouseInfo(req)
                            .execute(object :BaseSubscriber<SetHouseInfoRep?>(mView){
                                override fun onNext(t: SetHouseInfoRep?) {
                                    super.onNext(t)
                                    mView?.onAddHouseInfoSuccess(t)
                                }
                            },lifecycleProvider)
                    }
                }
            },lifecycleProvider)
    }
}
