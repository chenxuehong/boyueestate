package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.UserInfo
import com.kotlin.base.presenter.view.BaseView

interface LoginView : BaseView{
    fun onLoginResult(result: UserInfo)
}
