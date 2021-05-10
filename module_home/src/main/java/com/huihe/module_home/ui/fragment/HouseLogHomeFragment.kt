package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.huihe.module_home.R
import com.huihe.module_home.ui.adpter.HomeHouseLogRvAdapter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_log_home.*

class HouseLogHomeFragment : BaseFragment() {

    val mTitles = mutableListOf<String>(
        BaseApplication.context.resources.getString(R.string.log),
        BaseApplication.context.resources.getString(R.string.phone_log))
    var mFragments:ArrayList<Fragment> = ArrayList()
    var houseCode:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_log_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseCode = arguments?.getString(HomeConstant.KEY_HOUSE_CODE)
        initAdapter()
    }

    private fun initAdapter() {
        mFragments = getFragments()
        homeHouseLogTabLayout.setupWithViewPager(homeHouseLogViewpager)
        homeHouseLogViewpager.adapter = HomeHouseLogRvAdapter(
            childFragmentManager,
            mTitles,
            mFragments)
    }

    private fun getFragments(): ArrayList<Fragment> {
       var houseLogFragment = HouseLogFragment()
       var housePhoneLogFragment = HousePhoneLogFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_CODE,houseCode)
        houseLogFragment.arguments = args
        housePhoneLogFragment.arguments = args
        mFragments.add(houseLogFragment)
        mFragments.add(housePhoneLogFragment)
        return mFragments
    }
}
