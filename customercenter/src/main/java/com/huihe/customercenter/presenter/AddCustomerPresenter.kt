package com.huihe.customercenter.presenter

import com.huihe.customercenter.data.protocol.AddCustomerReq
import com.huihe.customercenter.data.protocol.CheckCustomerRep
import com.huihe.customercenter.presenter.view.AddCustomerView
import com.huihe.customercenter.service.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.jetbrains.anko.toast
import javax.inject.Inject

class AddCustomerPresenter @Inject constructor() : BasePresenter<AddCustomerView>() {

    @Inject
    lateinit var service: CustomerService

    fun addCustomer(req: AddCustomerReq) {

        service.checkCustomer(
            req.mobile
        )
            .execute(object : BaseSubscriber<CheckCustomerRep?>(mView) {

                override fun onNext(t: CheckCustomerRep?) {
                    super.onNext(t)
                    if (t?.isHave == 1) {
                        context.toast("组内中已存在咯")
                    }else{
                        service.addCustomer(req)
                            .execute(object : BaseSubscriber<Any?>(mView) {
                            override fun onError(e: Throwable) {
                                super.onError(e)
                                if (e is DataNullException) {
                                    mView?.onAddSuccess()
                                }
                            }
                        }, lifecycleProvider)
                    }
                }
            }, lifecycleProvider)
    }
}
