package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.ResultCommunityFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.UserConstant

class ResultCommunityActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        setFragment(ResultCommunityFragment(),extras)
    }
}