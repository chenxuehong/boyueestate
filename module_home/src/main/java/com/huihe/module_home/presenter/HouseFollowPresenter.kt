package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.FollowRep
import com.huihe.module_home.presenter.view.HouseFollowView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HouseFollowPresenter @Inject constructor() : BasePresenter<HouseFollowView>() {
    @Inject
    lateinit var service: HouseService

    fun getHouseFollowList(
        pageNo: Int? = 1,
        pageSize: Int? = 30,
        houseCode: String?
    ) {
        if (!checkNetWork()) {
            return
        }
        service?.getHouseFollowList(pageNo,pageSize,houseCode)
            .execute(object :BaseSubscriber<FollowRep?>(mView){
                override fun onNext(t: FollowRep?) {
                    super.onNext(t)
                    mView?.getFollowRepResult(t)
                }
            },lifecycleProvider)
    }
}