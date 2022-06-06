package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R

import com.huihe.boyueentities.protocol.user.CorporateCultureRep
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_corporateculture_item.view.*

class CorporateCultureRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CorporateCultureRep.CorporateCulture, CorporateCultureRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_corporateculture_item,parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var corporateCulture = dataList[position]
        holder.itemView.ivCultureCoverImage.loadUrl(corporateCulture.coverImageUrl)
        holder.itemView.tvCultureTitle.text = corporateCulture.title?:""
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
