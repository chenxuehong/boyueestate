package com.huihe.usercenter.ui.fragment

import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.ResultCommunityPresenter
import com.huihe.usercenter.presenter.view.ResultCommunityView
import com.huihe.usercenter.ui.activity.SearchHouseListActivity
import com.huihe.usercenter.ui.adapter.ResultCommunityRvAdapter
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.event.VillageEvent
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseTitleRefreshFragment
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.data.protocol.District
import org.jetbrains.anko.support.v4.startActivity

class ResultCommunityFragment :
    BaseTitleRefreshFragment<ResultCommunityPresenter, ResultCommunityRvAdapter, District.ZoneBean.VillageBean>(),
    ResultCommunityView {

    var mVillage: String = ""
    var isSelect = false
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun getAdapter(): ResultCommunityRvAdapter {
        return ResultCommunityRvAdapter(context!!)
    }

    override fun initView() {
        initTitle("搜索")
        mVillage = arguments?.getString(UserConstant.KEY_VILLAGE) ?: ""
        isSelect = arguments?.getBoolean(BaseConstant.KEY_ISSELECT) ?: false
        if (isSelect) {
            Bus.observe<VillageEvent>()
                .subscribe {
                    activity?.finish()
                }.registerInBus(this)
        }
    }

    override fun loadData() {
        mPresenter.getVillages(mVillage)
    }

    override fun addAllData(
        mRvAdapter: ResultCommunityRvAdapter,
        list: MutableList<District.ZoneBean.VillageBean>
    ) {
        mRvAdapter?.dataList?.addAll(list)
    }

    override fun setData(
        mRvAdapter: ResultCommunityRvAdapter,
        list: MutableList<District.ZoneBean.VillageBean>
    ) {
        mRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<District.ZoneBean.VillageBean> {
            override fun onItemClick(
                view: View,
                item: District.ZoneBean.VillageBean,
                position: Int
            ) {
                if (isSelect) {
                    Bus.send(
                        VillageEvent(
                            item.id ?: "",
                            item.districtName ?: "",
                            item.zoneName ?: "",
                            item.name ?: ""
                        )
                    )
                }else{
                    startActivity<SearchHouseListActivity>(HomeConstant.KEY_VILLAGE_NAME to item.name)
                }
            }
        })
        mRvAdapter?.setData(list)
    }

    override fun onGetAreaBeanListResult(villages: MutableList<District.ZoneBean.VillageBean>?) {
        refreshDataList(villages!!)
    }

    override fun onDestroy() {
        try {
            if (isSelect) {
                Bus.unregister(this)
            }
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}