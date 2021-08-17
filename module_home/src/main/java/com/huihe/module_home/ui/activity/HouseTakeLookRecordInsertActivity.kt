package com.huihe.module_home.ui.activity

import android.os.Bundle

import com.huihe.module_home.ui.fragment.HouseTakeLookRecordInsertFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

class HouseTakeLookRecordInsertActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment(HouseTakeLookRecordInsertFragment(),intent.extras)
    }
}