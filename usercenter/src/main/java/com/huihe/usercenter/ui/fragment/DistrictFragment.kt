package com.huihe.usercenter.ui.fragment

import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.SchoolDistrictRep
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.DistrictPresenter
import com.huihe.usercenter.presenter.view.DistrictView
import com.huihe.usercenter.ui.adapter.DistrictRvAdapter
import com.kotlin.base.ui.fragment.BaseTitleRefreshFragment

class DistrictFragment :
    BaseTitleRefreshFragment<DistrictPresenter, DistrictRvAdapter, SchoolDistrictRep.SchoolDistrict>(),DistrictView {

    override fun getAdapter(): DistrictRvAdapter {
        return DistrictRvAdapter(context!!)
    }

    override fun initView() {
       initTitle(resources.getString(R.string.district_manager))
    }

    override fun loadData() {
        mPresenter.getSchoolDistrictList(mCurrentPage,mPageSize)
    }

    override fun onSchoolDistrictList(list: MutableList<SchoolDistrictRep.SchoolDistrict>?) {
        refreshDataList(list!!)
    }

    override fun addAllData(
        mRvAdapter: DistrictRvAdapter,
        list: MutableList<SchoolDistrictRep.SchoolDistrict>
    ) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun setData(
        mRvAdapter: DistrictRvAdapter,
        list: MutableList<SchoolDistrictRep.SchoolDistrict>
    ) {
        mRvAdapter.setData(list)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }
}
