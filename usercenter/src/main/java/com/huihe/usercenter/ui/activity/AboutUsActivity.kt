package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.AboutUsFragment
import com.kotlin.base.ui.activity.BaseActivity

class AboutUsActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(AboutUsFragment())
    }
}
