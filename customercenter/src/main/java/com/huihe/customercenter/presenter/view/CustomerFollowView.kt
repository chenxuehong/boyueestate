package com.huihe.customercenter.presenter.view

import com.huihe.customercenter.data.protocol.CustomerFollowRep
import com.kotlin.base.presenter.view.BaseView

interface CustomerFollowView:BaseView {
    fun onCustomerFollowListResult(list: MutableList<CustomerFollowRep.CustomerFollow>?)
}
