package com.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.kotlin.base.R
import com.kotlin.base.ext.startLoading
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_refresh.*
import kotlinx.android.synthetic.main.fragment_title_refresh.*

abstract class BaseTitleRefreshFragment<T : BasePresenter<*>, Adapter : BaseRecyclerViewAdapter<*, *>,D> :
    BaseMvpFragment<T>() {

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
        return inflater.inflate(R.layout.fragment_title_refresh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initRefreshLayout()
        initAdapter()
        initView()
        initData()
    }

    private fun initData() {
        refreshTitle_mMultiStateView.startLoading()
        mCurrentPage = 1
        loadData()
    }

    private fun initTitle() {
        refreshTitleBar.setTitle(getTitle())
    }

    abstract fun getTitle(): String

     fun initAdapter(){
         refreshTitle_mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRvAdapter = getAdapter()
         refreshTitle_mRecyclerView?.adapter = mRvAdapter
    }
    abstract fun getAdapter(): Adapter

    private fun initRefreshLayout() {
        refreshTitle_mBGARefreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData()
        }
        refreshTitle_mBGARefreshLayout.setOnLoadMoreListener {
            mCurrentPage++
            loadData()
        }
    }

    abstract fun initView()

    abstract fun loadData()

    fun showContentView() {
        refreshTitle_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_CONTENT
    }

    fun setRightTitle(title: String, l: View.OnClickListener) {
        refreshTitleBar.setRightTitle(title)
        refreshTitleBar.getRightView().setOnClickListener(l)
    }

    fun finishLoading() {
        refreshTitle_mBGARefreshLayout?.finishLoadMore()
        refreshTitle_mBGARefreshLayout?.finishRefresh()
    }

    fun finishRefreshWithNoMoreData() {
        refreshTitle_mBGARefreshLayout?.finishRefreshWithNoMoreData()
    }

    fun finishLoadMoreWithNoMoreData() {
        refreshTitle_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
    }

    fun resetNoMoreData() {
        refresh_mBGARefreshLayout?.resetNoMoreData()
    }

    fun refreshDataList(list: MutableList<D>) {
        finishLoading()
        hasMoreData = if (list != null) (list.size >= mPageSize) else false
        if (list != null && list.size > 0) {
            if (mCurrentPage == 1) {
                resetNoMoreData()
                setData(mRvAdapter,list)
            } else {
                addAllData(mRvAdapter,list)
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
        mRvAdapter:Adapter,
        list: MutableList<D>
    )

    abstract fun setData(
        mRvAdapter: Adapter,
        list: MutableList<D>
    )
}
