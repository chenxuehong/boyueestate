package com.kotlin.base.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BaseFragmentStatePageAdapter : FragmentStatePagerAdapter {

    private var titles: MutableList<String>? = null
    private var fragments: List<Fragment>? = null

    constructor(
        fm: FragmentManager,
        titles: MutableList<String>,
        fragments: List<Fragment>
    ) : super(fm) {
        this.titles = titles
        this.fragments = fragments
    }

    constructor(fm: FragmentManager, fragments: List<Fragment>) : super(fm) {
        this.fragments = fragments
    }

    override fun getItem(i: Int): Fragment {

        return fragments!![i]
    }

    override fun getCount(): Int {
        return if (fragments != null) fragments!!.size else 0
    }

    // 动态设置我们标题的方法
    fun setPageTitle(position: Int, title: String) {
        if (position >= 0 && position < titles!!.size) {
            titles!![position] = title
            notifyDataSetChanged()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return if (titles != null) {
            titles!![position]
        } else super.getPageTitle(position)
    }
}
