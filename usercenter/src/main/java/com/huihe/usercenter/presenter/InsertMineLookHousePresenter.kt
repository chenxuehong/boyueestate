package com.huihe.usercenter.presenter

import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.MineLookHouseReq
import com.huihe.usercenter.presenter.view.InsertMineLookHouseView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class InsertMineLookHousePresenter @Inject constructor(): BasePresenter<InsertMineLookHouseView>() {

    @Inject
    lateinit var service: UserService

    fun insertMineLookHouse(req: MineLookHouseReq) {
        mView?.showLoading(context.resources.getString(R.string.committing))
        service.insertMineLookHouse(req)
            .execute(object : BaseSubscriber<String>(mView) {
                override fun onNext(t: String) {
                    super.onNext(t)
                    mView?.onInsertSuccess()
                }
            }, lifecycleProvider)
    }

}