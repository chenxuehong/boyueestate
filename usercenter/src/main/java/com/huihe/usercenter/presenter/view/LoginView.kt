package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView

interface LoginView : BaseView{
    fun onLoginResult(result: String)
}
