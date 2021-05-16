package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.SettingFragment
import com.kotlin.base.ui.activity.BaseActivity

class SettingActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(SettingFragment())
    }
}
