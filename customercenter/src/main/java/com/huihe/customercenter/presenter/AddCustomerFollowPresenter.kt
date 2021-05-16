package com.huihe.customercenter.presenter

import com.huihe.customercenter.presenter.view.AddCustomerFollowView
import com.huihe.customercenter.service.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class AddCustomerFollowPresenter @Inject constructor(): BasePresenter<AddCustomerFollowView>(){

    @Inject
    lateinit var service: CustomerService

    fun addFollowContent(customerCode: String?, followUpContent: String) {

        service.addFollowContent(
            customerCode,
            followUpContent
        ).execute(object :BaseSubscriber<Any?>(mView){
            override fun onError(e: Throwable) {
                if (e is DataNullException){
                    mView?.onAddFollowSuccess()
                }
            }
        },lifecycleProvider)
    }
}
