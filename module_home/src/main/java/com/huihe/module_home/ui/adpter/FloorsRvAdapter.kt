package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_floor_item.view.*

class FloorsRvAdapter(context: Context) :
    BaseRecyclerViewAdapter<String, FloorsRvAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(
                R.layout.layout_floor_item,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        holder.itemView.tvFloorTitle.text = item
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
