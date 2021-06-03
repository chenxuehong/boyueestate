package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_house_takelookrecord_item.view.*

class HouseTakeLookRecordRvAdapter(mContext: Context,val mOnSeeDetailListener:OnSeeDetailListener<HouseTakeLookRep.HouseTakeLook>) :
    BaseRecyclerViewAdapter<HouseTakeLookRep.HouseTakeLook, HouseTakeLookRecordRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_house_takelookrecord_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var houseTakeLook = dataList[position]
        holder.itemView.tvHouseLookUserDeptName.text = "${houseTakeLook.takeLookUser?:""}(${houseTakeLook.deptName?:""})"
        holder.itemView.tvHouseLookContent.text = houseTakeLook.evaluate
        holder.itemView.tvHouseLookCreateTime.text = houseTakeLook.createDate
        holder.itemView.tvHouseLookDetail.onClick {
            mOnSeeDetailListener?.onSeeDetail(houseTakeLook,position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    /*
     ItemClick事件声明
  */
    interface OnSeeDetailListener<in T> {
        fun onSeeDetail(item: T, position: Int)
    }

}
