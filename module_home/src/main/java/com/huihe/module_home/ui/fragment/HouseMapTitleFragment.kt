package com.huihe.module_home.ui.fragment

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.huihe.module_home.R
import com.kotlin.base.common.OnRefreshListener
import com.kotlin.base.ext.doRefreshFragments
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseTitleFragment
import com.kotlin.base.utils.FragmentUtils
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.event.ResetMapEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.layout_house_map_reset.view.*

@Route(path = RouterPath.HomeCenter.PATH_HOUSE_MAP_FRAGMENT)
class HouseMapTitleFragment : BaseTitleFragment(),OnRefreshListener {

    override fun initTitle(titleBar: HeaderBar) {
        titleBar.setTitle("地图")
        var inflateRightContentView =
            titleBar.inflateRightContentView(R.layout.layout_house_map_reset)
        inflateRightContentView.ivHouseMapReset.onClick {
            Bus.send(ResetMapEvent())
        }
    }

    override fun getFragment(): Fragment {
        return HouseMapFragment()
    }

    override fun onRefresh() {
        FragmentUtils.getFragments(childFragmentManager).doRefreshFragments()
    }
}