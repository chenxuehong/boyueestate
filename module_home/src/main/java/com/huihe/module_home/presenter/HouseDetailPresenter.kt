package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HouseDetailPresenter @Inject constructor() : BasePresenter<HouseDetailView>(){

    @Inject
    lateinit var mHouseService: HouseService

    fun getHouseDetailById(id:String?){
        if (!checkNetWork()) {
            return
        }
        mHouseService.getHouseDetailById(id)
            .execute(object :BaseSubscriber<HouseDetail?>(mView){
                override fun onNext(t: HouseDetail?) {
                    super.onNext(t)
                    var house = t?.data
                   mView?.onGetHouseDetailResult(house)
                }
            },lifecycleProvider)
    }

}