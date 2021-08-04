package com.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.kotlin.base.R
import com.kotlin.base.data.protocol.ErrorBean
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ext.vertical
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_refresh.*
import kotlinx.android.synthetic.main.fragment_title_refresh.*
import javax.inject.Inject

abstract class BaseRefreshFragment<T : BasePresenter<*>, Adapter : BaseRecyclerViewAdapter<*, *>, D> :
    BaseMvpFragment<T>() {

    @Inject
    lateinit var num: ErrorBean

    var mCurrentPage: Int = 1
    var mPageSize: Int = 30
    var hasMoreData: Boolean = false
    lateinit var mRvAdapter: Adapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_refresh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRefreshLayout()
        initAdapter()
        initData()
    }

    abstract fun initView()

    private fun initData() {
        refresh_mMultiStateView.startLoading()
        mCurrentPage = 1
        loadData(mCurrentPage, mPageSize)
    }

    fun initAdapter() {
        refresh_mRecyclerView?.vertical(getColumn(),getSpace())
        mRvAdapter = getAdapter()
        refresh_mRecyclerView?.adapter = mRvAdapter
    }

    open fun getColumn(): Int {
        return 1
    }

    open fun getSpace(): Int {
        return 0
    }

    abstract fun getAdapter(): Adapter

    private fun initRefreshLayout() {
        refresh_mBGARefreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData(mCurrentPage, mPageSize)
        }
        refresh_mBGARefreshLayout.setOnLoadMoreListener {
            mCurrentPage++
            loadData(mCurrentPage, mPageSize)
        }
    }

    abstract fun loadData(mCurrentPage: Int, mPageSize: Int)

    fun showContentView() {
        refresh_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_CONTENT
    }

    fun setRightTitle(title: String, l: View.OnClickListener) {
        refreshTitleBar.setRightTitle(title)
        refreshTitleBar.getRightView().setOnClickListener(l)
    }

    fun finishLoading() {
        refresh_mBGARefreshLayout?.finishLoadMore()
        refresh_mBGARefreshLayout?.finishRefresh()
    }

    fun resetNoMoreData() {
        refresh_mBGARefreshLayout?.resetNoMoreData()
    }

    fun finishRefreshWithNoMoreData() {
        refresh_mBGARefreshLayout?.finishRefreshWithNoMoreData()
    }

    fun finishLoadMoreWithNoMoreData() {
        refresh_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
    }

    fun refreshDataList(list: MutableList<D>) {
        finishLoading()
        hasMoreData = if (list != null) (list.size >= mPageSize) else false
        if (list != null && list.size > 0) {
            if (mCurrentPage == 1) {
                resetNoMoreData()
                setData(mRvAdapter, list)
            } else {
                addAllData(mRvAdapter, list)
                mRvAdapter?.notifyDataSetChanged()
            }
            showContentView()
        } else {
            if (mCurrentPage == 1) {
                finishRefreshWithNoMoreData()
                onDataIsNull()
            } else {
                finishLoadMoreWithNoMoreData()
                showContentView()
            }
        }
        if (!hasMoreData) {
            if (mCurrentPage == 1) {
                finishRefreshWithNoMoreData()
            } else {
                finishLoadMoreWithNoMoreData()
            }
        }
    }

    abstract fun addAllData(
        mRvAdapter: Adapter,
        list: MutableList<D>
    )

    abstract fun setData(
        mRvAdapter: Adapter,
        list: MutableList<D>
    )

    override fun onDataIsNull() {
        super.onDataIsNull()
        refresh_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_EMPTY
    }
}
