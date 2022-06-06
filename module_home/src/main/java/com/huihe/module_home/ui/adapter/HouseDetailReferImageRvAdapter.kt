package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.HouseDetail
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.GlideUtils

class HouseDetailReferImageRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<HouseDetail.ReferUrlsBean, HouseDetailReferImageRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                mContext
            ).inflate(R.layout.layout_house_detail_photo_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        var imagUrlsBean = dataList[position]
        var viewHolder = holder as ViewHolder
        GlideUtils.loadImage(mContext,imagUrlsBean?.url!!,viewHolder.itemView as ImageView)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}