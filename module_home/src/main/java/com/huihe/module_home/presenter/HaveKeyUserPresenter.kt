package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.HaveKeyUserRep
import com.huihe.boyueentities.protocol.home.HaveKeyUserReq
import com.huihe.module_home.presenter.view.HaveKeyUserView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HaveKeyUserPresenter @Inject constructor() : BasePresenter<HaveKeyUserView>() {

    @Inject
    lateinit var mHouseService: HouseService

    fun getUploadToken() {
        mView?.showLoading("正在上传...")
        mHouseService.getUploadToken()
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onGetUploadTokenResult(t)
                }
            }, lifecycleProvider)
    }

    fun putHouseKey(req: HaveKeyUserReq) {
        mHouseService.putHouseKey(req)
            .execute(object : BaseSubscriber<HaveKeyUserRep?>(mView) {
                override fun onNext(t: HaveKeyUserRep?) {
                    super.onNext(t)
                    mView?.onPutHouseKeySuccess(t)
                }
            }, lifecycleProvider)
    }

}