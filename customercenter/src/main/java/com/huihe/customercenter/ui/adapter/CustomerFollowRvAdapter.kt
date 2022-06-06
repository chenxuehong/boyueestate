package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.CustomerFollowRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_house_follow_item.view.*

class CustomerFollowRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerFollowRep.CustomerFollow, CustomerFollowRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_house_follow_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var followBean = dataList[position]
        holder.itemView.tvHouseFollowUserDeptName.text = "${followBean.customerName?:""}(${followBean.deptName?:""})"
        holder.itemView.tvHouseFollowContent.text = followBean.followUpContent
        holder.itemView.tvHouseFollowCreateTime.text = followBean.followUpName
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
