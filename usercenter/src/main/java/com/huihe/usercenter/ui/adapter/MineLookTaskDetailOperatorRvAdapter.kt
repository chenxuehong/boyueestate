package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_looktask_operator_item.view.*

class MineLookTaskDetailOperatorRvAdapter (mContext: Context) :
    BaseRecyclerViewAdapter<String, MineLookTaskDetailOperatorRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_looktask_operator_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        holder.itemView.tvOperator.text = "$item"
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}