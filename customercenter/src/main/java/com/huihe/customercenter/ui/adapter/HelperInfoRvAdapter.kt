package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R

import com.huihe.boyueentities.protocol.customer.CustomerDetailBean
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_helper_item.view.*

class HelperInfoRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerDetailBean.ItemHelperInfo, HelperInfoRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_helper_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemHelperInfo = dataList[position]
        holder.itemView.ivHelperIcon.setImageResource(itemHelperInfo.icon?:0)
        holder.itemView.ivHelperTitle.text = itemHelperInfo.title
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
