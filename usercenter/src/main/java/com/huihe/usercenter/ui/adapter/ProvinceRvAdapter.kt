package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.qqtheme.framework.entity.City

import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

import cn.qqtheme.framework.entity.Province
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.District
import com.kotlin.base.ext.initInflater
import kotlinx.android.synthetic.main.layout_community_item.view.*

class ProvinceRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<District, ProvinceRvAdapter.ViewHolder>(mContext) {
    var checkedIndex = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext,R.layout.layout_community_item,parent))
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

    override fun changeStatus(itemView: View, t: District, position: Int) {
        super.changeStatus(itemView, t, position)
        checkedIndex = position
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
