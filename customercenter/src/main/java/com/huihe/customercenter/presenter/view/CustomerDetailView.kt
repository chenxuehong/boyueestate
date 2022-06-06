package com.huihe.customercenter.presenter.view

import com.huihe.boyueentities.protocol.customer.CustomerDetailRep
import com.kotlin.base.presenter.view.BaseView

interface CustomerDetailView :BaseView {
    fun onCustomerDetailResult(t: CustomerDetailRep?)
    fun onReqCollectionResult(isCollection: Boolean)
    fun onSetCustomerStatusSuccess()
    fun onCustomerMobile(s: String)
}
