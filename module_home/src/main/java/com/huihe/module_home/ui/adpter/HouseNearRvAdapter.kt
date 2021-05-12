package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baidu.mapapi.search.core.PoiInfo
import com.huihe.module_home.R
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_near_item.view.*

class HouseNearRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<PoiInfo?, HouseNearRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_near_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var poiInfo = dataList[position]
        holder.itemView.tvNearTitle.text = poiInfo?.name?:""
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}