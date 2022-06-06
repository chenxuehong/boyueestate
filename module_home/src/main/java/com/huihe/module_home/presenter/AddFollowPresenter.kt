package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.FollowRep
import com.huihe.module_home.presenter.view.AddFollowView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class AddFollowPresenter @Inject constructor(): BasePresenter<AddFollowView>(){

    @Inject
    lateinit var service: HouseService

    fun addFollowContent(houseId:String?,followContent:String?){
        service.addFollowContent(
            houseId,
            followContent
        ).execute(object :BaseSubscriber<FollowRep.FollowBean?>(mView){
            override fun onNext(t: FollowRep.FollowBean?) {
                super.onNext(t)
                mView?.onAddFollowResult(t)
            }
        },lifecycleProvider)
    }
}
