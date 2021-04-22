package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.component.UserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.LoginPresenter
import com.huihe.usercenter.presenter.view.LoginView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>() , LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    override fun injectComponent() {

        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    private fun initView() {

    }

    override fun onLoginResult(result: UserInfo) {

    }
}
