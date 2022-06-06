package com.huihe.customercenter.presenter

import com.huihe.boyueentities.protocol.customer.CustomerLogRep
import com.huihe.customercenter.presenter.view.CustomerLogView
import com.huihe.commonservice.service.customer.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CustomerLogPresenter @Inject constructor(): BasePresenter<CustomerLogView>(){

    @Inject
    lateinit var service: CustomerService

    fun getCustomerLogList(page: Int, limit: Int, customerCode: String?) {
        if (!checkNetWork()){
            return
        }
        service.getCustomerLogList(page,limit,customerCode)
            .execute(object :BaseSubscriber<CustomerLogRep?>(mView){

                override fun onNext(t: CustomerLogRep?) {
                    super.onNext(t)
                    mView?.onCustomerLogListResult(t?.list)
                }
            },lifecycleProvider)
    }
}
