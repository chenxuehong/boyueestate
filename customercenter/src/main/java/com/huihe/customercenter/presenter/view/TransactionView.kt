package com.huihe.customercenter.presenter.view

import com.huihe.boyueentities.protocol.customer.CustomerRep
import com.huihe.boyueentities.protocol.customer.DeptUserRep
import com.huihe.boyueentities.protocol.customer.StatusRep
import com.kotlin.base.presenter.view.BaseView

interface TransactionView :BaseView {
    fun onCustomerListResult(list: MutableList<CustomerRep.Customer>?)
    fun onDeptUserListResult(list: MutableList<DeptUserRep>?)
    fun onCustomerStatusListResult(t: MutableList<StatusRep>?)
}