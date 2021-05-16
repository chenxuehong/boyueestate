package com.huihe.customercenter.presenter.view

import com.huihe.customercenter.data.protocol.CustomerTelLogRep
import com.kotlin.base.presenter.view.BaseView

interface CustomerTelLogView : BaseView {
    fun onCustomerPhoneLogListResult(list: MutableList<CustomerTelLogRep.CustomerTelLog>?)
}
