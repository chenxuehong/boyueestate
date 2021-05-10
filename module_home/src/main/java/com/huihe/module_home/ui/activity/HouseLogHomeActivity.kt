package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.HouseLogHomeFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

class HouseLogHomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var code = extras.getString(HomeConstant.KEY_HOUSE_CODE)
        var fragment = HouseLogHomeFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_CODE, code)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            fragment
        ).commitAllowingStateLoss()
    }
}