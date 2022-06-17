package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.huihe.boyueentities.protocol.home.CustomerProfileInfo
import com.huihe.module_home.databinding.LayoutFragmentCustomerProfileBinding
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.CustomerProfilePresenter
import com.huihe.module_home.presenter.view.CustomerProfileView
import com.huihe.module_home.ui.activity.HouseDetailActivity
import com.huihe.module_home.ui.adapter.CustomerProfileRvAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.CustomerConstant
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath
import org.jetbrains.anko.support.v4.startActivity

class CustomerProfileFragment : BaseMvpFragment<CustomerProfilePresenter>(), CustomerProfileView,
    CustomerProfileRvAdapter.OnListener {

    override fun onHouseClicked(id: String) {
        startActivity<HouseDetailActivity>(HomeConstant.KEY_HOUSE_ID to id)
    }

    override fun onCustomerClicked(id: String) {
        ARouter.getInstance()
            .build(RouterPath.CustomerCenter.PATH_CUSTOMER_DETAIL)
            .withString(CustomerConstant.KEY_CUSTOMER_ID, id)
            .navigation(context)
    }

    var id: String? = null
    var mCustomerProfileRvAdapter: CustomerProfileRvAdapter? = null
    lateinit var binding : LayoutFragmentCustomerProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = LayoutFragmentCustomerProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(HomeConstant.KEY_HOUSE_ID)
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        mCustomerProfileRvAdapter = CustomerProfileRvAdapter(context!!, this)
        binding.customerProfileExListView.layoutManager = LinearLayoutManager(context)
        binding.customerProfileExListView?.adapter = mCustomerProfileRvAdapter
    }

    private fun initData() {
        mPresenter.getCustomerProfile(id)
    }

    override fun getCustomerProfileIResult(customerProfileInfo: CustomerProfileInfo?) {
        var mutableListOf =
            mutableListOf(customerProfileInfo, customerProfileInfo, customerProfileInfo)
        mCustomerProfileRvAdapter?.setData(mutableListOf)
    }
}
