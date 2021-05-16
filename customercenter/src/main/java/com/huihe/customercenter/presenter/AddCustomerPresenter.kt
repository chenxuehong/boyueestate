package com.huihe.customercenter.presenter

import com.huihe.customercenter.data.protocol.AddCustomerReq
import com.huihe.customercenter.presenter.view.AddCustomerView
import com.huihe.customercenter.service.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class AddCustomerPresenter @Inject constructor(): BasePresenter<AddCustomerView>(){

    @Inject
    lateinit var service: CustomerService

    fun addCustomer(req: AddCustomerReq) {

        service.addCustomer(
            req
        ).execute(object :BaseSubscriber<Any?>(mView){
            override fun onError(e: Throwable) {
                super.onError(e)
                if (e is DataNullException){
                    mView?.onAddSuccess()
                }
            }
        },lifecycleProvider)
    }
}
