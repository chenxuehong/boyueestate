package com.huihe.module_home.ui.fragment

import com.huihe.module_home.data.protocol.HouseLog
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HousePhoneLogPresenter
import com.huihe.module_home.presenter.view.HousePhoneLogView
import com.huihe.module_home.ui.adpter.HousePhoneLogRvAdapter
import com.kotlin.base.ui.fragment.BaseRefreshFragment
import com.kotlin.provider.constant.HomeConstant

class HousePhoneLogFragment : BaseRefreshFragment<HousePhoneLogPresenter, HousePhoneLogRvAdapter, HouseLog>(), HousePhoneLogView {

    var houseCode: String? = null

    override fun setData(mRvAdapter: HousePhoneLogRvAdapter, list: MutableList<HouseLog>) {
        mRvAdapter.setData(list)
    }

    override fun addAllData(mRvAdapter: HousePhoneLogRvAdapter, list: MutableList<HouseLog>) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun loadData(mCurrentPage: Int, mPageSize: Int) {
        mPresenter.getHousePhoneLog(mCurrentPage,mPageSize,houseCode)
    }

    override fun getAdapter(): HousePhoneLogRvAdapter {
        return HousePhoneLogRvAdapter(context!!)
    }

    override fun initView() {
        houseCode = arguments?.getString(HomeConstant.KEY_HOUSE_CODE)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun getLogListResult(list: MutableList<HouseLog>?, totalCount: Int?) {
        refreshDataList(list!!)
    }
}
