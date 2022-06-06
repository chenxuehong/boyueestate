package com.huihe.customercenter.ui.fragment

import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.boyueentities.protocol.customer.CustomerFollowRep
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerFollowPresenter
import com.huihe.customercenter.presenter.view.CustomerFollowView
import com.huihe.customercenter.ui.adapter.CustomerFollowRvAdapter
import com.kotlin.base.ui.fragment.BaseRefreshFragment
import com.kotlin.provider.constant.CustomerConstant
import com.kotlin.provider.event.AddCustomerFollowEvent

class CustomerFollowFragment :
    BaseRefreshFragment<CustomerFollowPresenter, CustomerFollowRvAdapter, CustomerFollowRep.CustomerFollow>(),
    CustomerFollowView {
    var customerCode = ""
    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun getAdapter(): CustomerFollowRvAdapter {
        return CustomerFollowRvAdapter(context!!)
    }

    override fun initView() {
        customerCode = arguments?.getString(CustomerConstant.KEY_CUSTOMER_CODE) ?: ""

        initListener()
    }

    private fun initListener() {
        Bus.observe<AddCustomerFollowEvent>()
            .subscribe {
                loadData(mCurrentPage,mPageSize)
            }.registerInBus(this)
    }

    override fun loadData(mCurrentPage: Int, mPageSize: Int) {
        mPresenter.getCustomerFollowList(page = mCurrentPage, customerCode = customerCode)
    }

    override fun onCustomerFollowListResult(list: MutableList<CustomerFollowRep.CustomerFollow>?) {
        refreshDataList(list!!)
    }

    override fun addAllData(
        mRvAdapter: CustomerFollowRvAdapter,
        list: MutableList<CustomerFollowRep.CustomerFollow>
    ) {
        mRvAdapter.dataList?.addAll(list)
    }

    override fun setData(
        mRvAdapter: CustomerFollowRvAdapter,
        list: MutableList<CustomerFollowRep.CustomerFollow>
    ) {
        mRvAdapter.setData(list)
    }

    override fun onDestroy() {
        try {
            Bus.unregister(this)
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

}
