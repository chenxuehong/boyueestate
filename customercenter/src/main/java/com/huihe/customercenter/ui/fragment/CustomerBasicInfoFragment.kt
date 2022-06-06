package com.huihe.customercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.CustomerDetailBean
import com.huihe.customercenter.ui.adapter.CustomerBasicInfoRvAdapter
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.CustomerConstant
import kotlinx.android.synthetic.main.fragment_customer_basic_info.*

class CustomerBasicInfoFragment : BaseFragment() {

    lateinit var customerBasicInfoRvAdapter: CustomerBasicInfoRvAdapter
    lateinit var mBasicInfolist:MutableList<CustomerDetailBean.BasicInfo>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_customer_basic_info, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var json = arguments?.getString(CustomerConstant.KEY_BASIC_INFO) ?: ""
        var typeToken = object : TypeToken<MutableList<CustomerDetailBean.BasicInfo>?>() {}
        mBasicInfolist =
            Gson().fromJson<MutableList<CustomerDetailBean.BasicInfo>>(json, typeToken.type)
        initAdapter()

    }

    private fun initAdapter() {
        customerBasicInfoRvAdapter = CustomerBasicInfoRvAdapter(context!!)
        rvCustomerBasicInfo.vertical()
        rvCustomerBasicInfo.adapter = customerBasicInfoRvAdapter
        customerBasicInfoRvAdapter.setData(mBasicInfolist)
    }
}