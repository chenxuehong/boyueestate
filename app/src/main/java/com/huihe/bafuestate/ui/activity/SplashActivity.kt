package com.huihe.bafuestate.ui.activity

import android.os.Bundle
import com.huihe.bafuestate.R
import com.huihe.bafuestate.injection.component.DaggerSplashComponent
import com.huihe.bafuestate.injection.module.SplashModule
import com.huihe.bafuestate.presenter.SplashPresenter
import com.huihe.bafuestate.presenter.view.SplashView
import com.huihe.usercenter.ui.activity.MainActivity
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.GlideUtils
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_splash.*
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
        ivSplashBanner.postDelayed(
            {
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
            },2000
        )

    }

    override fun onBanner(banner: String) {
        GlideUtils.loadImage(this,banner,ivSplashBanner)
    }

}