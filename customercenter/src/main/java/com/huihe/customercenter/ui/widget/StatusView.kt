package com.huihe.customercenter.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.boyueentities.protocol.customer.StatusRep
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.ui.adapter.StatusRvAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class StatusView {
    fun initStatusView(
        mContext: Context,
        mListener: ISearchResultListener?,
        inflate: View
    ) {
        var mSortRvAdapter = StatusRvAdapter(mContext)
        (inflate as RecyclerView).apply {
            layoutManager = LinearLayoutManager(mContext)

            adapter = mSortRvAdapter
        }

        mSortRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<StatusRep> {
            override fun onItemClick(view: View, item: StatusRep, position: Int) {
                mListener?.onSearchResult(
                    item,
                    item.dataValue?:"",
                    CustomersModule.SearchType.StatusType
                )
            }

        })
        mListener?.startLoadStatus(mSortRvAdapter)
    }
}
