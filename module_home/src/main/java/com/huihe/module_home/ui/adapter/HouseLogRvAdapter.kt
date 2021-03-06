package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.boyueentities.protocol.home.HouseLog
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_log_item.view.*

class HouseLogRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<HouseLog, HouseLogRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_log_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var houseLog = dataList[position]
        holder.itemView.tvLogUserDeptName.text = "${houseLog.createUserName?:""}(${houseLog.deptName?:""})"
        holder.itemView.tvLogContent.text = houseLog.content?:""
        holder.itemView.tvLogCreateTime.text = houseLog.createTime?:""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
