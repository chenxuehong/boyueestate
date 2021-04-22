package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.LoginView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor():BasePresenter<LoginView>(){

    @Inject
    lateinit var userService: UserService

    /*
      登录
   */
    fun login(mobile: String, pwd: String, pushId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

    }
}
