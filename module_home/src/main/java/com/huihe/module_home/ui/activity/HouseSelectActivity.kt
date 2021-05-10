package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.HouseFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

class HouseSelectActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var houseFragment = HouseFragment()
        val args = Bundle()
        args.putBoolean(HomeConstant.KEY_IS_HOUSE_SELECT,true)
        houseFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            houseFragment
        ).commitAllowingStateLoss()
    }
}
