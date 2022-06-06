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

class DeptUsersRvAdapter2(mContext: Context, var mListener: OnCheckListener) :
    BaseRecyclerViewAdapter<DeptUserRep.DeptUser, DeptUsersRvAdapter2.ViewHolder>(
        mContext
    ) {

    var checkList = mutableListOf<DeptUserRep.DeptUser>()

    init {
        checkList.clear()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.layout_deptuser_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var deptUserRep = dataList[position]
        holder.itemView.deptuserCheckbox.setVisible(true)
        holder.itemView.tvDeptuserName.text = deptUserRep?.label ?: ""

        holder.itemView.tvDeptuserName.setTextColor(
            if (inCheckList(position)) mContext.resources.getColor(R.color.main_color)
            else mContext.resources.getColor(R.color.color_333333)
        )

        holder.itemView.deptuserCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                if (!inCheckList(position)) {
                    holder.itemView.tvDeptuserName.setTextColor(mContext.resources.getColor(R.color.main_color))
                    checkList.add(deptUserRep)
                }
            } else {
                if (inCheckList(position)) {
                    holder.itemView.tvDeptuserName.setTextColor(mContext.resources.getColor(R.color.color_333333))
                    checkList.remove(deptUserRep)
                }
            }
            mListener?.onChecked(holder.itemView, deptUserRep, position, checkList)
        }
        holder.itemView.deptuserCheckbox.isChecked = inCheckList(position)
    }

    override fun changeStatus(itemView: View, t: DeptUserRep.DeptUser, position: Int) {
        super.changeStatus(itemView, t, position)
        if (inCheckList(position)) {
            checkList.remove(t)
        } else {
            checkList.add(t)
        }
        notifyDataSetChanged()
        mListener?.onChecked(itemView, t, position, checkList)
    }

    private fun inCheckList(position: Int): Boolean {

        var deptUser = dataList[position]
        checkList.forEach { item ->
            if (deptUser.uid == item.uid) {
                return true
            }
        }
        return false
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    interface OnCheckListener {
        fun onChecked(
            view: View,
            item: DeptUserRep.DeptUser?,
            position: Int,
            checkList: MutableList<DeptUserRep.DeptUser>
        )
    }
}