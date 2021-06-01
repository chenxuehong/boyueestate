package com.huihe.boyueestate.ui.activity

import android.os.Bundle
import com.huihe.boyueestate.R
import com.huihe.boyueestate.injection.component.DaggerSplashComponent
import com.huihe.boyueestate.injection.module.SplashModule
import com.huihe.boyueestate.presenter.SplashPresenter
import com.huihe.boyueestate.presenter.view.SplashView
import com.huihe.usercenter.ui.activity.MainActivity
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.utils.UserPrefsUtils
import org.jetbrains.anko.startActivity

class SplashActivity : BaseMvpActivity<SplashPresenter>(),
    SplashView {

    override fun injectComponent() {
        DaggerSplashComponent.builder().activityComponent(mActivityComponent).splashModule(
            SplashModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var messageService = MessageService()
        setContentView(R.layout.activity_splash)
        mPresenter?.getSplashBanner()
        afterLogin {
            var userInfo = UserPrefsUtils.getUserInfo()
            if (userInfo!=null){
                messageService.login(
                    userInfo?.uid?:"",
                    userInfo?.userSig?:"",
                    object :MessageService.OnMessageListener{
                        override fun onLoginSuccess() {

                        }

                        override fun onLoginFail(message: String, code: Int) {
                        }

                    })
            }
            startActivity<MainActivity>()
        }
        finish()
    }

}
