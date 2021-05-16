package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.UserInfo
import com.kotlin.base.presenter.view.BaseView

interface DeptInfoView : BaseView {
    fun onUserInfo(t: UserInfo?)
}
