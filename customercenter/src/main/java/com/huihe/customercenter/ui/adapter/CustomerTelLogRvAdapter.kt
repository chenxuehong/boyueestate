package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R

import com.huihe.customercenter.data.protocol.CustomerTelLogRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_customer_phone_log_item.view.*

class CustomerTelLogRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerTelLogRep.CustomerTelLog, CustomerTelLogRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_customer_phone_log_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var houseLog = dataList[position]
        holder.itemView.tvLogUserDeptName.text = "${houseLog.createUserName?:""}(${houseLog.deptName?:""})"
        holder.itemView.tvLogCreateTime.text = "${houseLog.operationType}  ${houseLog.createDate?:""}"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
