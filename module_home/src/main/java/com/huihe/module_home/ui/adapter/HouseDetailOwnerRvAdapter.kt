package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.ItemHouseDetail
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_house_detail_item.view.*

class HouseDetailOwnerRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<ItemHouseDetail.OwnerInfo, HouseDetailOwnerRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_house_detail_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var detailInfo = dataList[position]
        holder.itemView.tvHouseDetailTitle.text = mContext.resources.getString(R.string.house_owners)
        holder.itemView.tvHouseDetailRightTitle.text = detailInfo.leftTitle
        holder.itemView.tvHouseDetailRightContent.text = detailInfo.content?:""
        holder.itemView.tvHouseDetailTitle.setVisible(position==0)
        holder.itemView.tvHouseDetailRightArrow.setVisible(position!=0)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
