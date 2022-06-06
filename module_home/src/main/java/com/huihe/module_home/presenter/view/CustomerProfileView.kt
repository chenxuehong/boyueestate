package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.CustomerProfileInfo
import com.kotlin.base.presenter.view.BaseView

interface CustomerProfileView : BaseView{
    fun getCustomerProfileIResult(customerProfileInfo: CustomerProfileInfo?)
}
