package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

import com.huihe.usercenter.R
import com.kotlin.base.ext.initInflater
import com.huihe.boyueentities.protocol.common.District
import kotlinx.android.synthetic.main.layout_community_item.view.*

class CityRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<District.ZoneBean, CityRvAdapter.ViewHolder>(mContext) {

    var checkedIndex = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_community_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var province = dataList[position]
        holder.itemView.tvCommunityTitle.text = province.name
        if (checkedIndex == position) {
            holder.itemView.tvCommunityTitle.setBackgroundColor(mContext.resources.getColor(R.color.line))
        } else {
            holder.itemView.tvCommunityTitle.setBackgroundColor(mContext.resources.getColor(R.color.white))
        }
    }

    override fun changeStatus(itemView: View, t: District.ZoneBean, position: Int) {
        super.changeStatus(itemView, t, position)
        checkedIndex = position
        notifyDataSetChanged()
    }

    fun resetData() {
        checkedIndex =-1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
