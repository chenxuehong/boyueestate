package com.huihe.customercenter.presenter.view

import com.huihe.customercenter.data.protocol.CustomerLogRep
import com.kotlin.base.presenter.view.BaseView

interface CustomerLogView : BaseView {
    fun onCustomerLogListResult(list: MutableList<CustomerLogRep.CustomerLog>?)
}
