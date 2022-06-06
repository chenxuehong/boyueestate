package com.huihe.customercenter.presenter

import com.huihe.customercenter.presenter.view.SetCustomerInfoView
import com.huihe.commonservice.service.customer.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class SetCustomerInfoPresenter @Inject constructor(): BasePresenter<SetCustomerInfoView>(){

    @Inject
    lateinit var service: CustomerService

    fun setCustomerStatus(id: String?, type: Int, value: String) {
        service.setCustomerStatus(
            id,
            type,
            value
        )
            .execute(object : BaseSubscriber<Any?>(mView) {
                override fun onError(e: Throwable) {
                    if (e is DataNullException) {
                        mView?.onSetCustomerStatusSuccess()
                    }
                }
            }, lifecycleProvider)
    }
}
