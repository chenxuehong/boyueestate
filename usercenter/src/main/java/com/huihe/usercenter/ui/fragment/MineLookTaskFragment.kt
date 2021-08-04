package com.huihe.usercenter.ui.fragment

import android.view.View
import com.huihe.usercenter.data.protocol.MineLookTaskRep.MineLookTask
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MineLookTaskPresenter
import com.huihe.usercenter.presenter.view.MineLookTaskView
import com.huihe.usercenter.ui.activity.MineLookTaskDetailActivity
import com.huihe.usercenter.ui.adapter.MineLookTaskRvAdapter
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

import com.kotlin.base.ui.fragment.BaseRefreshFragment
import com.kotlin.base.utils.DensityUtils
import org.jetbrains.anko.support.v4.startActivity

class MineLookTaskFragment :
    BaseRefreshFragment<MineLookTaskPresenter, MineLookTaskRvAdapter, MineLookTask>(),
    MineLookTaskView, BaseRecyclerViewAdapter.OnItemClickListener<MineLookTask> {

    var status:Int=0
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun initView() {
        status = arguments?.getInt(BaseConstant.KEY_STATUS, 0) ?: 0
    }

    override fun getSpace(): Int {
        return DensityUtils.dp2px(context,15f)
    }

    override fun getAdapter(): MineLookTaskRvAdapter {
        var mineLookTaskRvAdapter = MineLookTaskRvAdapter(context!!)
        mineLookTaskRvAdapter.setOnItemClickListener(this)
        return mineLookTaskRvAdapter
    }

    override fun loadData(mCurrentPage: Int, mPageSize: Int) {
        mPresenter?.getLookTaskList(status,mCurrentPage,mPageSize)
    }

    override fun addAllData(mRvAdapter: MineLookTaskRvAdapter, list: MutableList<MineLookTask>) {
        mRvAdapter?.dataList?.addAll(list)
    }

    override fun setData(mRvAdapter: MineLookTaskRvAdapter, list: MutableList<MineLookTask>) {
        mRvAdapter?.setData(list)
    }

    override fun onLookTaskList(list: MutableList<MineLookTask>?) {
        refreshDataList(list!!)
    }

    override fun onItemClick(view: View, item: MineLookTask, position: Int) {
        startActivity<MineLookTaskDetailActivity>()
    }
}