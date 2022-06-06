package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R

import com.huihe.boyueentities.protocol.customer.CustomerLogRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_customer_log_item.view.*

class CustomerLogRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerLogRep.CustomerLog, CustomerLogRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_customer_log_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var houseLog = dataList[position]
        holder.itemView.tvLogUserDeptName.text = "${houseLog.createUserName?:""}(${houseLog.deptName?:""})"
        holder.itemView.tvLogContent.text = houseLog.content?:""
        holder.itemView.tvLogCreateTime.text = houseLog.createDate?:""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
