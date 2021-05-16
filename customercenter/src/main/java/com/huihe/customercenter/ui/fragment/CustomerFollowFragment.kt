package com.huihe.customercenter.ui.fragment

import android.content.Intent
import android.view.View
import com.huihe.customercenter.R
import com.huihe.customercenter.data.protocol.CustomerFollowRep
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerFollowPresenter
import com.huihe.customercenter.presenter.view.CustomerFollowView
import com.huihe.customercenter.ui.activity.AddCustomerFollowActivity
import com.huihe.customercenter.ui.adapter.CustomerFollowRvAdapter
import com.kotlin.base.ui.fragment.BaseTitleRefreshFragment
import com.kotlin.provider.constant.CustomerConstant
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult

class CustomerFollowFragment :
    BaseTitleRefreshFragment<CustomerFollowPresenter, CustomerFollowRvAdapter, CustomerFollowRep.CustomerFollow>(),
    CustomerFollowView {
    var customerCode = ""
    val REQUEST_CODE_NEW_INSERT = 1000
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
        initTitle(resources.getString(R.string.customer_follow))
        setRightTitle(resources.getString(R.string.new_insert), View.OnClickListener {

            startActivityForResult<AddCustomerFollowActivity>(REQUEST_CODE_NEW_INSERT,CustomerConstant.KEY_CUSTOMER_CODE to customerCode)
        })
    }

    override fun loadData() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_NEW_INSERT == requestCode){
            mCurrentPage = 1
            loadData()
        }
    }
}
