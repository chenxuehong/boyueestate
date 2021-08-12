package com.huihe.module_home.ui.activity

import android.os.Bundle
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.module_home.R
import com.huihe.module_home.ui.fragment.HouseFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.HomeCenter.PATH_HOUSE_SELECT_ACTIVITY)
class HouseSelectActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.select_house))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        var houseFragment = HouseFragment()
        val args = Bundle()
        args.putBoolean(HomeConstant.KEY_IS_HOUSE_SELECT, true)
        setFragment(houseFragment, args)
    }
}
