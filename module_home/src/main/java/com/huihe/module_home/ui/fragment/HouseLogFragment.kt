package com.huihe.module_home.ui.fragment

import com.huihe.boyueentities.protocol.home.HouseLog
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseLogPresenter
import com.huihe.module_home.presenter.view.HouseLogView
import com.huihe.module_home.ui.adapter.HouseLogRvAdapter
import com.kotlin.base.ui.fragment.BaseRefreshFragment
import com.kotlin.provider.constant.HomeConstant

class HouseLogFragment : BaseRefreshFragment<HouseLogPresenter, HouseLogRvAdapter,HouseLog>(), HouseLogView {

    var houseCode: String? = null
    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun initView() {
        houseCode = arguments?.getString(HomeConstant.KEY_HOUSE_CODE)
    }

    override fun getAdapter(): HouseLogRvAdapter {
        return HouseLogRvAdapter(context!!)
    }

    override fun loadData(mCurrentPage: Int, mPageSize: Int) {
        mPresenter.getHouseLog(mCurrentPage, mPageSize, houseCode)
    }

    override fun onLogListResult(list: MutableList<HouseLog>?, totalCount: Int?) {
        refreshDataList(list!!)
    }

    override fun addAllData(mRvAdapter: HouseLogRvAdapter, list: MutableList<HouseLog>) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun setData(mRvAdapter: HouseLogRvAdapter, list: MutableList<HouseLog>) {
        mRvAdapter.setData(list)
    }

}