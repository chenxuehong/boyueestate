package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R

import com.huihe.customercenter.data.protocol.CustomerDetailBean
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.isFakeBoldText
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_customer_detail_basicinfo_child_item.view.*


class BasicInfoRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerDetailBean.ItemBasicInfo, BasicInfoRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_customer_detail_basicinfo_child_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemBasicInfo = dataList[position]
        holder.itemView.tvCustomerLeftTitle.isFakeBoldText(itemBasicInfo.isTitle)
        holder.itemView.tvCustomerLeftTitle.text = itemBasicInfo.title?:""
        holder.itemView.tvCustomerRightContent.text = itemBasicInfo.content?:""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
