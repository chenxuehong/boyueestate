package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.CustomerProfileInfo
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.CustomerProfilePresenter
import com.huihe.module_home.presenter.view.CustomerProfileView
import com.huihe.module_home.ui.adpter.CustomerProfileRvAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.layout_fragment_customer_profile.*

class CustomerProfileFragment : BaseMvpFragment<CustomerProfilePresenter>(), CustomerProfileView {

    var id: String? = null
    var mCustomerProfileRvAdapter:CustomerProfileRvAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_customer_profile, container, false)
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
        mCustomerProfileRvAdapter = CustomerProfileRvAdapter(context!!)
        customerProfileExListView.layoutManager = LinearLayoutManager(context)
        customerProfileExListView?.adapter = mCustomerProfileRvAdapter
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
