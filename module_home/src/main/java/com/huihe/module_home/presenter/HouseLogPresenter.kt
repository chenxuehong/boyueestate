package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.HouseLogRep
import com.huihe.module_home.presenter.view.HouseLogView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HouseLogPresenter @Inject constructor(): BasePresenter<HouseLogView>() {

    @Inject
    lateinit var service: HouseService

    fun getHouseLog(page: Int, size: Int, houseCode: String?) {
        if (!checkNetWork()){
            return
        }
        service.getHouseLog(page,size,houseCode)
            .execute(object :BaseSubscriber<HouseLogRep?>(mView){
                override fun onNext(t: HouseLogRep?) {
                    super.onNext(t)
                    mView?.onLogListResult(t?.list,t?.totalCount)
                }
            },lifecycleProvider)

    }
}