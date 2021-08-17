package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.FollowRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseFollowPresenter
import com.huihe.module_home.presenter.view.HouseFollowView
import com.huihe.module_home.ui.activity.AddFollowActivity
import com.huihe.module_home.ui.adapter.HouseFollowRvAdapter
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_follow.*

class HouseFollowFragment : BaseMvpFragment<HouseFollowPresenter>(), HouseFollowView {

    var houseCode: String? = null
    var houseId: String? = null
    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    lateinit var houseFollowRvAdapter: HouseFollowRvAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_follow, container, false)
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
        houseId = arguments?.getString(HomeConstant.KEY_HOUSE_ID)
        initRefreshLayout()
        initView()
        initData()
    }

    private fun initRefreshLayout() {
        chouse_follow_mBGARefreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData()
        }
        chouse_follow_mBGARefreshLayout.setOnLoadMoreListener {
            mCurrentPage++
            loadData()
        }
    }

    private fun initView() {
        house_follow_rvList.layoutManager = LinearLayoutManager(context)
        houseFollowRvAdapter = HouseFollowRvAdapter(context!!)
        house_follow_rvList.adapter = houseFollowRvAdapter
        houseFollowRvAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<FollowRep.FollowBean>{
            override fun onItemClick(view: View, item: FollowRep.FollowBean, position: Int) {

            }
        })
        house_follow_titleBar.getRightView().onClick {
            val intent = Intent(context,AddFollowActivity::class.java)
            intent.putExtra(HomeConstant.KEY_HOUSE_ID,houseId)
            startActivity(intent)
        }
        Bus.observe<FollowRep.FollowBean>()
            .subscribe {
                houseFollowRvAdapter?.dataList?.add(0,it)
                houseFollowRvAdapter?.notifyItemChanged(0)
            }.registerInBus(this)
    }

    private fun initData() {
        house_follow_mMultiStateView?.startLoading()
        mCurrentPage = 1
        loadData()
    }

    private fun loadData() {
        mPresenter?.getHouseFollowList(
            mCurrentPage,
            mPageSize,
            houseCode
        )
    }

    override fun getFollowRepResult(t: FollowRep?) {
        var followBeanList = t?.list
        chouse_follow_mBGARefreshLayout?.finishRefresh()
        chouse_follow_mBGARefreshLayout?.finishLoadMore()
        var hasMoreData = if (followBeanList != null) (followBeanList.size >= mPageSize) else false
        if (followBeanList != null && followBeanList.size > 0) {
            if (mCurrentPage == 1) {
                chouse_follow_mBGARefreshLayout?.resetNoMoreData()
                houseFollowRvAdapter?.setData(followBeanList)
            } else {
                houseFollowRvAdapter?.dataList?.addAll(followBeanList)
                houseFollowRvAdapter?.notifyDataSetChanged()
            }
            house_follow_mMultiStateView?.viewState =
                MultiStateView.VIEW_STATE_CONTENT
        }else{
            if (mCurrentPage == 1) {
                chouse_follow_mBGARefreshLayout?.finishRefreshWithNoMoreData()
                onDataIsNull()
            } else {
                chouse_follow_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
                house_follow_mMultiStateView?.viewState =
                    MultiStateView.VIEW_STATE_CONTENT
            }
        }
        if (!hasMoreData){
            if (mCurrentPage == 1) {
                chouse_follow_mBGARefreshLayout?.finishRefreshWithNoMoreData()
            }else{
                chouse_follow_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
            }
        }
    }

    override fun onDataIsNull() {
        house_follow_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onError(text: String) {
        super.onError(text)
        house_follow_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_ERROR
    }

    override fun onDestroy() {
        try {
            Bus.unregister(this)
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}