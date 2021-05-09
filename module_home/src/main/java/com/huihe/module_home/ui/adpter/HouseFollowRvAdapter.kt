package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.module_home.data.protocol.FollowRep
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_house_follow_item.view.*

class HouseFollowRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<FollowRep.FollowBean, HouseFollowRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_house_follow_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var followBean = dataList[position]
        holder.itemView.tvHouseFollowUserDeptName.text = "${followBean.createUserName?:""}(${followBean.deptName?:""})"
        holder.itemView.tvHouseFollowContent.text = followBean.followContent
        holder.itemView.tvHouseFollowCreateTime.text = followBean.createTime
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
