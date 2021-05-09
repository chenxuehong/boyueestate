package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.huihe.module_home.presenter.view.HouseTakeLookView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HouseTakeLookPresenter @Inject constructor(): BasePresenter<HouseTakeLookView>() {
    @Inject
    lateinit var service: HouseService

    fun getTakeLookRecord(page:Int?=1,limit:Int?=30,code:String?){
        if (!checkNetWork()){
            return
        }
        service.getTakeLookRecord(
            page,
            limit,
            code
        ).execute(object :BaseSubscriber<HouseTakeLookRep?>(mView){
            override fun onNext(t: HouseTakeLookRep?) {
                super.onNext(t)
                mView?.onTakeLookRecordResult(t)
            }
        },lifecycleProvider)
    }
}