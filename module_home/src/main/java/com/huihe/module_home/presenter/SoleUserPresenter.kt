package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.SoleUserRep
import com.huihe.boyueentities.protocol.home.SoleUserReq
import com.huihe.module_home.presenter.view.SoleUserView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class SoleUserPresenter @Inject constructor(): BasePresenter<SoleUserView>(){

    @Inject
    lateinit var service: HouseService

    fun postSole(req: SoleUserReq?) {
        mView?.showLoading()
        service.postSole(req)
            .execute(object : BaseSubscriber<SoleUserRep?>(mView){
                override fun onNext(t: SoleUserRep?) {
                    super.onNext(t)
                    mView?.onPostSoleResult(t)
                }
            },lifecycleProvider)
    }


}
