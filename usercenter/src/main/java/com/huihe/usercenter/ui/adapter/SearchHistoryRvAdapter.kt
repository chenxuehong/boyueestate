package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R

import com.huihe.boyueentities.protocol.user.SearchBean
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_query_history.view.*

class SearchHistoryRvAdapter(mContext: Context,var mOnListener:OnListener) :
    BaseRecyclerViewAdapter<SearchBean, SearchHistoryRvAdapter.ViewHolder>(mContext) {

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        } else if (position == 1) {
            return 1
        }
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType) {
            0 -> {
                return ViewHolder(initInflater(mContext, R.layout.layout_query_history, parent))
            }
            1 -> {
                return ViewHolder(
                    initInflater(
                        mContext,
                        R.layout.layout_query_history_clear,
                        parent
                    )
                )
            }
            else -> {
                return ViewHolder(initInflater(mContext, R.layout.item_query_history, parent))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemViewType = getItemViewType(position)
        if (itemViewType != 0 && itemViewType != 1) {
            var searchBean = dataList[position]
            holder.itemView.item_query_history_tv_history.text = searchBean.content
            holder.itemView.item_query_history_tv_close.onClick {
                mOnListener?.onDelete(searchBean.content)
            }
            holder.itemView.onClick {
                mOnListener?.onSearch(searchBean.content)
            }
        }else if (itemViewType == 1){
            holder.itemView.onClick {
                mOnListener?.clearHistory()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnListener{
        fun onDelete(content:String)
        fun onSearch(content:String)
        fun clearHistory()
    }
}
