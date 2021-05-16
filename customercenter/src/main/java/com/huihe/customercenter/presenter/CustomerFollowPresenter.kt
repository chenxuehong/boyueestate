package com.huihe.customercenter.presenter

import com.huihe.customercenter.data.protocol.CustomerFollowRep
import com.huihe.customercenter.presenter.view.CustomerFollowView
import com.huihe.customercenter.service.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CustomerFollowPresenter @Inject constructor() : BasePresenter<CustomerFollowView>() {

    @Inject
    lateinit var service: CustomerService

    fun getCustomerFollowList(page: Int = 0, limit: Int? = 30, customerCode: String?) {
        if (!checkNetWork()){
            return
        }
        service.getCustomerFollowList(
            page,
            limit,
            customerCode
        ).execute(object :BaseSubscriber<CustomerFollowRep?>(mView){
            override fun onNext(t: CustomerFollowRep?) {
                super.onNext(t)
                mView?.onCustomerFollowListResult(t?.list)
            }
        },lifecycleProvider)
    }

}
