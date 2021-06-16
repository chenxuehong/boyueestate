package com.huihe.module_home.ui.fragment

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.ui.fragment.BaseTitleFragment
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.HomeCenter.PATH_HOUSE_MAP_FRAGMENT)
class HouseMapTitleFragment : BaseTitleFragment() {

    override fun initTitle(titleBar: HeaderBar) {
        titleBar.setTitle("地图")
    }

    override fun getFragment(): Fragment {
        return HouseMapFragment()
    }

}