package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.MeItemBean
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.DensityUtils
import kotlinx.android.synthetic.main.layout_me_child_item.view.*
import kotlinx.android.synthetic.main.layout_me_group_item.view.*

class MeChildRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<MeItemBean.ItemData, MeChildRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_me_child_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemBean = dataList[position]
        holder.itemView.ivIcon.setImageResource(itemBean.icon)
        holder.itemView.tvStatus.text = itemBean.title
        if (itemBean.unread > 0) {
            holder.itemView.bvUnread.setBadgeNum(itemBean.unread)
            holder.itemView.bvUnread.setShowNum(true)
        } else {
            holder.itemView.bvUnread.setShowNum(false)
        }
        holder.itemView.bvUnread.redraw()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}