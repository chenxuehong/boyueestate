package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.SetHouseInfoRep
import com.huihe.boyueentities.protocol.home.SetHouseInfoReq
import com.huihe.module_home.presenter.view.SetOwnerInfoView
import com.huihe.commonservice.service.house.HouseService
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

    fun getHouseMobile(id: String?) {
        mHouseService.getHouseMobile(id)
            .execute(object : BaseSubscriber<String?>(mView){
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView.onMobile(t)
                }
                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView.onMobile("")
                }
            },lifecycleProvider)
    }
}