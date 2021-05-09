package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.CustomerProfileInfo
import com.huihe.module_home.presenter.view.CustomerProfileView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber

import javax.inject.Inject

class CustomerProfilePresenter @Inject constructor(): BasePresenter<CustomerProfileView>(){

    @Inject
    lateinit var service: HouseService

    fun getCustomerProfile(id:String?){

        service?.getCustomerProfile(id)
            .execute(object :BaseSubscriber<CustomerProfileInfo?>(mView){
                override fun onNext(t: CustomerProfileInfo?) {
                    super.onNext(t)
                    mView.getCustomerProfileIResult(t)
                }
            },lifecycleProvider)
    }
}
