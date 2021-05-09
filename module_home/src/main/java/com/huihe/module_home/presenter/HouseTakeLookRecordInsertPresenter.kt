package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.huihe.module_home.presenter.view.HouseTakeLookRecordInsertView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HouseTakeLookRecordInsertPresenter @Inject constructor(): BasePresenter<HouseTakeLookRecordInsertView>(){

    @Inject
    lateinit var service: HouseService

    fun addHouseTakeLookRecord(houseCodeList:MutableList<String>?=null,evaluate:String?=null){
        service?.addTakeLookRecord(
            houseCodeList= houseCodeList,
            evaluate =  evaluate
        ).execute(object :BaseSubscriber<HouseTakeLookRep.HouseTakeLook?>(mView){
            override fun onNext(t: HouseTakeLookRep.HouseTakeLook?) {
                super.onNext(t)
                mView?.onAddHouseTakeLookResult(t)
            }
        },lifecycleProvider)
    }
}
