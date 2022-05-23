package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R

import com.huihe.usercenter.data.protocol.DeptUserRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_community_item.view.*
import kotlinx.android.synthetic.main.layout_dept_user_item.view.*

class DeptUserRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<DeptUserRep, DeptUserRvAdapter.ViewHolder>(mContext) {

    var checkedIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_dept_user_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        holder.itemView.tvDeptUserTitle.text = item.label
        if (checkedIndex == position) {
            holder.itemView.tvDeptUserTitle.setBackgroundColor(mContext.resources.getColor(R.color.line))
        } else {
            holder.itemView.tvDeptUserTitle.setBackgroundColor(mContext.resources.getColor(R.color.white))
        }
    }

    override fun changeStatus(itemView: View, t: DeptUserRep, position: Int) {
        super.changeStatus(itemView, t, position)
        checkedIndex = position
        notifyDataSetChanged()
    }

    fun resetData() {
        checkedIndex =-1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
