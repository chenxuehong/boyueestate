package com.huihe.customercenter.presenter

import com.huihe.boyueentities.protocol.customer.CustomerTelLogRep
import com.huihe.customercenter.presenter.view.CustomerTelLogView
import com.huihe.commonservice.service.customer.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CustomerTelLogPresenter @Inject constructor(): BasePresenter<CustomerTelLogView>(){

    @Inject
    lateinit var service: CustomerService

    fun getCustomerTelLogList(page: Int, pageSize: Int, customerCode: String?) {
       if (!checkNetWork()){
           return
       }
        service.getCustomerTelLogList(page,pageSize,customerCode)
            .execute(object :BaseSubscriber<CustomerTelLogRep?>(mView){
                override fun onNext(t: CustomerTelLogRep?) {
                    super.onNext(t)
                    mView?.onCustomerPhoneLogListResult(t?.list)
                }
            },lifecycleProvider)
    }

}
