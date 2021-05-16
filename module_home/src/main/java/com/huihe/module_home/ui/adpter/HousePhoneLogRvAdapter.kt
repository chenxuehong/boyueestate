package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.module_home.data.protocol.HouseLog
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_phone_log_item.view.*

class HousePhoneLogRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<HouseLog, HousePhoneLogRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_phone_log_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var houseLog = dataList[position]
        holder.itemView.tvLogUserDeptName.text = "${houseLog.createUserName?:""}(${houseLog.deptName?:""})"
        holder.itemView.tvLogCreateTime.text = "${houseLog.operationType}  ${houseLog.createTime?:""}"
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
