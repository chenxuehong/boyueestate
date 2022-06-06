package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.DeptUserRep
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_deptuser_item.view.*

class DeptUsersRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<DeptUserRep, DeptUsersRvAdapter.ViewHolder>(
        mContext
    ) {

    var  checkedIndex=-1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_deptuser_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var deptUserRep = dataList[position]
        holder.itemView.deptuserCheckbox.setVisible(false)
        holder.itemView.tvDeptuserName.text = deptUserRep?.label?:""
        if (checkedIndex ==position){
            holder.itemView.tvDeptuserName.setTextColor(mContext.resources.getColor(R.color.main_color))
        }else{
            holder.itemView.tvDeptuserName.setTextColor(mContext.resources.getColor(R.color.color_333333))
        }
    }

    override fun changeStatus(itemView: View, t: DeptUserRep, position: Int) {
        super.changeStatus(itemView, t, position)
        checkedIndex = position
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}