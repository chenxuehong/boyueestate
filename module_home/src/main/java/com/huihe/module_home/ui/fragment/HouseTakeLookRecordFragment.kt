package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseTakeLookPresenter
import com.huihe.module_home.presenter.view.HouseTakeLookView
import com.huihe.module_home.ui.adpter.HouseTakeLookRecordRvAdapter
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_take_look_record.*

class HouseTakeLookRecordFragment : BaseMvpFragment<HouseTakeLookPresenter>(),HouseTakeLookView {

    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    private var houseCode:String?=null
    private var mHouseTakeLookRvAdapter: HouseTakeLookRecordRvAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_take_look_record, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseCode = arguments?.getString(HomeConstant.KEY_HOUSE_CODE)
        initRefreshLayout()
        initView()
        initData()
    }

    private fun initRefreshLayout() {
        chouse_take_look_record_mBGARefreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData()
        }
        chouse_take_look_record_mBGARefreshLayout.setOnLoadMoreListener {
            mCurrentPage++
            loadData()
        }
    }

    private fun initView() {
        house_take_look_record_rvList.layoutManager = LinearLayoutManager(context)
        mHouseTakeLookRvAdapter =
            HouseTakeLookRecordRvAdapter(context!!)
        house_take_look_record_rvList.adapter = mHouseTakeLookRvAdapter
        mHouseTakeLookRvAdapter?.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<HouseTakeLookRep.HouseTakeLook>{
            override fun onItemClick(
                view: View,
                item: HouseTakeLookRep.HouseTakeLook,
                position: Int
            ) {

            }
        })
    }

    private fun initData() {
        house_take_look_record_mMultiStateView?.startLoading()
        mCurrentPage = 1
        loadData()
    }

    private fun loadData() {
        mPresenter?.getTakeLookRecord(mCurrentPage,mPageSize,houseCode)
    }

    override fun onDataIsNull() {
        house_take_look_record_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onError(text: String) {
        house_take_look_record_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_ERROR
    }

    override fun onTakeLookRecordResult(t: HouseTakeLookRep?) {
        var list = t?.list
        chouse_take_look_record_mBGARefreshLayout?.finishRefresh()
        chouse_take_look_record_mBGARefreshLayout?.finishLoadMore()
        var hasMoreData = if (list != null) (list.size >= mPageSize) else false
        if (list != null && list.size > 0) {
            if (mCurrentPage == 1) {
                chouse_take_look_record_mBGARefreshLayout?.resetNoMoreData()
                mHouseTakeLookRvAdapter?.setData(list)
            } else {
                mHouseTakeLookRvAdapter?.dataList?.addAll(list)
                mHouseTakeLookRvAdapter?.notifyDataSetChanged()
            }
            house_take_look_record_mMultiStateView?.viewState =
                MultiStateView.VIEW_STATE_CONTENT
        }else{
            if (mCurrentPage == 1) {
                chouse_take_look_record_mBGARefreshLayout?.finishRefreshWithNoMoreData()
                onDataIsNull()
            } else {
                chouse_take_look_record_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
                house_take_look_record_mMultiStateView?.viewState =
                    MultiStateView.VIEW_STATE_CONTENT
            }
        }
        if (!hasMoreData){
            if (mCurrentPage == 1) {
                chouse_take_look_record_mBGARefreshLayout?.finishRefreshWithNoMoreData()
            }else{
                chouse_take_look_record_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
            }
        }
    }
}