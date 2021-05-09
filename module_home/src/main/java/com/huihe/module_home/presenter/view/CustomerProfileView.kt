package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.CustomerProfileInfo
import com.kotlin.base.presenter.view.BaseView

interface CustomerProfileView : BaseView{
    fun getCustomerProfileIResult(customerProfileInfo: CustomerProfileInfo?)
}
