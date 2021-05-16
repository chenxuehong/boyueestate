package com.huihe.customercenter.ui.fragment

import com.huihe.customercenter.data.protocol.CustomerLogRep
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerLogPresenter
import com.huihe.customercenter.presenter.view.CustomerLogView
import com.huihe.customercenter.ui.adapter.CustomerLogRvAdapter
import com.kotlin.base.ui.fragment.BaseRefreshFragment
import com.kotlin.provider.constant.CustomerConstant

class CustomerLogFragment :
    BaseRefreshFragment<CustomerLogPresenter, CustomerLogRvAdapter, CustomerLogRep.CustomerLog>(),
    CustomerLogView {

    var customerCode: String? = null
    override fun initView() {
        customerCode = arguments?.getString(CustomerConstant.KEY_CUSTOMER_CODE)
    }

    override fun getAdapter(): CustomerLogRvAdapter {
        return CustomerLogRvAdapter(context!!)
    }

    override fun loadData(mCurrentPage: Int, mPageSize: Int) {

        mPresenter.getCustomerLogList(mCurrentPage,mPageSize,customerCode)
    }

    override fun onCustomerLogListResult(list: MutableList<CustomerLogRep.CustomerLog>?) {
        refreshDataList(list!!)
    }

    override fun addAllData(mRvAdapter: CustomerLogRvAdapter, list: MutableList<CustomerLogRep.CustomerLog>) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun setData(mRvAdapter: CustomerLogRvAdapter, list: MutableList<CustomerLogRep.CustomerLog>) {
        mRvAdapter.setData(list)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }
}
