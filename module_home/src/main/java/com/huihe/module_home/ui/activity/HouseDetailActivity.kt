package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.R
import com.huihe.module_home.ui.fragment.HouseDetailFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant

/**
 * @desc 房源详情
 */
class HouseDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var id = extras.getString(HomeConstant.KEY_HOUSE_ID)
        var houseDetailFragment = HouseDetailFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_ID,id)
        houseDetailFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            houseDetailFragment
        ).commitAllowingStateLoss()
    }
}