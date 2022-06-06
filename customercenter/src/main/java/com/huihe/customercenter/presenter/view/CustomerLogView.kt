package com.huihe.customercenter.presenter.view

import com.huihe.boyueentities.protocol.customer.CustomerLogRep
import com.kotlin.base.presenter.view.BaseView

interface CustomerLogView : BaseView {
    fun onCustomerLogListResult(list: MutableList<CustomerLogRep.CustomerLog>?)
}
