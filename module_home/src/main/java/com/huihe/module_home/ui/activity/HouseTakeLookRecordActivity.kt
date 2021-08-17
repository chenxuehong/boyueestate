package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.module_home.ui.fragment.HouseTakeLookRecordFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.HomeCenter.PATH_TAKELOOK_RECORD)
class HouseTakeLookRecordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var houseTakeLookFragment = HouseTakeLookRecordFragment()
        setFragment(houseTakeLookFragment,intent.extras)
    }
}