package com.huihe.customercenter.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.data.protocol.SortReq
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.ui.adapter.SortRvAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class SortView {
    private val sorts =
        mutableListOf(
            "默认排序", "录入时间升序", "录入时间降序",
            "跟进时间升序", "跟进时间降序"
        )
    fun initSortView(
        mContext: Context,
        mListener: ISearchResultListener?,
        inflate: View
    ) {
        var mSortRvAdapter = SortRvAdapter(mContext)
        (inflate as RecyclerView).apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mSortRvAdapter
        }
        mSortRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, item: String, position: Int) {
                mListener?.onSearchResult(
                    getSortReq(item, position),
                    item,
                    CustomersModule.SearchType.SortType
                )
            }

        })
        mSortRvAdapter.setData(sorts)
    }

    private fun getSortReq(item: String, position: Int): SortReq? {

        when (position) {
            0 -> {
                return SortReq()
            }
            1 -> {
                return SortReq(createDateAsc = 0)
            }
            2 -> {
                return SortReq(createDateAsc = 1)
            }
            3 -> {
                return SortReq(followUpDateAsc = 0)
            }
            4 -> {
                return SortReq(followUpDateAsc = 1)
            }
            else -> {
                return SortReq()
            }
        }
    }

    fun clearData(){
        sorts?.clear()
    }
}
