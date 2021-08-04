package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.MineLookTaskHomeFragment
import com.kotlin.base.ui.activity.BaseActivity

class MineLookTaskHomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(MineLookTaskHomeFragment(),intent.extras)
    }
}