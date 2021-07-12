package com.huihe.usercenter.ui.fragment

import android.view.View
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.CorporateCultureRep
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.CorporateCulturePresenter
import com.huihe.usercenter.presenter.view.CorporateCultureView
import com.huihe.usercenter.ui.adapter.CorporateCultureRvAdapter
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ui.activity.WebViewActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseTitleRefreshFragment
import org.jetbrains.anko.support.v4.startActivity


class CorporateCultureFragment :
    BaseTitleRefreshFragment<CorporateCulturePresenter, CorporateCultureRvAdapter, CorporateCultureRep.CorporateCulture>(),
    CorporateCultureView {

    override fun getAdapter(): CorporateCultureRvAdapter {
        return CorporateCultureRvAdapter(context!!)
    }

    override fun initView() {
        initTitle(resources.getString(R.string.corporate_culture))
    }

    override fun loadData() {

        mPresenter.getMobTechList(mCurrentPage, mPageSize)
    }

    override fun onMobTechList(list: MutableList<CorporateCultureRep.CorporateCulture>?) {
        refreshDataList(list!!)
    }

    override fun addAllData(
        mRvAdapter: CorporateCultureRvAdapter,
        list: MutableList<CorporateCultureRep.CorporateCulture>
    ) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun setData(
        mRvAdapter: CorporateCultureRvAdapter,
        list: MutableList<CorporateCultureRep.CorporateCulture>
    ) {
        mRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<CorporateCultureRep.CorporateCulture> {
            override fun onItemClick(
                view: View,
                item: CorporateCultureRep.CorporateCulture,
                position: Int
            ) {
                var url = "http://billion.housevip.cn/#/content/${item.workNo}/ip/${BaseConstant.ip}"
                startActivity<WebViewActivity>(WebViewActivity.KET_URL to url)
            }
        })
        mRvAdapter.setData(list)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }
}
