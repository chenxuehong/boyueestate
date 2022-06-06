package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.StatusRep
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_status_item.view.*

class StatusRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<StatusRep, StatusRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_status_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var statusRep = dataList[position]
        holder.itemView.tvStatusTitle.text = statusRep.dataValue
    }
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}