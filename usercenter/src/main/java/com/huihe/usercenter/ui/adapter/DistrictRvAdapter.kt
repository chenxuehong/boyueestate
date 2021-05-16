package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R

import com.huihe.usercenter.data.protocol.SchoolDistrictRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_district_item.view.*

class DistrictRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<SchoolDistrictRep.SchoolDistrict, DistrictRvAdapter.ViewHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_district_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var schoolDistrict = dataList[position]
        holder.itemView.tvDistrictTitle.text = schoolDistrict.schoolDistrictName
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
