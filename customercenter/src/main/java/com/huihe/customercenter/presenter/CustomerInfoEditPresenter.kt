package com.huihe.customercenter.presenter

import com.huihe.boyueentities.protocol.customer.SetCustomersReq
import com.huihe.commonservice.service.customer.CustomerService
import com.huihe.customercenter.presenter.view.CustomerInfoEditView

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class CustomerInfoEditPresenter @Inject constructor() : BasePresenter<CustomerInfoEditView>() {

    @Inject
    lateinit var service: CustomerService

    fun setCustomerInfo(req: SetCustomersReq) {
        service.setCustomerInfo(req)
            .execute(object :BaseSubscriber<Any?>(mView){

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView?.onSetCustomerInfoSuccess()
                    }
                }
            },lifecycleProvider)
    }
}
