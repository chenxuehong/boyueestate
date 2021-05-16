package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.HouseTakeLookRecordFragment
import com.huihe.module_home.ui.fragment.HouseTakeLookRecordInsertFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

class HouseTakeLookRecordInsertActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var code = extras.getString(HomeConstant.KEY_CODE)
        var houseTakeLookFragment = HouseTakeLookRecordInsertFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_CODE,code)
        houseTakeLookFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            houseTakeLookFragment
        ).commitAllowingStateLoss()
    }
}