package com.huihe.customercenter.presenter

import com.huihe.customercenter.data.protocol.CustomerDetailRep
import com.huihe.customercenter.data.protocol.CustomerMobileRep
import com.huihe.customercenter.presenter.view.CustomerDetailView
import com.huihe.customercenter.service.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException

import javax.inject.Inject

class CustomerDetailPresenter @Inject constructor() : BasePresenter<CustomerDetailView>() {

    @Inject
    lateinit var service: CustomerService

    fun getCustomerDetail(id: String?) {
        if (!checkNetWork()) {
            return
        }
        service.getCustomerDetail(id)
            .execute(object : BaseSubscriber<CustomerDetailRep?>(mView) {
                override fun onNext(t: CustomerDetailRep?) {
                    super.onNext(t)
                    mView?.onCustomerDetailResult(t)
                }
            }, lifecycleProvider)
    }

    fun reqCollection(id: String?) {
        service.reqCollection(id)
            .execute(object : BaseSubscriber<Any?>(mView) {
                override fun onError(e: Throwable) {
                    if (e is DataNullException) {
                        mView?.onReqCollectionResult(true)
                    }
                }
            }, lifecycleProvider)
    }

    fun reqDeleteCollection(id: String?) {
        service.reqDeleteCollection(id)
            .execute(object : BaseSubscriber<Any?>(mView) {
                override fun onError(e: Throwable) {
                    if (e is DataNullException) {
                        mView?.onReqCollectionResult(false)
                    }
                }
            }, lifecycleProvider)
    }

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

    fun getCustomerMobile(id: String?) {
        service.getCustomerMobile(id)
            .execute(object :BaseSubscriber<CustomerMobileRep?>(mView){

                override fun onNext(t: CustomerMobileRep?) {
                    super.onNext(t)
                    mView?.onCustomerMobile(t?.mobile?:"")
                }
            },lifecycleProvider)
    }
}
