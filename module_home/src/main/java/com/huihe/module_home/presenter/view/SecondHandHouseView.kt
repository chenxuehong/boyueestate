package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.Customer
import com.kotlin.base.presenter.view.BaseView

interface SecondHandHouseView : BaseView {

    fun onGetHouseListResult(customers:MutableList<Customer>?)
}
