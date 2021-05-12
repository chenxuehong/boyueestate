package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.HouseNearFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

class HouseNearActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var data = extras.getString(HomeConstant.KEY_MAP_BEAN)
        var fragment = HouseNearFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_MAP_BEAN,data)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            fragment
        ).commitAllowingStateLoss()
    }
}