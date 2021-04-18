package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huihe.module_home.R
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.layout_fragment_home.*

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        home_tabLayout?.setupWithViewPager(home_viewPager)
        val titles = ArrayList<String>()
        val fragments = ArrayList<Fragment>()
        titles.add("二手房")
        titles.add("地图找房")
        fragments.add(CustomersFragment())
        fragments.add(CustomersMapFragment())
        home_viewPager.adapter = BaseFragmentStatePageAdapter(
            childFragmentManager,
            titles,
            fragments
        )
        home_fl_add.onClick {

        }
    }
}
