package com.huihe.usercenter.presenter

import com.huihe.usercenter.R
import com.huihe.usercenter.presenter.view.LoginView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class LoginPresenter @Inject constructor():BasePresenter<LoginView>(){

    @Inject
    lateinit var userService: UserService

    /*
      登录
   */
    fun login(account: String, password: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading(context.resources.getString(R.string.srl_header_loading))
        userService.login(account,password).execute(object : BaseSubscriber<String>(mView){

            override fun onNext(t: String) {
                mView.onLoginResult(t)
            }
        },lifecycleProvider)
    }
}
