package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.CorporateCultureRep
import com.huihe.usercenter.presenter.view.CorporateCultureView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CorporateCulturePresenter @Inject constructor() : BasePresenter<CorporateCultureView>(){

    @Inject
    lateinit var service: UserService

    fun getMobTechList(page: Int, pageSize: Int) {
        if (!checkNetWork()){
            return
        }
        service.getMobTechList(page,pageSize)
            .execute(object :BaseSubscriber<CorporateCultureRep?>(mView){
                override fun onNext(t: CorporateCultureRep?) {
                    super.onNext(t)
                    mView?.onMobTechList(t?.list)
                }
            },lifecycleProvider)
    }
}
