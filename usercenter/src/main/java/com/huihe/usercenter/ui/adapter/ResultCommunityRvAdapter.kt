package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.setVisible

import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.provider.data.protocol.District
import kotlinx.android.synthetic.main.layout_community_result_item.view.*

class ResultCommunityRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<District.ZoneBean.VillageBean, ResultCommunityRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_community_result_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var villageBean = dataList[position]
        holder.itemView.tvCommunityTitle.text = villageBean.name ?: ""
        holder.itemView.lineCommunity.setVisible(position != dataList.size - 1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
