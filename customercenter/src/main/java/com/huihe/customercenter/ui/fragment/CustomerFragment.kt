package com.huihe.customercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.huihe.customercenter.R
import com.huihe.customercenter.ui.activity.AddCustomerActivity
import com.kotlin.base.common.OnRefreshListener
import com.kotlin.base.ext.doRefreshFragments
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.CustomerConstant
import com.kotlin.provider.event.ResetCustomerEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.layout_fragment_customer.*
import org.jetbrains.anko.support.v4.startActivity

@Route(path = RouterPath.CustomerCenter.PATH_CUSTOMER)
class CustomerFragment : BaseFragment(), OnRefreshListener {

    val fragments = ArrayList<Fragment>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        customer_tabLayout?.setupWithViewPager(customer_viewPager)
        val titles = ArrayList<String>()
        fragments.clear()
        titles.add("买卖")
        titles.add("租赁")
        titles.add("聚宝盆")
        fragments.add(getStatusFragment(2, null))
        fragments.add(getStatusFragment(1, null))
        fragments.add(getStatusFragment(2, 1))
        customer_viewPager.offscreenPageLimit = 3
        customer_viewPager.adapter = BaseFragmentStatePageAdapter(
            childFragmentManager,
            titles,
            fragments
        )
        customer_fl_add.onClick {
            startActivity<AddCustomerActivity>()
        }
        flClearRefresh.onClick {
            Bus.send(ResetCustomerEvent())
        }
    }

    private fun getStatusFragment(customerType: Int, isCornucopia: Int?): Fragment {
        var customerListFragment = CustomerListFragment()
        val args = Bundle()
        args.putInt(CustomerConstant.KEY_CUSTOMERTYPE, customerType)
        if (isCornucopia != null){
            args.putInt(CustomerConstant.KEY_IS_CORNUCOPIA, isCornucopia)
        }
        customerListFragment.arguments = args
        return customerListFragment
    }

    override fun onRefresh() {
        fragments.doRefreshFragments()
    }
}
