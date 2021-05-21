package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class PhotoRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<String?, PhotoRvAdapter.ViewHolder>(
        mContext
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(initInflater(mContext!!, R.layout.layout_photo_item,parent))
    }

    fun addData(photo: String) {
        dataList?.add(photo)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}