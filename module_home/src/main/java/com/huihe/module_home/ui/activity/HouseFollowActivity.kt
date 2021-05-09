package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.HouseFollowFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

class HouseFollowActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var house_code = extras.getString(HomeConstant.KEY_HOUSE_CODE)
        var id = extras.getString(HomeConstant.KEY_HOUSE_ID)
        var houseFollowFragment = HouseFollowFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_CODE,house_code)
        args.putString(HomeConstant.KEY_HOUSE_ID,id)
        houseFollowFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            houseFollowFragment
        ).commitAllowingStateLoss()
    }
}