package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.R
import com.huihe.module_home.ui.fragment.HouseDetailFragment
import com.kotlin.base.ui.activity.BaseActivity

/**
 * @desc 房源详情
 */
class HouseDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            HouseDetailFragment()
        ).commitAllowingStateLoss()
    }
}