package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_photo_item.view.*

class RvPhotoAdapter(mContext: Context) : BaseRecyclerViewAdapter<String, RvPhotoAdapter.ViewHolder>(mContext){

    open val isUpdate:String = "isUpdate"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_photo_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var path = dataList[position]
        if (isUpdate.equals(path)){
            holder.itemView.ivPhoto.setImageResource(R.drawable.add_uploading)
        }else{
            holder.itemView.ivPhoto.loadUrl(path)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
