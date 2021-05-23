package com.huihe.boyueestate.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.huihe.boyueestate.R
import com.huihe.usercenter.ui.activity.LoginActivity
import com.huihe.usercenter.ui.activity.MainActivity
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.utils.UserPrefsUtils
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var messageService = MessageService()
        setContentView(R.layout.activity_splash)
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
            finish()
        }

    }

}
