package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.MineLookTaskDetailFragment
import com.kotlin.base.ui.activity.BaseActivity

class MineLookTaskDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(MineLookTaskDetailFragment(),intent.extras)
    }
}