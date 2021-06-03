package com.huihe.module_home.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter

class HouseDetailFragmentAdapter : BaseFragmentStatePageAdapter {
    constructor(
        fm: FragmentManager,
        titles: MutableList<String>,
        fragments: List<Fragment>
    ) : super(fm, titles, fragments) {
    }

    constructor(fm: FragmentManager, fragments: List<Fragment>) : super(fm, fragments) {}
}
