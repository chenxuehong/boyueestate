package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.module_home.ui.fragment.HouseTakeLookRecordFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.HomeCenter.PATH_TAKELOOK_RECORD)
class HouseTakeLookRecordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var code = extras.getString(HomeConstant.KEY_CODE)
        var isAdd = extras.getBoolean(HomeConstant.KEY_IS_ADD)
        var houseTakeLookFragment = HouseTakeLookRecordFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_CODE,code)
        args.putBoolean(HomeConstant.KEY_IS_ADD,isAdd)
        houseTakeLookFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            houseTakeLookFragment
        ).commitAllowingStateLoss()
    }
}