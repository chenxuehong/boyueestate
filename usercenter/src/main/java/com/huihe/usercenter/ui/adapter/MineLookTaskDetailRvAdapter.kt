package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.LookTaskDetailRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_looktask_detail_item.view.*

class MineLookTaskDetailRvAdapter(mContext: Context,var listener:OnLookTaskListener) :
    BaseRecyclerViewAdapter<LookTaskDetailRep, MineLookTaskDetailRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_looktask_detail_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var lookTaskDetailRep = dataList[position]
        holder.itemView.tvTitle.text = "${lookTaskDetailRep.villageName?:""}  ${lookTaskDetailRep?.building}幢- ${lookTaskDetailRep?.hNum?:""}号"
        var takeSignInStatus = lookTaskDetailRep.takeSignInStatus
        var accompanySignInStatus = lookTaskDetailRep.accompanySignInStatus

        when(takeSignInStatus){
            0->{
                // 0 未签到
                holder.itemView.tvTakeLook.text = "带看人:${mContext.resources.getString(R.string.unsigned)}"
                holder.itemView.tvTakeLookSign.text = mContext.resources.getString(R.string.sign)
                holder.itemView.tvTakeLookSign.onClick {
                    listener?.onTakeLookSign(position,lookTaskDetailRep)
                }
            }
            1->{
                // 1 异常签到
                val takeLookColorText = "带看人:<font color = \"#FF0000\">${mContext.resources.getString(R.string.signed_error)}</font>"
                holder.itemView.tvTakeLook.text = Html.fromHtml(takeLookColorText)
                holder.itemView.tvTakeLookSign.text = mContext.resources.getString(R.string.signed)
                holder.itemView.tvTakeLookSign.setOnClickListener(null)
            }
            else->{
                // 2 正常签到
                holder.itemView.tvTakeLook.text = "带看人:${mContext.resources.getString(R.string.signed_success)}"
                holder.itemView.tvTakeLookSign.text = mContext.resources.getString(R.string.signed)
                holder.itemView.tvTakeLookSign.setOnClickListener(null)
            }
        }

        when(accompanySignInStatus){
            0->{
                // 0 未签到
                holder.itemView.tvAccompany.text = "陪看人:${mContext.resources.getString(R.string.unsigned)}"
                holder.itemView.tvAccompanySign.text = mContext.resources.getString(R.string.sign)
                holder.itemView.tvAccompanySign.onClick {
                    listener?.onAccompanySign(position,lookTaskDetailRep)
                }
            }
            1->{
                // 1 异常签到
                val accompanyColorText = "陪看人:<font color = \"#FF0000\">${mContext.resources.getString(R.string.signed_error)}</font>"
                holder.itemView.tvAccompany.text = Html.fromHtml(accompanyColorText)
                holder.itemView.tvAccompanySign.text = mContext.resources.getString(R.string.signed)
                holder.itemView.tvTakeLookSign.setOnClickListener(null)
            }
            else->{
                // 2 正常签到
                holder.itemView.tvAccompany.text = "陪看人:${mContext.resources.getString(R.string.signed_success)}"
                holder.itemView.tvAccompanySign.text = mContext.resources.getString(R.string.signed)
                holder.itemView.tvTakeLookSign.setOnClickListener(null)
            }
        }

        holder.itemView.tvCancel.onClick {
            listener?.onItemCancel(position,lookTaskDetailRep)
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    interface OnLookTaskListener{
        fun onItemCancel(index:Int,item :LookTaskDetailRep)
        fun onTakeLookSign(index:Int,item :LookTaskDetailRep)
        fun onAccompanySign(index:Int,item :LookTaskDetailRep)
    }
}