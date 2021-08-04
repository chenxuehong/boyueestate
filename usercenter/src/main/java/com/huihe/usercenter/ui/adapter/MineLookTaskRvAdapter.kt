package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.MineLookTaskRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_mine_looktask_item.view.*

class MineLookTaskRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<MineLookTaskRep.MineLookTask, MineLookTaskRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_mine_looktask_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var mineLookTask = dataList[position]
        holder.itemView.tvCustomerInfo.text = "(${mineLookTask.customerName?:"无"})${mineLookTask.customerCode?:""}"
        when(mineLookTask.status?:0){
            0->{
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.to_start)
            }
            2->{
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.take_look)
            }
            3->{
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.in_summary)
            }
            else->{
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.under_review)
            }
        }
        var houseNum = getHouseCount(mineLookTask?.houseCode)
        holder.itemView.tvLookTaskInfo1.text = "创建:${mineLookTask.createUser?:"无"} 带看:${mineLookTask.takeLookUser?:"无"} 陪看:${mineLookTask.accompanyUser?:"无"}"
        val colorText = "带看房源:<font color = \"#FF0000\">${houseNum}套</font> 带看时间:<font color = \"#666666\">${mineLookTask.lookTime?:"无"}</font>"
        holder.itemView.tvLookTaskInfo2.text =  Html.fromHtml(colorText)
    }

    private fun getHouseCount(houseCode: String?): String {
        if (TextUtils.isEmpty(houseCode)){
            return "0"
        }
        var s = houseCode?.split(",")?: mutableListOf()
        return s?.size.toString()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}