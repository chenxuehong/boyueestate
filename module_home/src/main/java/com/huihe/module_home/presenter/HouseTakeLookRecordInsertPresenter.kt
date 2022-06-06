package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.AddTakeLookRecordReq
import com.huihe.module_home.presenter.view.HouseTakeLookRecordInsertView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class HouseTakeLookRecordInsertPresenter @Inject constructor(): BasePresenter<HouseTakeLookRecordInsertView>(){

    @Inject
    lateinit var service: HouseService

    fun addHouseTakeLookRecord(req: AddTakeLookRecordReq?){
        service?.addTakeLookRecord(req).execute(object :BaseSubscriber<Any?>(mView){
            override fun onNext(t: Any?) {
                super.onNext(t)
                mView?.onAddHouseTakeLookResult()
            }
            override fun onError(e: Throwable) {
                super.onError(e)
                if (e is DataNullException){
                    mView?.onAddHouseTakeLookResult()
                }
            }
        },lifecycleProvider)
    }
}
