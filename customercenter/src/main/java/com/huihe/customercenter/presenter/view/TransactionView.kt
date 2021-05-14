package com.huihe.customercenter.presenter.view

import com.huihe.customercenter.data.protocol.CustomerRep
import com.huihe.customercenter.data.protocol.DeptUserRep
import com.huihe.customercenter.data.protocol.StatusRep
import com.kotlin.base.presenter.view.BaseView

interface TransactionView :BaseView {
    fun onCustomerListResult(list: MutableList<CustomerRep.Customer>?)
    fun onDeptUserListResult(list: MutableList<DeptUserRep>?)
    fun onCustomerStatusListResult(t: MutableList<StatusRep>?)
}