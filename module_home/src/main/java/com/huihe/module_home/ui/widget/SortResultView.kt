package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.ui.adpter.SortRvAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

private val sorts =
    mutableListOf("默认排序", "录入时间升序", "录入时间降序",
        "跟进时间升序", "跟进时间降序",
        "座栋升序", "座东降序",
        "楼层升序", "楼层降序",
        "面积升序", "面积降序",
        "售价升序", "售价降序"
    )

fun View.initSortView(
    mContext: Context,
    mListener: ISearchResultListener?
) {
    var rvSort = this as RecyclerView
    rvSort.layoutManager = LinearLayoutManager(mContext)
    var mSortRvAdapter = SortRvAdapter(mContext)
    rvSort.adapter = mSortRvAdapter
    mSortRvAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<String>{
        override fun onItemClick(view: View, item: String, position: Int) {
//            mListener?.onSearchResult()
        }
    })
    mSortRvAdapter.setData(sorts)
}
