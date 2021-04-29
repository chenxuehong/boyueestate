package com.huihe.boyueestate.ui.activity

import android.os.Bundle
import com.huihe.boyueestate.R
import com.huihe.usercenter.ui.activity.LoginActivity
import com.huihe.usercenter.ui.activity.MainActivity
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.common.afterLogin
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        contentView.postDelayed({
            afterLogin {
                startActivity<MainActivity>()
            }
            finish()
        },1500)
    }

}