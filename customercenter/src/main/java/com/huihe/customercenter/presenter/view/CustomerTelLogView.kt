package com.huihe.customercenter.presenter.view

import com.huihe.boyueentities.protocol.customer.CustomerTelLogRep
import com.kotlin.base.presenter.view.BaseView

interface CustomerTelLogView : BaseView {
    fun onCustomerPhoneLogListResult(list: MutableList<CustomerTelLogRep.CustomerTelLog>?)
}
