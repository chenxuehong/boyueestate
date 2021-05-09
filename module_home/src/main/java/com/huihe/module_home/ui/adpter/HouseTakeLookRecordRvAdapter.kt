package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_house_takelookrecord_item.view.*

class HouseTakeLookRecordRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<HouseTakeLookRep.HouseTakeLook, HouseTakeLookRecordRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_house_takelookrecord_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var houseTakeLook = dataList[position]
        holder.itemView.tvHouseLookUserDeptName.text = "${houseTakeLook.takeLookUser?:""}(${houseTakeLook.deptName})"
        holder.itemView.tvHouseLookContent.text = houseTakeLook.evaluate
        holder.itemView.tvHouseLookCreateTime.text = houseTakeLook.createDate
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
