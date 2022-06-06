package com.huihe.customercenter.presenter

import com.huihe.boyueentities.protocol.customer.CustomerRep
import com.huihe.boyueentities.protocol.customer.CustomerReq
import com.huihe.boyueentities.protocol.customer.DeptUserRep
import com.huihe.boyueentities.protocol.customer.StatusRep
import com.huihe.customercenter.presenter.view.TransactionView
import com.huihe.commonservice.service.customer.CustomerService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CustomerListPresenter @Inject constructor():BasePresenter<TransactionView>(){

    @Inject
    lateinit var service: CustomerService

    fun getCustomerList(customerReq: CustomerReq) {
        if (!checkNetWork()){
            return
        }
        service.getCustomerList(
            customerReq
        ).execute(object :BaseSubscriber<CustomerRep?>(mView){
            override fun onNext(t: CustomerRep?) {
                super.onNext(t)
                mView?.onCustomerListResult(t?.list)
            }
        },lifecycleProvider)
    }

    fun getDeptUserList() {
        service.getDeptUserList()
            .execute(object :BaseSubscriber<MutableList<DeptUserRep>?>(mView){
                override fun onNext(t: MutableList<DeptUserRep>?) {
                    super.onNext(t)

                    if (t?.size!! >0){
                        mView?.onDeptUserListResult(t)
                    }else{
                        mView?.onDataIsNull()
                    }

                }
            },lifecycleProvider)
    }

    fun getCustomerStatusList(dataType: String) {
        service.getCustomerStatusList(dataType)
            .execute(object :BaseSubscriber<MutableList<StatusRep>?>(mView){
                override fun onNext(t: MutableList<StatusRep>?) {
                    super.onNext(t)
                    mView?.onCustomerStatusListResult(t)
                }
            },lifecycleProvider)

    }
}