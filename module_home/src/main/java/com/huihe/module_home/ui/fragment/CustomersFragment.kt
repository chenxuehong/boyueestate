package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.presenter.CustomersPresenter
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.ui.adpter.SecondHandHouseAdapter
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_secondhandhouse.*

class CustomersFragment : BaseMvpFragment<CustomersPresenter>(), SecondHandHouseView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 10
    private var mGoodsAdapter: SecondHandHouseAdapter ?= null

    override fun injectComponent() {
//        DaggerCategoryComponent.builder().activityComponent(mActivityComponent).categoryModule(CategoryModule()).build().inject(this)
        mPresenter.mView = this
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
    }

    private fun initView() {
        customers_mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mGoodsAdapter = SecondHandHouseAdapter(context!!)
        customers_mRecyclerView.adapter = mGoodsAdapter

        mGoodsAdapter?.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Customer> {
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

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        customers_mMultiStateView?.startLoading()
        mPresenter.getMoreCustomersList()
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
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            true
        } else {
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
        customers_mBGARefreshLayout?.endLoadingMore()
        customers_mBGARefreshLayout?.endRefreshing()
        if (result != null && result.size > 0) {
            if (mCurrentPage == 1) {
                mGoodsAdapter?.setData(result)
            } else {
                mGoodsAdapter?.dataList?.addAll(result)
                mGoodsAdapter?.notifyDataSetChanged()
            }
            customers_mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }
    }
}