package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.CommunityManagerFragment

import com.kotlin.base.ui.activity.BaseActivity

class CommunityManagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(CommunityManagerFragment())
    }
}
