package com.huihe.customercenter.ui.fragment

import com.huihe.customercenter.data.protocol.CustomerTelLogRep
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerTelLogPresenter
import com.huihe.customercenter.presenter.view.CustomerTelLogView
import com.huihe.customercenter.ui.adapter.CustomerTelLogRvAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.ui.fragment.BaseRefreshFragment
import com.kotlin.provider.constant.CustomerConstant

class CustomerTelLogFragment :
    BaseRefreshFragment<CustomerTelLogPresenter, CustomerTelLogRvAdapter, CustomerTelLogRep.CustomerTelLog>(),CustomerTelLogView {

    var customerCode: String? = null
    override fun initView() {
        customerCode = arguments?.getString(CustomerConstant.KEY_CUSTOMER_CODE)
    }

    override fun getAdapter(): CustomerTelLogRvAdapter {
       return CustomerTelLogRvAdapter(context!!)
    }

    override fun loadData(mCurrentPage: Int, mPageSize: Int) {
        mPresenter.getCustomerTelLogList(mCurrentPage,mPageSize,customerCode)
    }

    override fun onCustomerPhoneLogListResult(list: MutableList<CustomerTelLogRep.CustomerTelLog>?) {
        refreshDataList(list!!)
    }

    override fun addAllData(
        mRvAdapter: CustomerTelLogRvAdapter,
        list: MutableList<CustomerTelLogRep.CustomerTelLog>
    ) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun setData(
        mRvAdapter: CustomerTelLogRvAdapter,
        list: MutableList<CustomerTelLogRep.CustomerTelLog>
    ) {
        mRvAdapter.setData(list)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }
}
