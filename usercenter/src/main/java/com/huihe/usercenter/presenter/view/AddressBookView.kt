package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.DeptUserRep
import com.kotlin.base.presenter.view.BaseView

interface AddressBookView:BaseView {
    fun onDeptUsersResult(t: MutableList<DeptUserRep>?)
}
