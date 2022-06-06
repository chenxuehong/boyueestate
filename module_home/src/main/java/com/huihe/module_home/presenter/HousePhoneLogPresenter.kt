package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.HouseLogRep
import com.huihe.module_home.presenter.view.HousePhoneLogView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HousePhoneLogPresenter @Inject constructor() : BasePresenter<HousePhoneLogView>(){

    @Inject
    lateinit var service: HouseService

    fun getHousePhoneLog(page: Int, size: Int, houseCode: String?) {

        if (!checkNetWork()){
            return
        }
        service.getHousePhoneLog(page,size,houseCode)
            .execute(object :BaseSubscriber<HouseLogRep?>(mView){
                override fun onNext(t: HouseLogRep?) {
                    super.onNext(t)
                    mView?.getLogListResult(t?.list,t?.totalCount)
                }
            },lifecycleProvider)
    }

}
