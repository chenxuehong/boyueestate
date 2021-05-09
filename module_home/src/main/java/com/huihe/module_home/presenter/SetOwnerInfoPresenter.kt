package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.data.protocol.SetHouseInfoReq
import com.huihe.module_home.presenter.view.SetOwnerInfoView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class SetOwnerInfoPresenter @Inject constructor() : BasePresenter<SetOwnerInfoView>(){

    @Inject
    lateinit var mHouseService: HouseService

    fun setHouseInfo(
        id: String?,
        ownerTel: String?=null,
        ownerName: String?=null
    ) {
        mView?.showLoading("正在修改")
        mHouseService.setHouseInfo(SetHouseInfoReq(id,ownerTel=ownerTel,ownerName=ownerName))
            .execute(object : BaseSubscriber<SetHouseInfoRep?>(mView) {
                override fun onNext(t: SetHouseInfoRep?) {
                    super.onNext(t)
                    mView?.onHouseInfo(t)
                }
            }, lifecycleProvider)
    }
}