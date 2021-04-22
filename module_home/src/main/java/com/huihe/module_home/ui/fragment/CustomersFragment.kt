package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.CustomersPresenter
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.ui.adpter.SecondHandHouseAdapter
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_secondhandhouse.*
import org.jetbrains.anko.support.v4.toast

class CustomersFragment : BaseMvpFragment<CustomersPresenter>(), SecondHandHouseView,
    BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    private var hasMoreData = true
    private var mGoodsAdapter: SecondHandHouseAdapter? = null

    init {
        hasMoreData = true
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_secondhandhouse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRefreshLayout()
        initData()
    }

    private fun initView() {
        customers_mRecyclerView.layoutManager = LinearLayoutManager(context)
        mGoodsAdapter = SecondHandHouseAdapter(context!!)
        customers_mRecyclerView.adapter = mGoodsAdapter

        mGoodsAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Customer> {
            override fun onItemClick(item: Customer, position: Int) {
//                startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)
            }
        })
    }

    private fun initRefreshLayout() {
        customers_mBGARefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(context, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        customers_mBGARefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun initData() {
        customers_mMultiStateView?.startLoading()
        loadData()
    }

    private fun loadData() {
        mPresenter?.getMoreCustomersList(mCurrentPage, mPageSize)
    }

    override fun onDataIsNull() {
        customers_mMultiStateView?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onError(text: String) {
        customers_mMultiStateView?.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    /*
        上拉加载更多
     */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (hasMoreData) {
            mCurrentPage++
            loadData()
            true
        } else {
            toast("没有更多数据了!")
            false
        }
    }

    /*
        下拉加载第一页
     */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

    override fun onGetHouseListResult(result: MutableList<Customer>?) {
        customers_mBGARefreshLayout?.endRefreshing()
        customers_mBGARefreshLayout?.endLoadingMore()
        hasMoreData = if (result != null) (result.size <= mPageSize) else false
        if (result != null && result.size > 0) {
            if (mCurrentPage == 1) {
                mGoodsAdapter?.setData(result)
            } else {
                mGoodsAdapter?.dataList?.addAll(result)
                mGoodsAdapter?.notifyDataSetChanged()
            }
            customers_mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            onDataIsNull()
        }
    }
}