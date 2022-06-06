package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.MineLookTaskRep
import com.kotlin.base.ext.convertNotNullStr
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
                holder.itemView.tvStatus.setBackgroundResource(R.drawable.common_red_radius_bg)
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.to_start)
            }
            1->{
                holder.itemView.tvStatus.setBackgroundResource(R.drawable.common_green_radius_bg)
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.take_look)
            }
            2->{
                holder.itemView.tvStatus.setBackgroundResource(R.drawable.common_yellow_radius_bg)
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.in_summary)
            }
            3->{
                holder.itemView.tvStatus.setBackgroundResource(R.drawable.common_review_radius_bg)
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.under_review)
            }
            else ->{
                holder.itemView.tvStatus.setBackgroundResource(R.drawable.common_green_radius_bg)
                holder.itemView.tvStatus.text = mContext.resources.getString(R.string.finished)
            }
        }
        var houseNum = getHouseCount(mineLookTask?.houseCode)
        var createUser = mineLookTask.createUser.convertNotNullStr("无")
        var takeLookUser = mineLookTask.takeLookUser.convertNotNullStr("无")
        var accompanyUser = mineLookTask.accompanyUser.convertNotNullStr("无")
        var lookTime = mineLookTask.lookTime.convertNotNullStr("无")
        holder.itemView.tvLookTaskInfo1.text = "创建:${createUser} 带看:${takeLookUser} 陪看:${ accompanyUser}"
        val colorText = "带看房源:<font color = \"#FF0000\">${houseNum}套</font> 带看时间:<font color = \"#666666\">${lookTime}</font>"
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