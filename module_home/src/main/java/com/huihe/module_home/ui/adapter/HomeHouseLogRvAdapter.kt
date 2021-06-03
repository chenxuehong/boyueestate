package com.huihe.module_home.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter

class HomeHouseLogRvAdapter(
    fm: FragmentManager,
    titles: MutableList<String>,
    fragments: List<Fragment>
) : BaseFragmentStatePageAdapter(fm, titles, fragments){

}
